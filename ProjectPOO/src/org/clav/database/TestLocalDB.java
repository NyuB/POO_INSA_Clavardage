package org.clav.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.clav.chat.Chat;
import org.clav.config.Installer;

public class TestLocalDB {
	
	public static void main(String[] args) {
		
		Installer installer = new Installer() ;
		installer.install() ;
		//installer.createDB() ;
		
		LocalStorage db = new LocalStorage() ;
		Chat chat = db.getChatByHashCode("test") ;
		//db.storeMessage(new Message("id1", "test", "Je Suis Un Envoi")) ;
		//db.storeMessage(new Message("id2", "test", "Je Suis Une reponse")) ;
		ResultSet rs = db.request("select * from Chats") ;
		try {
			while(rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + new Date(rs.getTimestamp(3).getTime()) + " " + rs.getString(4)) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close() ;
	}

}
