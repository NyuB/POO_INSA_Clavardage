package org.clav.database;

import org.clav.chat.Chat;
import org.clav.chat.History;
import org.clav.chat.Message;
import org.clav.user.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class TxtChatStorage implements ChatStorage {
	private String filePath;
	public TxtChatStorage(String filePath) {
		this.filePath = filePath;

	}

	@Override
	public void storeAll(Iterable<Chat> chats) {
		//TODO
		StringBuilder sb = new StringBuilder();
		for(Chat chat : chats){
			sb.append("<CHAT> ");

			for(int i =0;i< chat.getMembers().size();i++){
				sb.append(chat.getMembers().get(i).getIdentifier()+" ");
			}

			for(int m = 0;m<chat.getHistory().getMessageHistory().size();m++){
				sb.append(chat.getHistory().getMessageHistory().get(m).getText());//TODO
			}
		}


	}

	@Override
	public void storeChat(Chat chat) {
		//TODO
	}

	@Override
	public Chat getChatByHashCode(String code) {
		//TODO
		return null;
	}

	@Override
	public Iterable<Chat> readAllChats() {
		ArrayList<Chat> chats = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			History history = new History();
			String chatHashCode = null;
			ArrayList<User> users = new ArrayList<>();
			String line;
			while ((line = reader.readLine())!=null){
				String[] cmd = line.split("[\\s]+");
				switch (cmd[0]){
					case "<MSG>":
						int year = Integer.valueOf(cmd[1]);
						int month = Integer.valueOf(cmd[2]);
						int day = Integer.valueOf(cmd[3]);
						int hour = Integer.valueOf(cmd[4]);
						int min = Integer.valueOf(cmd[5]);
						Date date = new Date(year,month,day,hour,min);
						history.insertMessage(new Message(cmd[6],chatHashCode,cmd[7],date));
						break;
					case "<CHAT>":
						int nbUsers = Integer.valueOf(cmd[1]);
						users.clear();
						int i;
						for(i = 2;i<2+nbUsers;i++){
							users.add(new User(cmd[i],cmd[i]));//TODO
						}
						StringBuilder sb = new StringBuilder();
						for(i = nbUsers+2;i<cmd.length;i++){
							sb.append(cmd[i]);
						}
						chatHashCode = sb.toString();
						break;
					case "<CHATEND>":
						Chat newChat = new Chat(users);
						newChat.setHistory(history);
						chats.add(newChat);
						break;

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Reading "+chats.size()+" chats from txt");
		return chats;
	}

	@Override
	public void storeMessage(Message message) {
		//TODO

	}
}
