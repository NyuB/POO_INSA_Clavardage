package org.clav;

import org.clav.network.NetworkManager;

import java.net.InetAddress;
import java.util.Scanner;

public class ProtoSnd {
	public static void main(String[] args) {
		NetworkManager networkManager = new NetworkManager(null,null);
		Scanner in= new Scanner(System.in);
		String line;
		while((line=in.nextLine())!=null && !(line.equals("END"))){
			byte[] buf = line.getBytes();
			networkManager.UDP_Send(buf,null);
		}
	}

}
