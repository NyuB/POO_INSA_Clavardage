package org.clav.network;

import java.io.Serializable;

public class CLVPacket implements Serializable {
	public CLVHeader header;
	public Object data;
	CLVPacket(CLVHeader header, Object data) {
		this.header = header;
		this.data = data;
	}
}
