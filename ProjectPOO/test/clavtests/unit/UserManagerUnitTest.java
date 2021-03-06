package clavtests.unit;

import org.clav.AppHandler;
import org.clav.chat.ChatInit;
import org.clav.chat.ChatUnknown;
import org.clav.chat.Message;
import org.clav.user.PseudoRejection;
import org.clav.user.User;
import org.clav.user.UserManager;
import org.clav.utils.constants.DelayConstants;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UserManagerUnitTest {
	private static AppHandler emptyAppHandler = new AppHandler() {
		@Override
		public void processImage(BufferedImage image) {
			//TODO

		}

		@Override
		public void sendImage(String code,BufferedImage image) {
			//TODO

		}

		@Override
		public User getMainUser() {
			return null;
		}

		@Override
		public void sendMessage(Message message) {


		}

		@Override
		public void storeChats() {
			//TODO

		}

		@Override
		public void initiateChat(ArrayList<String> distantMembers) {


		}

		@Override
		public void processMessage(Message message) {


		}

		@Override
		public void processChatInitiation(ChatInit init) {


		}

		@Override
		public void processChatUnknownRequest(ChatUnknown chatUnknown) {


		}

		@Override
		public void processChatClosedByUser(String code) {


		}

		@Override
		public void processNewUser(User user) {

		}

		@Override
		public boolean checkRejection(User user) {
			//TODO
			return false;
		}

		@Override
		public void applyConfig() {
			//TODO

		}

		@Override
		public void processUserInaction(String id) {


		}

		@Override
		public void processPseudoRejection(PseudoRejection rejection) {


		}

		@Override
		public boolean processMainUserPseudoChange(String newPseudo) {
			return false;
		}

		@Override
		public boolean isActiveID(String identifier) {
			return false;
		}

		@Override
		public Iterable<String> getActivesID() {
			return null;
		}

		@Override
		public User getUserFor(String identifier) {
			return null;
		}

		@Override
		public void storeChat(String code) {}
	};
	@Test
	public void constructorTest(){
		User mainUser = new User("Main","Main");
		UserManager userManager = new UserManager(mainUser);
		Assert.assertEquals("UserManager should contain one user after construction",1,userManager.getActiveUsers().size());
		Assert.assertEquals(mainUser,userManager.getMainUser());
		Assert.assertTrue(userManager.getActiveUsers().containsKey(mainUser.getIdentifier()));
	}

	@Test
	public void processOneUserActivityTest(){
		User mainUser = new User("Main","Main");
		User otherUser = new User("Other","Other");
		UserManager userManager = new UserManager(mainUser);
		userManager.processActive(otherUser);
		Assert.assertEquals("User manager should contains two users",2,userManager.getActiveUsers().size());
		Assert.assertTrue(userManager.isActiveUser(otherUser.getIdentifier()));
		Assert.assertTrue(userManager.isActiveUser(mainUser.getIdentifier()));
		Assert.assertTrue(userManager.getPseudoSet().contains(mainUser.getPseudo()));
		Assert.assertTrue(userManager.getPseudoSet().contains(otherUser.getPseudo()));
	}

	@Test
	public void userInactivityTest() throws InterruptedException{
		int initialThreadCount = Thread.activeCount();
		User mainUser = new User("Main","Main");
		User otherUser = new User("Other","Other");
		UserManager userManager = new UserManager(mainUser);
		Assert.assertEquals("User manager should have created a thread to track user activity",initialThreadCount+1,Thread.activeCount());
		userManager.setAppHandler(emptyAppHandler);

		userManager.processActive(otherUser);
		Assert.assertEquals("User manager should contains two users",2,userManager.getActiveUsers().size());
		Assert.assertTrue(userManager.isActiveUser(otherUser.getIdentifier()));
		Assert.assertTrue(userManager.isActiveUser(mainUser.getIdentifier()));

		Assert.assertTrue(userManager.getPseudoSet().contains(mainUser.getPseudo()));
		Assert.assertTrue(userManager.getPseudoSet().contains(otherUser.getPseudo()));
		Thread.sleep(DelayConstants.INACTIVE_DELAY_SEC*1500);
		Assert.assertEquals(1,userManager.getActiveUsers().size());
		Assert.assertTrue(userManager.isActiveUser(mainUser.getIdentifier()));
		Assert.assertFalse(userManager.isActiveUser(otherUser.getIdentifier()));
	}

	@Test
	public void userActivityTimersTest() throws InterruptedException {
		int initialThreadCount = Thread.activeCount();
		User mainUser = new User("Main","Main");
		ArrayList<User> userList = new ArrayList<>();
		int nbUsers=  4;
		for(int i = 0;i<nbUsers;i++){
			String pseudo = "Proxy_"+i;
			userList.add(new User(pseudo,pseudo));
		}
		UserManager userManager = new UserManager(mainUser);
		Assert.assertEquals("User manager should have created a thread to track user activity",initialThreadCount+1,Thread.activeCount());
		userManager.setAppHandler(emptyAppHandler);

		for(User u : userList){
			userManager.processActive(u);
		}
		Assert.assertEquals("User manager should contains "+nbUsers+1+" users",nbUsers+1,userManager.getActiveUsers().size());

		Thread.sleep(DelayConstants.INACTIVE_DELAY_SEC*1500);
		Assert.assertEquals(1,userManager.getActiveUsers().size());
		Assert.assertTrue(userManager.isActiveUser(mainUser.getIdentifier()));

	}

	@Test
	public void pseudoConflictTestFirstCreatedFirstAdded() throws InterruptedException {
		User mainUser = new User("Main","Main");
		User otherUser = new User("Other","Conflict");
		Thread.sleep(1000);
		User lastUser = new User("Last","Conflict");
		UserManager userManager = new UserManager(mainUser);
		userManager.setAppHandler(emptyAppHandler);
		userManager.processActive(otherUser);
		userManager.processActive(lastUser);
		Assert.assertTrue(userManager.isActiveUser(otherUser.getIdentifier()));
		Assert.assertFalse(userManager.isActiveUser(lastUser.getIdentifier()));

	}

	@Test
	public void pseudoConflictTestFirstCreatedSecondAdded() throws InterruptedException {
		User mainUser = new User("Main","Main");
		User otherUser = new User("Other","Conflict");
		Thread.sleep(1000);
		User lastUser = new User("Last","Conflict");
		UserManager userManager = new UserManager(mainUser);
		userManager.setAppHandler(emptyAppHandler);
		userManager.processActive(lastUser);
		userManager.processActive(otherUser);
		Assert.assertTrue(userManager.isActiveUser(otherUser.getIdentifier()));
		Assert.assertFalse(userManager.isActiveUser(lastUser.getIdentifier()));
	}
}
