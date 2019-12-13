package org.clav.network;

import org.clav.Agent;
import org.clav.AppHandler;
import org.clav.debug.ConsoleLogger;
import org.clav.debug.DebugPlugin;
import org.clav.debug.Pluggable;
import org.clav.network.protocolsimpl.tcp.LinkTCPUserProtocol;
import org.clav.network.protocolsimpl.tcp.LinkTCPUserProtocolInit;
import org.clav.network.protocolsimpl.tcp.TCPListenerProtocol;
import org.clav.network.protocolsimpl.udp.ActivitySignalProtocol;
import org.clav.network.protocolsimpl.udp.ActivitySignalProtocolInit;
import org.clav.network.protocolsimpl.udp.UDPListenerProtocol;

import static org.clav.network.CLVHeader.STR;
import static org.clav.utils.constants.NetworkConstants.*;

import java.io.*;
import java.net.*;
import java.util.HashMap;

/**
 * Handles all the network base functions of the application via the multithreaded executions of several protocols.
 * New parallel behavior can be created by implementing a new protocol
 * -Listens UDP broadcast communications
 * -Listens TCP connection requests
 * -Initiates and maintains TCP connections
 * -Send broadcast UDP messages
 * -Send single-target TCP message
 * @see Protocol
 */
public class NetworkManager implements Pluggable {

	private Agent relatedAgent;
	private InetAddress networkAddress;
	private InetAddress broadcastAddress;
	private HashMap<String, InetAddress> addrMap;
	private DatagramSocket sendSocketUDP;
	private DatagramSocket receiveSocketUDP;
	private HashMap<String, TCPUserLink> tcpConnections;

	private AppHandler appHandler;
	private DebugPlugin debug;

	public DebugPlugin getDebug() {
		return debug;
	}

	@Override
	public void plug(DebugPlugin plugin) {
		this.debug = plugin;

	}
	public void log(String message){
		this.debug.log(message);
	}

	public static NetworkManager testModeNetworkManager(InetAddress networkAddress, InetAddress broadcastAddress, DatagramSocket sendSocketUDP, DatagramSocket receiveSocketUDP){
		return new NetworkManager(networkAddress,broadcastAddress,sendSocketUDP,receiveSocketUDP);
	}

