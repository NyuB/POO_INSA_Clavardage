package org.clav;

import org.clav.config.Installer;

public class ProtoInstaller {
	
	public static void main(String[] args) {
		Installer installer = new Installer() ; 
		installer.createDefaultConfig(".", (short) 16) ;
	}
}
