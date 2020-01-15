package org.clav.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;


public class Config implements Serializable{

	private InetAddress localAddr;
	private InetAddress broadcastAddr;
	private boolean autoSignalUDP;
	private boolean autoListenUDP;
	private boolean autoListenTCP;
	private boolean autoConnectServlet;
	private String serverUrl;
	
	public Config (InetAddress local, InetAddress broadcast, String serverUrl, boolean sig, boolean udp, boolean tcp, boolean server ) {
		this.localAddr = local ;
		this.broadcastAddr = broadcast ;
		this.serverUrl = serverUrl;
		this.autoSignalUDP = sig ;
		this.autoListenUDP = udp ;
		this.autoListenTCP = tcp ;
		this.autoConnectServlet = server;
	}
	
	public void save() {
		try {
			FileOutputStream file = new FileOutputStream("Objconfig.ser") ;
			ObjectOutputStream out = new ObjectOutputStream(file) ;
			out.writeObject(this) ;
			out.flush() ;
			out.close() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "Config [localAddr=" + localAddr.getHostAddress() + ", broadcastAddr=" + broadcastAddr
				+ ", autoSignalUDP=" + autoSignalUDP + ", autoListenUDP=" + autoListenUDP + ", autoListenTCP="
				+ autoListenTCP + "]";
	}

	public InetAddress getLocalAddr() {
		return localAddr;
	}

	public InetAddress getBroadcastAddr() {
		return broadcastAddr;
	}

	public boolean isAutoSignalUDP() {
		return autoSignalUDP;
	}

	public boolean isAutoListenUDP() {
		return autoListenUDP;
	}

	public boolean isAutoListenTCP() {
		return autoListenTCP;
	}

	public boolean isAutoConnectServlet() {
		return autoConnectServlet;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setLocalAddr(InetAddress localAddr) {
		this.localAddr = localAddr;
	}

	public void setBroadcastAddr(InetAddress broadcastAddr) {
		this.broadcastAddr = broadcastAddr;
	}

	public void setAutoSignalUDP(boolean autoSignalUDP) {
		this.autoSignalUDP = autoSignalUDP;
	}

	public void setAutoListenUDP(boolean autoListenUDP) {
		this.autoListenUDP = autoListenUDP;
	}

	public void setAutoListenTCP(boolean autoListenTCP) {
		this.autoListenTCP = autoListenTCP;
	}

	public void setAutoConnectServlet(boolean autoConnectServlet) {
		this.autoConnectServlet = autoConnectServlet;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
}
