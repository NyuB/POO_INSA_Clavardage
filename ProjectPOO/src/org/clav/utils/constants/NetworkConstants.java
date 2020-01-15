package org.clav.utils.constants;

public final class NetworkConstants {
	private NetworkConstants(){
		//Ensure no instance of this class can be created
		assert(false);
	}
	//PORTS
	public static int UDPSOCKET_SEND_PORT = 1034;
	public static int UDPSOCKET_RECEIVE_PORT = 1035;
	public static int TCP_SOCKET_SERVER_PORT = 1045;

	//URL
	public static String GEI_SERVER_URL = "https://srv-gei-tomcat.insa-toulouse.fr/gouvine_decaestecker/presence";
}
