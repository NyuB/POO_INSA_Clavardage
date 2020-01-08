package org.clav.network.server;

import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.utils.Serializer;
import org.clav.utils.constants.NetworkConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

public class HttpPresenceServlet extends HttpServlet {
	private HashMap<String, InetAddress> registered = new HashMap<>();
	private int nbPost = 0;
	private int errors = 0;
	private String lastMsg = "";
	int nbGet = 0;

	private void UDPSend(CLVPacket packet, InetAddress address) {
		try {
			DatagramSocket socket = new DatagramSocket();
			byte[] data = Serializer.toBytes(packet);
			socket.send(new DatagramPacket(data, 0, data.length, address, NetworkConstants.UDPSOCKET_RECEIVE_PORT));
		} catch (Exception e) {
			errors++;
			e.printStackTrace();
			lastMsg = e.toString();

		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/html");
		nbGet++;
		try {
			String saddr = req.getRemoteAddr();
			InetAddress distAddr = InetAddress.getByName(saddr);
			PrintWriter out = res.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Test Servlet</title>");
			out.println("</head>");
			out.println("<body>");
			StringBuilder sb = new StringBuilder();
			for (String key : this.registered.keySet()) {
				sb.append(key + " " + this.registered.get(key).toString() + " ");
			}
			out.println("<h1>" + "NBREQ : " + nbGet + " " + nbPost + " NBERRORS : " + errors + " REGISTERED : " + sb.toString() +" "+ lastMsg+"</h1>");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		nbPost++;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(req.getInputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(resp.getOutputStream());
			CLVPacket packet = (CLVPacket) objectInputStream.readObject();
			switch (packet.header) {
				case PUB:
					ServerPublication pub = (ServerPublication) packet.data;
					CLVPacket routed = CLVPacketFactory.gen_NOT(pub.getUser(), InetAddress.getByName(req.getRemoteAddr()));
					for (String id : this.registered.keySet()) {
						if (true || !id.equals(pub.getUser().getIdentifier())) {//TODO Stop sending to yourself for production
							this.UDPSend(routed, this.registered.get(id));
						}
					}
					break;
				case SUB:
					ServerSubcription sub = (ServerSubcription) packet.data;
					this.registered.put(sub.getId(), InetAddress.getByName(req.getRemoteAddr()));
					break;
				default:
					break;
			}
			objectOutputStream.writeObject(CLVPacketFactory.gen_ACK());
			objectOutputStream.flush();
			objectOutputStream.close();
			objectInputStream.close();
		} catch (Exception e) {
			errors++;
			e.printStackTrace();
			lastMsg=e.toString();
		}

	}
}
