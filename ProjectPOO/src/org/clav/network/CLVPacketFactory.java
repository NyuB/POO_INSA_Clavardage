package org.clav.network;

import org.clav.chat.ChatInit;
import org.clav.chat.ChatUnknown;
import org.clav.chat.Message;
import org.clav.network.server.ServerNotification;
import org.clav.network.server.ServerPublication;
import org.clav.network.server.ServerSubcription;
import org.clav.user.PseudoRejection;
import org.clav.user.User;

import java.net.InetAddress;

import static org.clav.network.CLVHeader.*;

public class CLVPacketFactory {

	public static CLVPacket gen_MSG(Message message){
		return new CLVPacket(MSG,message);
	}
	public static CLVPacket gen_ACK(){
		return new CLVPacket(ACK,null);
	}
	public static CLVPacket gen_STR(String s){
		return new CLVPacket(STR,s);
	}
	public static CLVPacket gen_CHI(ChatInit init){
		return new CLVPacket(CHI,init);
	}
	public static CLVPacket gen_UNK(String id,String chatHashCode){
		return new CLVPacket(UNK, new ChatUnknown(id,chatHashCode));
	}
	public static CLVPacket gen_SIG(User u){
		return new CLVPacket(SIG,u);
	}
	public static CLVPacket gen_ERR(){
		return new CLVPacket(ERR,null);
	}
	public static CLVPacket gen_REJ(PseudoRejection rejection){
		return new CLVPacket(REJ,rejection);
	}
	public static CLVPacket gen_PUB(User user){
		return new CLVPacket(PUB,new ServerPublication(user));
	}
	public static CLVPacket gen_SUB(String id){
		return new CLVPacket(SUB, new ServerSubcription(id));
	}
	public static CLVPacket gen_NOT(User user, InetAddress address){
		return new CLVPacket(NOT,new ServerNotification(user,address));
	}

}
