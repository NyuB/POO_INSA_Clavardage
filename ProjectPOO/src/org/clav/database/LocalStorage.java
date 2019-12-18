package org.clav.database;

import org.clav.chat.Chat;
import org.clav.chat.Message;

import java.sql.*;

public class LocalStorage implements ChatStorage {
	
	private Connection con ;
	
	public LocalStorage() {
		connect() ;
	}
	
	private void connect() {
		//Registering the HSQLDB JDBC driver
        try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			this.con = DriverManager.getConnection("jdbc:hsqldb:file:~/hsqldb-2.5.0/hsqldb/testdb", "SA", "");
			if (con!= null){
	            System.out.println("Connection created successfully");
	            
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
		ResultSet rs = null ;
		try {
			Statement statement = con.createStatement() ;
			rs = statement.executeQuery(sqlquerry) ;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs ;
	}

	@Override
	public void storeChat(Chat chat) {
		// TODO Auto-generated method stub
		
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
		
	}

}
