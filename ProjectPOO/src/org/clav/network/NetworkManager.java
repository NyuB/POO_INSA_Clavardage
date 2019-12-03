package org.clav.network;

import org.clav.Agent;
import org.clav.user.User;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class NetworkManager {
	public static int UDPSOCKET_SEND = 1034;
	public static int UDPSOCKET_RECEIVE = 1035;
	public static int TCPSOCKET_SEND = 1044;
	public static int TCP_SOCKET_RECEIVE = 1045;
	private Agent relatedAgent;

	public Agent getRelatedAgent() {
		return relatedAgent;
	}

	private InetAddress networkAddress;
	private InetAddress broadcastAddress;
	private HashMap<User, InetAddress> addrMap;
	private DatagramSocket sendSocketUDP;
	private DatagramSocket receiveSocketUDP;
	private HashMap<User, TCPUserLink> tcpConnections;

	TCPUserLink getTCPLinkFor(User user){
		return this.tcpConnections.get(user);
	}

	synchronized void initiateConnectionTCP(User user){
		if (this.tcpConnections.containsKey(user)) {
			try {
				System.out.println("Attempting TCP connections with user "+user.getIdentifier());
				Socket socket = new Socket(addrMap.get(user),TCP_SOCKET_RECEIVE);
				System.out.println("TCP Connection with user "+user.getIdentifier()+" INITIATED");
				new PrintWriter(new OutputStreamWriter(socket.getOutputStream())).write(this.getRelatedAgent().getUserManager().getMainUser().getIdentifier());
				String ack = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
				if(ack.equals("ACK")){
					tcpConnections.put(user,new TCPUserLink(user,socket));
					System.out.println("TCP Connection with user "+user.getIdentifier()+" SUCCESS");
				}



			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	synchronized void closeConnectionTCP(User user){
		if(this.tcpConnections.containsKey(user)){
			try {
				System.out.println("Closing TCP connection with user "+user.getIdentifier());
				this.tcpConnections.get(user).getDistant().close();
				this.tcpConnections.remove(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	synchronized void addConnectionTCP(User user,Socket distant){
		if(this.getTCPLinkFor(user)==null){
			TCPUserLink link = new TCPUserLink(user,distant);
			this.tcpConnections.put(user,link);
		}
		else{
			System.out.println("Network manager already has an established connection with user "+user.getIdentifier());
		}
	}

	public static NetworkManager testModeNetworkManager(InetAddress networkAddress, InetAddress broadcastAddress, DatagramSocket sendSocketUDP, DatagramSocket receiveSocketUDP){
		return new NetworkManager(networkAddress,broadcastAddress,sendSocketUDP,receiveSocketUDP);
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

	public void connect() {
		//TODO
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

	public void TCP_IP_send(Object data, InetAddress address) {
		//TODO
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
}
