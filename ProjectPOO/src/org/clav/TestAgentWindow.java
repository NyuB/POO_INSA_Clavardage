package org.clav;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestAgentWindow {
	
	public static void main(String[] args) {
		try {
			InetAddress addr = InetAddress.getByName("localhost");
			System.out.println(addr.getHostAddress());
			InetAddress broadcasr = InetAddress.getByName("10.191.255.255");
			System.out.println(broadcasr.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
