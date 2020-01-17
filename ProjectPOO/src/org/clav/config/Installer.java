package org.clav.config;

import org.clav.utils.constants.NetworkConstants;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
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
		File config = new File("./Objconfig.ser");
		File db = new File("./db/mydb.script");
		if (config.exists()) {
			System.out.println("Config OK") ;
		}
		else {
			System.out.println("Config Not OK") ;
			Scanner in = new Scanner(System.in);
			createDefaultConfig() ;
			System.out.println("Config created");
		}
		if (db.exists()) {
			System.out.println("DB OK") ;
		}
		else {
			System.out.println("DB Not OK") ;
			createDB() ;
			System.out.println("DB created");
		}
	}
	
	public void createDB() {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			Connection con = DriverManager.getConnection("jdbc:hsqldb:file:db/mydb", "SA", "") ;
			Statement stm = con.createStatement() ;
			String query = "CREATE TABLE Chats(codeChat VARCHAR(32) ,userid VARCHAR(25),date TIMESTAMP, text VARCHAR(1024), PRIMARY KEY (codeChat, date) ) "  ;
			String membersTable = "CREATE TABLE Members(codeChat VARCHAR(32) ,userid VARCHAR(25) )";
			stm.executeUpdate(query) ;
			stm.executeUpdate(membersTable);
			con.close() ;
		} 
		catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createDefaultConfig() {
		Config config;
		try {
			config = new Config( InetAddress.getLocalHost() ,InetAddress.getByName("255.255.255.255"), NetworkConstants.GEI_SERVER_URL, true, true, true, true);
			config.save() ;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		/*
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
				config = new Config( address ,inter.getBoradcast(), NetworkConstants.GEI_SERVER_URL, true, true, true, false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

	}
}
