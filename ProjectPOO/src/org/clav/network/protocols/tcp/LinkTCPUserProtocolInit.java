package org.clav.network.protocols.tcp;

import org.clav.network.NetworkManager;
import org.clav.network.ProtocolInit;
import org.clav.network.TCPUserLink;

/**
 * Settings for connection initiation with a distant user
 */
public class LinkTCPUserProtocolInit extends ProtocolInit {
	public enum Mode{
		CONNECT,
		ACCEPT;
	}
	private TCPUserLink link;
	private Mode mode;
	private String distantID;

	/**
	 * Constructor to initialize
	 * @param networkManager  The related network manager executing the connection protocol
	 * @param link The connection to set
	 * @param mode Specify if the protocol is launched in connection(user initiating the connection) or accept mode(user receiving connection request)
	 *             Should be Accept for this constructor
	 */
	public LinkTCPUserProtocolInit(NetworkManager networkManager, TCPUserLink link, Mode mode) {
		super(networkManager);
		this.link = link;
		this.mode = mode;
	}

	/**
	 * @param networkManager The related network manager executing the connection protocol
	 * @param link The connection to set
	 * @param mode Specify if the protocol is launched in connection(user initiating the connection) or accept mode(user receiving connection request)
	 *             Should be connect for this constructor
	 * @param distantID Distant user identifier
	 */
	public LinkTCPUserProtocolInit(NetworkManager networkManager, TCPUserLink link, Mode mode, String distantID) {
		super(networkManager);
		this.link = link;
		this.mode = mode;
		this.distantID = distantID;
	}

	public Mode getMode() {
		return mode;
	}
	public TCPUserLink getLink(){
		return this.link;
	}

	public String getDistantID() {
		return distantID;
	}
}
