package org.clav.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;

import org.clav.Agent;
import org.clav.chat.Chat;
import org.clav.chat.History;
import org.clav.chat.Message;

import java.sql.*;

public class LocalStorage implements ChatStorage {
	
	private Connection con ;
	private Statement stm ;
	private ResultSet rs ;
	
	public LocalStorage() {
		stm = null ;
		rs = null ;
		connect() ;
	}
	
	private void connect() {
		//Registering the HSQLDB JDBC driver
        try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			//this.con = DriverManager.getConnection("jdbc:hsqldb:file:~/hsqldb-2.5.0/hsqldb/testdb", "SA", "");
			this.con = DriverManager.getConnection("jdbc:hsqldb:file:db/mydb", "SA", "") ;
			if (con!= null){
	            System.out.println("Connection created successfully");
	            stm = con.createStatement() ;
	         }
			else{
	            System.out.println("Problem with creating connection");
	            
	         }
			
		} 
        catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			con.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet request(String sqlquerry) {
		rs = null ;
		
		try {
			stm = con.createStatement() ;
			rs = stm.executeQuery(sqlquerry) ;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs ;
	}

	@Override
	public void storeChat(Chat chat) {
		try {
			stm = con.createStatement() ;
			ArrayList<Message> messages = chat.getHistory().getMessageHistory() ;
			for (Message m : messages) {
				storeMessage(m) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public Chat getChatByHashCode(String code) {
		ResultSet rs = this.request("select CodeChat, userid from Chats Where CodeChat='" + code + "'" ) ;
		ArrayList<String> members = new ArrayList<String>() ;
		History hist = new History() ;
		try {
			while(rs.next()) {
				members.add(rs.getString(2)) ;
			}
			rs = this.request("select CodeChat,userid , Date, Text from Chats Where CodeChat='" + code + "'" ) ;
			while(rs.next()) {
				hist.insertMessage(new Message(rs.getString(2), rs.getString(1), rs.getString(4), new Date(rs.getTimestamp(3).getTime()) )) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Chat chat = new Chat(members) ;
		chat.setHistory(hist) ;
		return chat ;
	}

	@Override
	public Iterable<Chat> readAllChats() {
		// TODO Auto-generated method stub
		ResultSet rs = this.request("select distinct CodeChat from Chats " ) ;
		ArrayList<Chat> chats = new ArrayList<Chat>() ;
		try {
			while(rs.next()) {
				chats.add(getChatByHashCode(rs.getString(1))) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void storeAll(Iterable<Chat> chats) {
		for (Chat c : chats) {
			storeChat(c) ;
		}
	}

	@Override
	public void storeMessage(Message message) {
		Timestamp date = new Timestamp(message.getDate().getTime()) ;
		String chat = message.getChatHashCode() ;
		String userID = message.getUserID() ;
		String text = message.getText() ;
		String querry = "Insert Into Chats (codechat, userid, date, text) Values (?,?,?,?)" ; 
		//System.out.println(querry) ;
		try {
			PreparedStatement pstm = con.prepareStatement(querry) ;
			pstm.setString(1,chat) ;
			pstm.setString(2,userID) ;
			pstm.setTimestamp(3,date) ;
			pstm.setString(4,text) ;
			pstm.executeUpdate() ;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
