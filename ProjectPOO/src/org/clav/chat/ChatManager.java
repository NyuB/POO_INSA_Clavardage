package org.clav.chat;

import org.clav.Agent;
import org.clav.database.ChatStorage;
import org.clav.database.EmptyChatStorage;
import org.clav.database.TxtChatStorage;
import org.clav.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatManager {
	private Agent relatedAgent;
	private ChatStorage storage;
	private HashMap<String,Chat> chats;

	public ChatManager() {
		this.chats = new HashMap<>();
		try {
			this.storage = new TxtChatStorage("dataproxy.txt");//TODO
			this.load();
		}
		catch (Exception e){
			e.printStackTrace();
			this.storage = new EmptyChatStorage();
		}



	}

	public void setStorage(ChatStorage storage) {
		this.storage = storage;
	}
	public void load(){
		for(Chat chat:this.storage.readAllChats()){
			this.chats.put(chat.getChatHashCode(),chat);
			this.log("Reading chat from storage");
			this.log("\n"+chat.getHistory().printHistory());
		}
	}

	public void save(){
		this.storage.storeAll(this.chats.values());
	}

	private void log(String log){
		System.out.println("[CHAT_M]"+log);
	}


	public Chat getChat(String chatHashCode){
		return this.chats.get(chatHashCode);
	}
	public boolean containsChat(String chatHashCode){
		return this.chats.containsKey(chatHashCode);
	}

	public void createChat(ArrayList<User> members){
		this.log("Creating chat");
		Chat chat = new Chat(members);
		this.chats.put(chat.getChatHashCode(),chat);
		this.log("Chat created");
	}

	public void createIfNew(ChatInit init){
		if (!this.containsChat(init.getChatHashCode())) {
			ArrayList<User> users = new ArrayList<>();
			for(String id:init.getIdentifiers()){
				users.add(this.getRelatedAgent().getUserManager().getActiveUsers().get(id));
			}
			this.createChat(users);
		}
	}
	public void createIfNew(ArrayList<User> users){
		this.createIfNew(new Chat(users).genChatInit());
	}

	private boolean haveSameMembers(Chat chat,ArrayList<String> identifiers){
		if(chat.getMembers().size()!=identifiers.size())return false;
		for(User u:chat.getMembers()){
			if(!identifiers.contains(u.getIdentifier()))return false;
		}
		return true;
	}

	public void leaveChat(Chat chat){
		this.chats.remove(chat.getChatHashCode());

	}
	public void updateMissingHistory(Chat chat){

	}

	public void sendMissingHistory(Chat chat, User user){

	}
	public void processMessageReception(Message message){
		if(this.containsChat(message.getChatHashCode())) {
			Chat relatedChat = this.chats.get(message.getChatHashCode());
			relatedChat.receiveMessage(message);
			this.log("Updating chat with new message");
		}
		else{
			this.log("Unknown chat hashcode");
		}
	}
	public void processMessageEmission(Message message){
		if(this.containsChat(message.getChatHashCode())){
			Chat relatedChat = this.chats.get(message.getChatHashCode());
			relatedChat.emitMessage(message);
			this.log("Updaing chat with emmetted message");
		}
		else{
			this.log("Unknown chat hashcode");
		}
	}

	public void setRelatedAgent(Agent relatedAgent) {
		this.relatedAgent = relatedAgent;
	}

	public Agent getRelatedAgent() {
		return relatedAgent;
	}

	public HashMap<String, Chat> getChats() {
		return chats;
	}
}
