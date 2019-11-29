package org.clav.network;

import org.clav.user.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

public class NetworkManager {
	public static int UDPSOCKET_SEND = 1034;
	public static int UDPSOCKET_RECEIVE = 1035;
	public static int TCPSOCKET_SEND = 1044;
	public static int TCP_SOCKET_RECEIVE = 1045;
	private InetAddress networkAddress;
	private InetAddress broadcastAddress;
	private HashMap<User, InetAddress> addrMap;
	private DatagramSocket sendSocketUDP;
	private DatagramSocket receiveSocketUDP;

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
		try {
			this.receiveSocketUDP = new DatagramSocket(UDPSOCKET_RECEIVE);
			this.sendSocketUDP = new DatagramSocket(UDPSOCKET_SEND);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	private NetworkManager(InetAddress networkAddress, InetAddress broadcastAddress, DatagramSocket sendSocketUDP, DatagramSocket receiveSocketUDP) {

		this.networkAddress = networkAddress;
		this.broadcastAddress = broadcastAddress;
		this.sendSocketUDP = sendSocketUDP;
		this.receiveSocketUDP = receiveSocketUDP;
	}

	public void executeProtocol(Protocol protocol) {
		Thread t = new Thread(protocol);
		t.start();
	}
}
