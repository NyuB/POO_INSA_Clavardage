package org.clav.network;

import org.clav.chat.ChatInit;
import org.clav.chat.ChatUnknown;
import org.clav.chat.Message;
import org.clav.user.PseudoRejection;
import org.clav.user.User;

import java.util.Date;

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

}
