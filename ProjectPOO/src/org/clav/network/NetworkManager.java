package org.clav.network;

import org.clav.Agent;
import org.clav.network.protocolsimpl.tcp.LinkTCPUserProtocol;
import org.clav.network.protocolsimpl.tcp.LinkTCPUserProtocolInit;

import static org.clav.utils.constants.NetworkConstants.*;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class NetworkManager {
	private Agent relatedAgent;

	public Agent getRelatedAgent() {
		return relatedAgent;
	}

	private InetAddress networkAddress;
	private InetAddress broadcastAddress;
	private HashMap<String, InetAddress> addrMap;
	private DatagramSocket sendSocketUDP;
	private DatagramSocket receiveSocketUDP;
	private HashMap<String, TCPUserLink> tcpConnections;

	public static NetworkManager testModeNetworkManager(InetAddress networkAddress, InetAddress broadcastAddress, DatagramSocket sendSocketUDP, DatagramSocket receiveSocketUDP){
		return new NetworkManager(networkAddress,broadcastAddress,sendSocketUDP,receiveSocketUDP);
	}

	public NetworkManager(InetAddress networkAddress, InetAddress broadcastAddress) {
		this.networkAddress = networkAddress;
		this.broadcastAddress = broadcastAddress;
		this.addrMap = new HashMap<>();
		this.tcpConnections = new HashMap<>();
		try {
			this.receiveSocketUDP = new DatagramSocket(UDPSOCKET_RECEIVE);
			this.sendSocketUDP = new DatagramSocket(UDPSOCKET_SEND);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	public synchronized void initiateConnectionTCP(String user){
		if (!this.tcpConnections.containsKey(user)) {
			try {
				System.out.println("Initiating tcp connection");
				Socket distant = new Socket(this.addrMap.get(user), TCP_SOCKET_SERVER);
				System.out.println("Socket created,link protocolsimpl started");
				TCPUserLink link = new TCPUserLink(user,distant);
				LinkTCPUserProtocolInit init = new LinkTCPUserProtocolInit(this,link, LinkTCPUserProtocolInit.Mode.CONNECT,user);
				this.executeProtocol(new LinkTCPUserProtocol(init));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private synchronized void initiateConnectionTCP(String user,boolean blocking){
		if (!this.tcpConnections.containsKey(user)) {
			try {
				System.out.println("Initiating tcp connection");
				Socket distant = new Socket(this.addrMap.get(user), TCP_SOCKET_SERVER);
				System.out.println("Socket created,link protocolsimpl started");
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
			System.out.println("Network manager already has an active link with user "+identifier);
		}
	}
	public synchronized void closeConnectionTCP(String identifier){
		if(this.tcpConnections.containsKey(identifier)){
			try {
				System.out.println("Closing TCP connection with user "+identifier);
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

	public synchronized void UDP_Send(byte[] bytes, InetAddress address) {
		synchronized (this.sendSocketUDP) {
			try {
				DatagramPacket packetUDP = new DatagramPacket(bytes, bytes.length, address, UDPSOCKET_RECEIVE);
				this.sendSocketUDP.send(packetUDP);
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void TCP_IP_send(String id, String message) {
		TCPUserLink link = this.getTCPLinkFor(id);
		if(link==null){
			System.out.println("No TCP link established with user "+id);
			System.out.println("Trying to initiate connection");
			this.initiateConnectionTCP(id,true);
			this.getTCPLinkFor(id).send(message);
		}
		else {
			System.out.println("[TCP]Sending message to user "+id);
			this.getTCPLinkFor(id).send(message);
		}
	}

	public synchronized void addAddrFor(String identifier, InetAddress addr){
		this.addrMap.put(identifier,addr);
	}

	public String getAddrFor(String identifier){
		return this.addrMap.get(identifier).toString();
	}

	public TCPUserLink getTCPLinkFor(String user){
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

	private NetworkManager(InetAddress networkAddress, InetAddress broadcastAddress, DatagramSocket sendSocketUDP, DatagramSocket receiveSocketUDP) {
		this.networkAddress = networkAddress;
		this.broadcastAddress = broadcastAddress;
		this.sendSocketUDP = sendSocketUDP;
		this.receiveSocketUDP = receiveSocketUDP;
		this.addrMap = new HashMap<>();
		this.tcpConnections = new HashMap<>();
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
}
