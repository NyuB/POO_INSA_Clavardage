package org.clav.network;

import org.clav.AppHandler;
import org.clav.debug.ConsoleLogger;
import org.clav.debug.DebugPlugin;
import org.clav.debug.Pluggable;
import org.clav.network.protocols.tcp.LinkTCPUserProtocol;
import org.clav.network.protocols.tcp.LinkTCPUserProtocolInit;
import org.clav.network.protocols.tcp.TCPListenerProtocol;
import org.clav.network.protocols.udp.ActivitySignalProtocol;
import org.clav.network.protocols.udp.ActivitySignalProtocolInit;
import org.clav.network.protocols.udp.UDPListenerProtocol;
import org.clav.network.server.PresenceClient;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.clav.utils.constants.NetworkConstants.TCP_SOCKET_SERVER_PORT;
import static org.clav.utils.constants.NetworkConstants.UDPSOCKET_RECEIVE_PORT;

/**
 * Handles all the network base functions of the application via the multithreaded executions of several protocols.
 * New parallel behavior can be created by implementing a new protocol
 * -Listens UDP broadcast communications
 * -Listens TCP connection requests
 * -Initiates and maintains TCP connections
 * -Send broadcast UDP messages
 * -Send single-target TCP message
 *
 * @see Protocol
 */
public class NetworkManager implements Pluggable {
	private InetAddress networkAddress;
	private InetAddress broadcastAddress;
	private DatagramSocket sendSocketUDP;
	private DatagramSocket receiveSocketUDP;

	private final HashMap<String, InetAddress> addrMap;
	private final HashMap<String, TCPUserLink> tcpConnections;
	private final HashSet<PresenceClient> presenceClients;

	private final ExecutorService protocolService;

	private ExecutorService signalProtocolService;
	private ExecutorService broadcastListeningProtocolService;

	private AppHandler appHandler;//Application to interact with when receiving/interpreting messages

	private DebugPlugin debug;
	public DebugPlugin getDebug() {
		return debug;
	}

	//TODO Remove on final version, useful to launch multiple sessions on localhost
	public int tcpLocalPort;
	public int tcpDistantPort;
	public int udpLocalPort;
	public int udpDistantPort;


	@Override
	public void plug(DebugPlugin plugin) {
		this.debug = plugin;
	}

	public void log(String message) {
		this.debug.log(message);
	}

