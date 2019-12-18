package org.clav.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;


import org.clav.chat.Chat;
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
		// TODO Auto-generated method stub
		try {
			stm = con.createStatement() ;
			//String query = "Create Table Chats " + "(codeChat VARCHAR(256) ,userid VARCHAR(), date Date, text Text, Primary Key (codeChat, date) ) "  ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String[] query = new String[3] ;
		//String query = "Create Table Chat (ChatId VARCHAR[255], MessageId INT AUTO_INCREMENT, Primary Key(ChatId,MessageId))" ;
		
	}

	@Override
	public Chat getChatByHashCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Chat> readAllChats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeAll(Iterable<Chat> chats) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeMessage(Message message) {
		// TODO Auto-generated method stub
		Date date = new Date(message.getDate().getTime()) ;
		String chat = message.getChatHashCode() ;
		String userID = message.getUserID() ;
		String text = message.getText() ;
		String querry = "Insert Into Chats (codechat, userid, date, text) Values (?,?,?,?)" ; 
		//System.out.println(querry) ;
		try {
			PreparedStatement pstm = con.prepareStatement(querry) ;
			pstm.setString(1,chat) ;
			pstm.setString(2,userID) ;
			pstm.setDate(3,date) ;
			pstm.setString(4,text) ;
			pstm.executeUpdate() ;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
