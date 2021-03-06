package org.clav.config;

import org.clav.utils.constants.NetworkConstants;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Installer {
	
	public void install(){
		File config = new File("./Objconfig.ser");
		File db = new File("./db/mydb.script");
		if (config.exists()) {
			System.out.println("Config OK") ;
		}
		else {
			System.out.println("No configuration file ObjConfig.ser found") ;
			createDefaultConfig() ;
			System.out.println("Default config applied");
		}
		if (db.exists()) {
			System.out.println("DB OK") ;
		}
		else {
			System.out.println("No database found") ;
			createDB() ;
			System.out.println("Database created");
		}
	}
	
	private void createDB() {
		System.out.println("Installing database, please wait...");
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
			e.printStackTrace();
		}
		
	}
	
	private void createDefaultConfig() {
		Config config;
		System.out.println("Creating default configuration, please wait...");
		try {
			config = new Config( InetAddress.getLocalHost() ,InetAddress.getByName("255.255.255.255"), NetworkConstants.GEI_SERVER_URL, true, true, true, false);
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
			e.printStackTrace();
		}
		*/

	}
}