	/**
	 * A debug constructor, allows ports specifications to prevent interferences when launching multiple sessions on localhost
	 * @param broadcastAddress Address to broadcast udp packets
	 * @param udpPortLocal Port to listen udp packets
	 * @param tcpPortLocal Port to listen tcp requests
	 * @param udpPortDistant Port to send udp packets to
	 * @param tcpPortDistant Port to sent tcp connection requests to
	 */
	public NetworkManager(InetAddress broadcastAddress,int udpPortLocal,int tcpPortLocal,int udpPortDistant,int tcpPortDistant){
		this.tcpLocalPort = tcpPortLocal;
		this.udpLocalPort = udpPortLocal;
		this.tcpDistantPort = tcpPortDistant;
		this.udpDistantPort = udpPortDistant;
		this.broadcastAddress = broadcastAddress;
		this.addrMap = new HashMap<>();
		this.tcpConnections = new HashMap<>();
		this.presenceClients = new HashSet<>();
		try {
			this.receiveSocketUDP = new DatagramSocket(udpPortLocal);
			this.sendSocketUDP = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.signalProtocolService = Executors.newSingleThreadExecutor();
		this.broadcastListeningProtocolService = Executors.newSingleThreadExecutor();
		this.protocolService = Executors.newCachedThreadPool();
		this.debug = new ConsoleLogger();
	}

	public NetworkManager(InetAddress networkAddress, InetAddress broadcastAddress) {
		this.udpDistantPort = UDPSOCKET_RECEIVE_PORT;
		this.udpLocalPort = UDPSOCKET_RECEIVE_PORT;
		this.tcpDistantPort = TCP_SOCKET_SERVER_PORT;
		this.tcpLocalPort = TCP_SOCKET_SERVER_PORT;
		this.networkAddress = networkAddress;
		this.broadcastAddress = broadcastAddress;
		this.addrMap = new HashMap<>();
		this.tcpConnections = new HashMap<>();
		this.presenceClients = new HashSet<>();
		try {
			this.receiveSocketUDP = new DatagramSocket(UDPSOCKET_RECEIVE_PORT);
			this.sendSocketUDP = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.signalProtocolService = Executors.newSingleThreadExecutor();
		this.broadcastListeningProtocolService = Executors.newSingleThreadExecutor();
		this.protocolService = Executors.newCachedThreadPool();
		this.debug = new ConsoleLogger();
	}

	private void initiateConnectionTCP(String user, boolean parallel) {
		if (!this.tcpConnections.containsKey(user) && this.addrMap.containsKey(user)) {
			try {
				this.log("[TCP]Initiating tcp connection");
				Socket distant = new Socket(this.addrMap.get(user), this.tcpDistantPort);
				this.log("Socket created,link protocols started");
				TCPUserLink link = new TCPUserLink(user, distant);
				LinkTCPUserProtocolInit init = new LinkTCPUserProtocolInit(this, link, LinkTCPUserProtocolInit.Mode.CONNECT, user);
				this.executeProtocol(new LinkTCPUserProtocol(init), parallel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			this.log("[TCP]Unknown identifier, unable to create connection");
		}
	}

	/**
	 * Register a TCP connection in the network manager table
	 * @param identifier distant user id
	 * @param link an initialized tcp connection with socket and streams ready
	 */
	public void linkTCP(String identifier, TCPUserLink link) {
		synchronized (this.tcpConnections) {
			if (this.getTCPLinkFor(identifier) == null) {
				this.tcpConnections.put(identifier, link);
			} else {
				this.log("Network manager already has an active link with user " + identifier);
			}
		}
	}


	/**
	 * If existing, closes the current tcp socket and streams associated with the distant user
	 * @param identifier distant user id
	 */
	public synchronized void closeConnectionTCP(String identifier) {
		if (this.tcpConnections.containsKey(identifier)) {
			try {
				this.log("[TCP]Closing TCP connection with user " + identifier);
				this.tcpConnections.get(identifier).getDistant().close();
				this.tcpConnections.remove(identifier);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send a packet to all the local network
	 * @param bytes the packet to broadcast
	 */
	public void broadcast(byte[] bytes) {
		this.UDP_Send(bytes, this.broadcastAddress);
	}

	public void UDP_Send(byte[] bytes, InetAddress address) {
		synchronized (this.sendSocketUDP) {
			try {
				DatagramPacket packetUDP = new DatagramPacket(bytes, bytes.length, address, this.udpDistantPort);
				this.sendSocketUDP.send(packetUDP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean TCP_IP_send(String id, CLVPacket packet) {
		TCPUserLink link = this.getTCPLinkFor(id);
		if (link == null) {
			this.log("No TCP link established with user " + id);
			this.log("Trying to initiate connection");
			this.initiateConnectionTCP(id, false);
			link = this.getTCPLinkFor(id);
		}
		if (link != null) {
			this.log("[TCP]Sending packet to user " + id);
			link.send(packet);
			return true;
		} else {
			this.log("Unable to initiate connection with user " + id);
			return false;
		}
	}

	private void executeProtocol(Protocol protocol) {
		this.protocolService.execute(protocol);
	}

	public void executeProtocol(Protocol protocol, boolean parallel) {
		if(parallel){
			this.executeProtocol(protocol);
		}
		else{
			protocol.run();
		}


	}

	public void startUDPListening() {
		this.startBroadcastListening(new UDPListenerProtocol(new ProtocolInit(this)));
	}

	public void startUDPSignal() {
		this.startActivitySignal(new ActivitySignalProtocol(new ActivitySignalProtocolInit(this)));
	}


	public void startTCPListening() {
		this.executeProtocol(new TCPListenerProtocol(new ProtocolInit(this)));
	}

	public void startActivitySignal(ActivitySignalProtocol protocol) {
		this.signalProtocolService.execute(protocol);
	}

	public void startBroadcastListening(UDPListenerProtocol protocol) {
		this.broadcastListeningProtocolService.execute(protocol);
	}

	public void stopActivitySignal() {
		System.out.println("Shutting down udp signal");
		this.signalProtocolService.shutdown();
		this.signalProtocolService.shutdownNow();
		this.signalProtocolService = Executors.newSingleThreadExecutor();
	}

	public void stopBroadcastListening() {
		System.out.println("Shutting down broadcast listening");
		this.broadcastListeningProtocolService.shutdown();
		this.broadcastListeningProtocolService.shutdownNow();
		this.broadcastListeningProtocolService = Executors.newSingleThreadExecutor();
	}

	public AppHandler getAppHandler() {
		return this.appHandler;
	}

	public void setAppHandler(AppHandler appHandler) {
		this.appHandler = appHandler;
	}

	public void addAddrFor(String identifier, InetAddress addr) {
		synchronized (this.addrMap) {
			this.addrMap.put(identifier, addr);
		}
	}
	public void deleteAddrFor(String identifier){
		synchronized (this.addrMap){
			this.addrMap.remove(identifier);
		}
	}

	public String getAddrFor(String identifier) {
		return this.addrMap.get(identifier).toString();
	}

	private TCPUserLink getTCPLinkFor(String user) {
		return this.tcpConnections.get(user);
	}

	public DatagramSocket getSendSocketUDP() {
		return sendSocketUDP;
	}

	public DatagramSocket getReceiveSocketUDP() {
		return receiveSocketUDP;
	}

	public void linkPresenceServer(PresenceClient client){
		synchronized (this.presenceClients) {
			if(!this.presenceClients.contains(client)) {
				this.log("Subscribing to presence server");
				client.subscribe(this.getAppHandler().getMainUser().getIdentifier());
				this.presenceClients.add(client);
			}
		}
	}

	public HashSet<PresenceClient> getPresenceClients() {
		return presenceClients;
	}

	public void setBroadcastAddress(InetAddress broadcastAddress) {
		this.broadcastAddress = broadcastAddress;
	}

}
