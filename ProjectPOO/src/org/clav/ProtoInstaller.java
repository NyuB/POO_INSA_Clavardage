package org.clav;

import org.clav.config.ConfigManager;
import org.clav.config.Installer;

public class ProtoInstaller {
	
	public static void main(String[] args) {
		Installer installer = new Installer() ; 
		/*
		try {
			Config config = new Config(InetAddress.getByName("10.1.5.128"), InetAddress.getByName("10.1.255.255"), "ID", true, true, true) ;
			System.out.println(config) ;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//installer.createDefaultConfig((short) 16) ;
		//config.save() ;
		Agent agent = new Agent() ;
		ConfigManager manager = new ConfigManager() ;
		manager.configNetworkManager(agent) ;
		//System.out.println(agent.getNetworkManager()) ;
		
		System.out.println(manager.getConfig()) ;
		//installer.createDefaultConfig(".", (short) 16) ;
	}
}
