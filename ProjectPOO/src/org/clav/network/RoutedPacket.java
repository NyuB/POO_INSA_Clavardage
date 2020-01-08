package org.clav.network;

import java.net.InetAddress;

public class RoutedPacket {
	private CLVPacket packet;
	private InetAddress origin;

	public RoutedPacket(CLVPacket packet, InetAddress origin) {
		this.packet = packet;
		this.origin = origin;
	}

	public CLVPacket getPacket() {
		return packet;
	}

	public InetAddress getOrigin() {
		return origin;
	}
}
