package org.clav.network.server;

import org.clav.network.CLVPacket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.util.HashMap;

public class HttpPresenceServlet extends HttpServlet {
	private HashMap<String, InetAddress> registered = new HashMap<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectInputStream objectInputStream = new ObjectInputStream(req.getInputStream());
		try {
			CLVPacket packet = (CLVPacket) objectInputStream.readObject();
			switch (packet.header) {
				case PUB:
					break;
				case SUB:
					ServerSubcription sub = (ServerSubcription) packet.data;
					break;
				default:
					break;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
