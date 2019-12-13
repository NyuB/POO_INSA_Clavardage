package org.clav.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class Installer {
	
	public void install(){

	}
	
	public void createDefaultConfig(String path, short mask) {
		File config = new File(path+"/test_config.txt") ;
		try {
			PrintWriter out = new PrintWriter(new FileWriter(config)) ;
			//get interface 
			Enumeration<NetworkInterface> enumNet = NetworkInterface.getNetworkInterfaces() ; 
			boolean find = false ;
			boolean right = false ;
			InetAddress address = null ;
			NetworkInterface net = null ;
			InterfaceAddress inter = null ;
			while (!find && enumNet.hasMoreElements()) {
				net = enumNet.nextElement() ;
				List<InterfaceAddress> l_add = net.getInterfaceAddresses() ;
				Iterator<InterfaceAddress> iterator = l_add.iterator();
				while (!right && iterator.hasNext()) {
					inter = iterator.next() ;
					if (inter.getNetworkPrefixLength() == mask) {
						address = inter.getAddress() ; 
						System.out.println(address) ;
						right = true ;
						find = true ;
					}
				}
			}
			
			if(address!=null) {
				out.println("ID " + System.getProperty("user.home")) ;
				//out.println("ID " + address.getHostName()) ;
				out.println("LOCAL " + address.getHostAddress()) ;
				out.println("BROAD " + inter.getBroadcast()) ;
				out.println("UDP LISTEN SIGNAL") ;
				out.println("TCP LISTEN") ;
				out.close() ;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
