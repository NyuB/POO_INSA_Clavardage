package org.clav.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

public class Installer {
	
	public void install(){

	}
	
	public void createDefaultConfig(String path, byte[] mask) {
		File config = new File(path+"/test_config.txt") ;
		try {
			PrintWriter out = new PrintWriter(new FileWriter(config)) ;
			
			//get interface 
			//TODO recuperer la bonne interface selon le mask du reseau donné
			Enumeration<NetworkInterface> enumNet = NetworkInterface.getNetworkInterfaces() ; 
			boolean find = false ;
			NetworkInterface net = enumNet.nextElement() ;
			while (!find) {
				List<InterfaceAddress> address = net.getInterfaceAddresses() ;
			}
			
			InetAddress inadd = InetAddress.getLocalHost() ;
			NetworkInterface netInt = NetworkInterface.getByInetAddress(inadd);
			
			//write file
			//out.println("ID " + System.getProperty("user.home")) ;
			out.println("ID " + inadd.getHostName()) ;
			out.println("LOCAL " + inadd.getHostAddress()) ;
			out.println("BROAD " + netInt.getInterfaceAddresses().get(0).getBroadcast()) ;
			out.close() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
