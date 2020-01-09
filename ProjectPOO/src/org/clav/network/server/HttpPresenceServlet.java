package org.clav.network.server;

import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.user.ActivityHandler;
import org.clav.user.ActivityTimerTask;
import org.clav.utils.Serializer;
import org.clav.utils.constants.DelayConstants;
import org.clav.utils.constants.NetworkConstants;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Timer;

public class HttpPresenceServlet extends HttpServlet implements ActivityHandler {
	private final HashMap<String, InetAddress> registered = new HashMap<>();
	private final HashMap<String, ActivityTimerTask> timers = new HashMap<>();
	private int nbPost = 0;
	private int errors = 0;
	private String lastMsg = "";
	int nbGet = 0;

	@Override
	public void removeActiveUser(String id) {
		synchronized (this.registered) {
			this.registered.remove(id);
		}
		synchronized (this.timers) {
			this.timers.remove(id);
		}
	}

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
			PrintWriter out = res.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Test Servlet</title>");
			out.println("</head>");
			out.println("<body>");
			StringBuilder sb = new StringBuilder("<h3>Active users</h3><ul>");
			for (String key : this.registered.keySet()) {
				sb.append("<li>" + key + " : " + this.registered.get(key).toString() + "</li>");
			}
			sb.append("</ul>");
			out.println("<h2>Presence Server Gouvine-Birrer | Decaestecker</h2>");
			out.println("<p>Stats : <br/>Number of GET request : " + nbGet + "<br/>Number of POST Request : " + nbPost + "<br/>Number of errors : " + errors + "<br/>Last error message : " + lastMsg + "</p>");
			out.println("<p>" + sb.toString() + "</p>");
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
					this.timers.get(pub.getUser().getIdentifier()).setCounter(DelayConstants.INACTIVE_DELAY_SEC);
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
					ActivityTimerTask activityTimerTask = new ActivityTimerTask(DelayConstants.INACTIVE_DELAY_SEC, sub.getId(), this);
					this.timers.put(sub.getId(),activityTimerTask);
					Timer timer = new Timer();
					timer.schedule(activityTimerTask,0,1000);
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
			lastMsg = e.toString();
		}

	}
}