	private NetworkManager(InetAddress networkAddress, InetAddress broadcastAddress, DatagramSocket sendSocketUDP, DatagramSocket receiveSocketUDP) {
		this.networkAddress = networkAddress;
		this.broadcastAddress = broadcastAddress;
		this.sendSocketUDP = sendSocketUDP;
		this.receiveSocketUDP = receiveSocketUDP;
		this.addrMap = new HashMap<>();
		this.tcpConnections = new HashMap<>();
		
		this.debug = new ConsoleLogger();
	}
	public NetworkManager(InetAddress networkAddress, InetAddress broadcastAddress) {
		this.networkAddress = networkAddress;
		this.broadcastAddress = broadcastAddress;
		this.addrMap = new HashMap<>();
		this.tcpConnections = new HashMap<>();
		try {
			this.receiveSocketUDP = new DatagramSocket(UDPSOCKET_RECEIVE_PORT);
			this.sendSocketUDP = new DatagramSocket(UDPSOCKET_SEND_PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		this.debug = new ConsoleLogger();
	}

	public synchronized void initiateConnectionTCP(String user){
		if (!this.tcpConnections.containsKey(user)) {
			try {
				this.log("[TCP]Initiating tcp connection");
				Socket distant = new Socket(this.addrMap.get(user), TCP_SOCKET_SERVER_PORT);
				this.log("[TCP]Socket created,link protocol started");
				TCPUserLink link = new TCPUserLink(user,distant);
				LinkTCPUserProtocolInit init = new LinkTCPUserProtocolInit(this,link, LinkTCPUserProtocolInit.Mode.CONNECT,user);
				this.executeProtocol(new LinkTCPUserProtocol(init));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void initiateConnectionTCP(String user,boolean blocking){
		if (!this.tcpConnections.containsKey(user)) {
			try {
				this.log("[TCP]Initiating tcp connection");
				Socket distant = new Socket(this.addrMap.get(user), TCP_SOCKET_SERVER_PORT);
				this.log("Socket created,link protocolsimpl started");
				TCPUserLink link = new TCPUserLink(user,distant);
				LinkTCPUserProtocolInit init = new LinkTCPUserProtocolInit(this,link, LinkTCPUserProtocolInit.Mode.CONNECT,user);
				this.executeProtocol(new LinkTCPUserProtocol(init),blocking);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void linkTCP(String identifier, TCPUserLink link){
		if(this.getTCPLinkFor(identifier)==null){
			this.tcpConnections.put(identifier,link);
		}
		else{
			this.log("Network manager already has an active link with user "+identifier);
		}
	}

	public synchronized void closeConnectionTCP(String identifier){
		if(this.tcpConnections.containsKey(identifier)){
			try {
				this.log("[TCP]Closing TCP connection with user "+identifier);
				this.tcpConnections.get(identifier).getDistant().close();
				this.tcpConnections.remove(identifier);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void broadcast(byte[] bytes) {
		this.UDP_Send(bytes, this.broadcastAddress);
	}

	public synchronized void UDP_Send(byte[] bytes, InetAddress address) {//TODO transmit objects instead of strings
		synchronized (this.sendSocketUDP) {
			try {
				DatagramPacket packetUDP = new DatagramPacket(bytes, bytes.length, address, UDPSOCKET_RECEIVE_PORT);
				this.sendSocketUDP.send(packetUDP);
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void TCP_IP_send(String id, String message) {
		this.log("[TCP]Sending message to user "+id);
		this.TCP_IP_send(id,new CLVPacket(STR,message));
		this.getDebug().writeChatMessageTo(id,message);
	}


	public void TCP_IP_send(String id,CLVPacket packet){
		TCPUserLink link = this.getTCPLinkFor(id);
		if(link==null){
			this.log("No TCP link established with user "+id);
			this.log("Trying to initiate connection");
			this.initiateConnectionTCP(id,true);
			link = this.getTCPLinkFor(id);
		}
		this.log("[TCP]Sending packet to user "+id);
		link.send(packet);
	}

	/**
	 * Crucial method to allow the network manager to serve multiple components of the agent
	 * For any new agent component need, implement a new Protocol to serve it
	 * @param protocol A runnable class using the network manager methods and interacting with the other managers
	 */
	public void executeProtocol(Protocol protocol) {
		Thread t = new Thread(protocol);
		t.start();
	}

	private void executeProtocol(Protocol protocol,boolean join){
		Thread t = new Thread(protocol);
		t.start();
		if(join){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void startUDPListening(){
		this.executeProtocol(new UDPListenerProtocol(new ProtocolInit(this)));
	}

	public void startTCPListening(){
		this.executeProtocol(new TCPListenerProtocol(new ProtocolInit(this)));
	}

	public void startUDPSignal(){
		this.executeProtocol(new ActivitySignalProtocol(new ActivitySignalProtocolInit(this,this.getRelatedAgent().getUserManager())));
	}

	public Agent getRelatedAgent() {
		return relatedAgent;
	}
	
	public void addAddrFor(String identifier, InetAddress addr){
		synchronized (this.addrMap) {
			this.addrMap.put(identifier,addr);
		}
	}

	public String getAddrFor(String identifier){
		return this.addrMap.get(identifier).toString();
	}

	private TCPUserLink getTCPLinkFor(String user){
		return this.tcpConnections.get(user);
	}

	public DatagramSocket getSendSocketUDP() {
		return sendSocketUDP;
	}

	public DatagramSocket getReceiveSocketUDP() {
		return receiveSocketUDP;
	}

	public void setReceiveSocketUDP(DatagramSocket receiveSocketUDP) {
		this.receiveSocketUDP = receiveSocketUDP;
	}

	public void setRelatedAgent(Agent relatedAgent) {
		this.relatedAgent = relatedAgent;
	}
}
