package org.clav.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.clav.chat.Message;
import org.clav.config.Installer;
import org.clav.user.User;

public class TestLocalDB {
	
	public static void main(String[] args) {
		LocalStorage db = new LocalStorage() ;
		ArrayList<User> members = new ArrayList<User>() ; 
		members.add(new User("bob", "bob")) ;
		members.add(new User("michel", "michel")) ;
		db.storeMessage(new Message("id", "hash", "Je Suis Un Test")) ;
		ResultSet rs = db.request("select * from Chats") ;
		try {
			while(rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getDate(3) + " " + rs.getString(4)) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
