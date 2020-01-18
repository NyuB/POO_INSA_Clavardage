package org.clav.database;


import org.clav.chat.Chat;
import org.clav.chat.History;
import org.clav.chat.Message;

import java.sql.*;
import java.util.ArrayList;

public class LocalStorage implements ChatStorage {

	private Connection con;
	private Statement stm;
	private ResultSet rs;

	public LocalStorage() {
		stm = null;
		rs = null;
		connect();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				close();
			}
		});
	}

	private void connect() {
		//Registering the HSQLDB JDBC driver
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			//this.con = DriverManager.getConnection("jdbc:hsqldb:file:~/hsqldb-2.5.0/hsqldb/testdb", "SA", "");
			this.con = DriverManager.getConnection("jdbc:hsqldb:file:db/mydb", "SA", "");
			if (con != null) {
				System.out.println("Connection created successfully");
				stm = con.createStatement();
			} else {
				System.out.println("Problem with creating connection");
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet request(String sqlquerry) {
		rs = null;

		try {
			stm = con.createStatement();
			rs = stm.executeQuery(sqlquerry);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public void storeChat(Chat chat) {
		try {
			stm = con.createStatement();

			//Storing memebers
			//Deleting previous list of members if this chat hashcode is already present in the database to avoid adding multiple times the same member list
			String membersClean = "DELETE FROM Members WHERE codeChat = ?";
			PreparedStatement pstm = con.prepareStatement(membersClean);
			pstm.setString(1,chat.getChatHashCode());
			pstm.executeUpdate();
			//Adding list of members
			for(String id : chat.getMembers()){
				String memberAdd = "INSERT INTO Members VALUES (?, ?)";
				pstm = con.prepareStatement(memberAdd);
				pstm.setString(1,chat.getChatHashCode());
				pstm.setString(2,id);
				pstm.executeUpdate();
			}


			ArrayList<Message> messages = chat.getHistory().getMessageHistory();
			for (Message m : messages) {
				storeMessage(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Chat getChatByHashCode(String code) {
		PreparedStatement pstm;
		ArrayList<String> members = new ArrayList<String>();
		History hist = new History();
		try {
			//pstm = con.prepareStatement("select CodeChat, userid from Chats Where CodeChat=?");
			pstm = con.prepareStatement("SELECT userid FROM Members WHERE codeChat=?");
			pstm.setString(1, code);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				//members.add(rs.getString(2));
				members.add(rs.getString(1));
			}
			pstm = con.prepareStatement("SELECT CodeChat,userid , Date, Text FROM Chats WHERE CodeChat=?");
			pstm.setString(1, code);
			rs = pstm.executeQuery();
			while (rs.next()) {
				hist.insertMessage(new Message(rs.getString(2), rs.getString(1), rs.getString(4), new Date(rs.getTimestamp(3).getTime())));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Chat chat = new Chat(members);
		chat.setHistory(hist);
		return chat;
	}

	@Override
	public Iterable<Chat> readAllChats() {
		// TODO Auto-generated method stub
		ResultSet rs = this.request("SELECT DISTINCT CodeChat FROM Chats ");
		ArrayList<Chat> chats = new ArrayList<Chat>();
		try {
			while (rs.next()) {
				chats.add(getChatByHashCode(rs.getString(1)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chats;
	}

	@Override
	public void storeAll(Iterable<Chat> chats) {
		for (Chat c : chats) {
			storeChat(c);
		}
	}

	@Override
	public void storeMessage(Message message) {
		Timestamp date = new Timestamp(message.getDate().getTime());
		String chat = message.getChatHashCode();
		String userID = message.getUserID();
		String text = message.getText();
		String querry = "INSERT INTO Chats (codechat, userid, date, text) VALUES (?,?,?,?)";
		try {
			PreparedStatement pstm = con.prepareStatement(querry);
			pstm.setString(1, chat);
			pstm.setString(2, userID);
			pstm.setTimestamp(3, date);
			pstm.setString(4, text);
			pstm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
