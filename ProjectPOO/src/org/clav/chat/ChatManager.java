package org.clav.chat;
import org.clav.Agent;
import org.clav.user.User;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatManager {
	private Agent relatedAgent;
	private HashMap<String,Chat> chats;

	public ChatManager() {
		this.chats = new HashMap<>();
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

	private void createChat(ArrayList<User> members){
		this.log("Creating chat");
		Chat chat = new Chat(members,this.getRelatedAgent());
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
			this.log("\n" + relatedChat.getHistory().toString());
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
}
