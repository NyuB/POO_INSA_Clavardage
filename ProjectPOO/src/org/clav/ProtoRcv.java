package org.clav;

import org.clav.network.NetworkManager;
import org.clav.network.ProtocolInit;
import org.clav.network.UDPListenerProtocol;

import java.util.Scanner;

public class ProtoRcv {
	public static void main(String[] args) {
		NetworkManager networkManager = new NetworkManager(null,null);
		networkManager.executeProtocol(new UDPListenerProtocol(new ProtocolInit(networkManager)));

	}
}
