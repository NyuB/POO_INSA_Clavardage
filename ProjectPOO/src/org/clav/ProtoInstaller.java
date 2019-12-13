package org.clav;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.clav.config.Config;
import org.clav.config.Installer;

public class ProtoInstaller {
	
	public static void main(String[] args) {
		Installer installer = new Installer() ; 
		try {
			Config config = new Config(InetAddress.getByName("localhost"), InetAddress.getByName("localhost"), "ID", true, true, true) ;
			config.save() ;
			FileInputStream file = new FileInputStream("Objconfig.ser") ;
			ObjectInputStream in = new ObjectInputStream(file) ;
			Config test = (Config) in.readObject() ;
			System.out.println(test) ;
			//installer.createDefaultConfig(".", (short) 16) ;
			
		} 
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
