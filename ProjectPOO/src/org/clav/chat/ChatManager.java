package org.clav.chat;

import org.clav.AppHandler;
import org.clav.database.ChatStorage;
import org.clav.database.EmptyChatStorage;
import org.clav.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatManager {
	private AppHandler appHandler;
	private ChatStorage storage;
	private HashMap<String, Chat> chats;

	public ChatManager() {
		this.chats = new HashMap<>();
		this.storage = new EmptyChatStorage();
		this.load();
	}

	public void setStorage(ChatStorage storage) {
		this.storage = storage;
	}

	public void load() {
		for (Chat chat : this.storage.readAllChats()) {
			this.chats.put(chat.getChatHashCode(), chat);
			this.log("Reading chat from storage");
			this.log("\n" + chat.getHistory().printHistory());
		}
	}

	public void save() {
		this.storage.storeAll(this.chats.values());
	}

	private void log(String log) {
		System.out.println("[CHAT_M]" + log);
	}


	public Chat getChat(String chatHashCode) {
		return this.chats.get(chatHashCode);
	}

	private boolean containsChat(String chatHashCode) {
		return this.chats.containsKey(chatHashCode);
	}

	public void createChat(ArrayList<User> members) {
		this.log("Creating chat");
		Chat chat = new Chat(members);
		this.chats.put(chat.getChatHashCode(), chat);
		this.log("Chat created");
	}

	public void createIfNew(ChatInit init) {
		if (!this.containsChat(init.getChatHashCode())) {
			ArrayList<User> users = new ArrayList<>();
			for (String id : init.getIdentifiers()) {
				users.add(this.getAppHandler().getUserFor(id));
			}
			this.createChat(users);
		}
	}

	private boolean haveSameMembers(Chat chat, ArrayList<String> identifiers) {
		if (chat.getMembers().size() != identifiers.size()) return false;
		for (User u : chat.getMembers()) {
			if (!identifiers.contains(u.getIdentifier())) return false;
		}
		return true;
	}

	public void leaveChat(Chat chat) {
		this.chats.remove(chat.getChatHashCode());

	}

	public void updateMissingHistory(Chat chat) {

	}

	public void sendMissingHistory(Chat chat, User user) {

	}

	public void processMessageReception(Message message) {
		if (this.containsChat(message.getChatHashCode())) {
			Chat relatedChat = this.chats.get(message.getChatHashCode());
			relatedChat.receiveMessage(message);
			this.log("Updating chat with new message");
		} else {
			this.log("Unknown chat hashcode");
		}
	}

	public void processMessageEmission(Message message) {
		if (this.containsChat(message.getChatHashCode())) {
			Chat relatedChat = this.chats.get(message.getChatHashCode());
			relatedChat.emitMessage(message);
			this.log("Updaing chat with emmetted message");
		} else {
			this.log("Unknown chat hashcode");
		}
	}

	public void setAppHandler(AppHandler appHandler) {
		this.appHandler = appHandler;
	}

	public AppHandler getAppHandler() {
		return appHandler;
	}

	public HashMap<String, Chat> getChats() {
		return chats;
	}
}
