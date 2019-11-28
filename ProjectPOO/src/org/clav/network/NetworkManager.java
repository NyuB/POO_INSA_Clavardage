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
	private HashMap<User,InetAddress> addrMap;
	public void connect(){
		//TODO
	}
	public void broadcast(byte[] bytes){
		//TODO
	}
	public void UDP_Send(byte[] bytes,InetAddress address){
		try {
			DatagramSocket socketUDP = new DatagramSocket(UDPSOCKET_SEND);
			DatagramPacket packetUDP = new DatagramPacket(bytes,bytes.length,address, UDPSOCKET_RECEIVE);
			socketUDP.send(packetUDP);

		} catch (SocketException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	public void TCP_IP_send(Object data, InetAddress address){
		//TODO
	}

	public NetworkManager(InetAddress networkAddress, InetAddress broadcastAddress) {
		this.networkAddress = networkAddress;
		this.broadcastAddress = broadcastAddress;
		this.addrMap = new HashMap<>();
	}

	public void executeProtocol(Protocol protocol){
		Thread t = new Thread(protocol);
		t.start();
	}
}
