package org.clav.database;

import org.clav.chat.Chat;
import org.clav.chat.History;
import org.clav.chat.Message;
import org.clav.utils.HashUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Write and reads chats in a txt file following a custom beacon format
 * Unable to read or store a specific chat, has to read and rewrite everything
 */
public class TxtChatStorage implements ChatStorage {
	private String filePath;

	public TxtChatStorage(String filePath) {
		this.filePath = filePath;

	}

	@Override
	public void storeAll(Iterable<Chat> chats) {
		//TODO
		StringBuilder sb = new StringBuilder();
		for (Chat chat : chats) {
			sb.append("<CHAT> ");

			for (int i = 0; i < chat.getMembers().size(); i++) {
				sb.append(chat.getMembers().get(i) + ((i==chat.getMembers().size()-1)?"\n":" "));
			}

			for (int m = 0; m < chat.getHistory().getMessageHistory().size(); m++) {
				Message message = chat.getHistory().getMessageHistory().get(m);
				sb.append("<MSG> ");
				sb.append(message.getDate().getYear()+" ");
				sb.append((message.getDate().getMonth()+1)+" ");
				sb.append(message.getDate().getDay()+" ");
				sb.append(message.getDate().getHours()+" ");
				sb.append(message.getDate().getMinutes()+" ");
				sb.append(message.getUserID()+"\n");
				sb.append(message.getText()+"\n");
			}
			sb.append("<CHATEND>\n");
		}
		try {
			FileWriter writer = new FileWriter(filePath);
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
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
			ArrayList<String> users = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				String[] cmd = line.split("[\\s]+");
				switch (cmd[0]) {
					case "<MSG>":
						int year = Integer.valueOf(cmd[1]);
						int month = Integer.valueOf(cmd[2]);
						int day = Integer.valueOf(cmd[3]);
						int hour = Integer.valueOf(cmd[4]);
						int min = Integer.valueOf(cmd[5]);
						Date date = new Date(year, month-1, day, hour, min);
						history.insertMessage(new Message(cmd[6], chatHashCode, reader.readLine(), date));
						break;
					case "<CHAT>":
						for (int i = 1;i<cmd.length;i++){
							users.add(cmd[i]);
						}
						chatHashCode = HashUtils.hashStringList(users);
						break;
					case "<CHATEND>":
						Chat newChat = new Chat(users);
						newChat.setHistory(history);
						chats.add(newChat);
						users.clear();
						history = new History();
						chatHashCode = null;
						break;

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Reading " + chats.size() + " chats from txt");
		return chats;
	}

	@Override
	public void storeMessage(Message message) {
		//TODO

	}
}
