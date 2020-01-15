package org.clav.config;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Installer {
	
	public void install(){
		File config = new File("./Objconfig.ser") ;
		File db = new File("./db/mydb.script") ;
		if (config.exists()) {
			System.out.println("Config OK") ;
		}
		else {
			System.out.println("Config Not OK") ;
			Scanner in = new Scanner(System.in);
			System.out.println("Enter mask of the address");
			short mask = Short.valueOf(in.nextLine());
			createDefaultConfig(mask) ;
		}
		if (db.exists()) {
			System.out.println("DB OK") ;
		}
		else {
			System.out.println("DB Not OK") ;
			createDB() ;
		}
	}
	
	public void createDB() {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			Connection con = DriverManager.getConnection("jdbc:hsqldb:file:db/mydb", "SA", "") ;
			Statement stm = con.createStatement() ;
			String query = "Create Table Chats(codeChat VARCHAR(32) ,userid VARCHAR(25),date TIMESTAMP, text VARCHAR(1024), Primary Key (codeChat, date) ) "  ;
			stm.executeUpdate(query) ;
			con.close() ;
		} 
		catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createDefaultConfig(short mask) {
		try {
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
						//System.out.println(address) ;
						right = true ;
						find = true ;
					}
				}
			}
			
			if(address!=null) {
				Config config = new Config( address , inter.getBroadcast() , address.getHostName(), true, true, true ) ;
				config.save() ;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
