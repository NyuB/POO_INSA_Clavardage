package org.clav;

import org.clav.chat.ChatInit;
import org.clav.chat.ChatUnknown;
import org.clav.chat.Message;
import org.clav.user.PseudoRejection;
import org.clav.user.User;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Provides methods to handle the global chat application behavior
 * Coordinates the different managers
 */
public interface AppHandler {

	User getMainUser();

	/**
	 * Handle emission of a message by the main user
	 * @param message emitted message
	 */
	void sendMessage(Message message);

	/**
	 * Handle a chat initiation request from the main user
	 * @param distantMembers The list of distant members (not including the main user) of this chat
	 */
	void initiateChat(ArrayList<String> distantMembers);

	/**
	 * Handle the reception of a message
	 * @param message Received message
	 */
	void processMessage(Message message);

	void processImage(BufferedImage image);

	void sendImage(String code,BufferedImage image);

	/**
	 * Handle a chat request from a distant agent
	 * @param init Chat initiation request from a distant user
	 */
	void processChatInitiation(ChatInit init);

	/**
	 * Handle a notification from an user receiving a message for an unknown chat
	 * @param chatUnknown information about concerned userand chat
	 */
	void processChatUnknownRequest(ChatUnknown chatUnknown);

	void processChatClosedByUser(String code);

	/**
	 * Handle the activity detection of a distant user
	 * @param user Signaled user
	 */
	void processNewUser(User user);

	/**
	 * Handle the inactivity dtection of a distant user
	 * @param id Id of a recently detected inactive user
	 */
	void processUserInaction(String id);


	boolean checkRejection(User user);

	/**
	 * Handle the rejection of the pseudo chosen by the main user
	 * @param rejection rejected pseudo and its date of selection by the conflicting user
	 */
	void processPseudoRejection(PseudoRejection rejection);

	boolean processMainUserPseudoChange(String newPseudo);

	/**
	 * @param identifier User unique id
	 * @return true if a User with id identifier is currently active
	 */
	boolean isActiveID(String identifier);

	/**
	 * @return an Iterable containing the unique ids of all active users
	 */
	Iterable<String> getActivesID();

	/**
	 * @param identifier user unique id
	 * @return the User associated to the id
	 */
	User getUserFor(String identifier);

	void storeChat(String code);

	/**
	 * WARNING : Some parameters may only apply on restart, effects of this method depends on implementation
	 */
	void applyConfig();

	void storeChats();
}
