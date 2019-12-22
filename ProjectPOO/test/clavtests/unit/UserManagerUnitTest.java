package clavtests.unit;

import org.clav.AppHandler;
import org.clav.chat.ChatInit;
import org.clav.chat.Message;
import org.clav.user.PseudoRejection;
import org.clav.user.User;
import org.clav.user.UserManager;
import org.clav.utils.constants.DelayConstants;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserManagerUnitTest {
	private static AppHandler emptyAppHandler = new AppHandler() {
		@Override
		public User getMainUser() {
			//TODO
			return null;
		}

		@Override
		public void sendMessage(Message message) {
			//TODO

		}

		@Override
		public void initiateChat(ArrayList<User> distantMembers) {
			//TODO

		}

		@Override
		public void processMessage(Message message) {
			//TODO

		}

		@Override
		public void processChatInitiation(ChatInit init) {
			//TODO

		}

		@Override
		public void processNewUser(User user) {
			//TODO

		}

		@Override
		public void processUserInaction(String id) {
			//TODO

		}

		@Override
		public void processPseudoRejection(PseudoRejection rejection) {
			//TODO

		}

		@Override
		public boolean processMainUserPseudoChange(String newPseudo) {
			//TODO
			return false;
		}

		@Override
		public boolean isActiveID(String identifier) {
			//TODO
			return false;
		}

		@Override
		public Iterable<String> getActivesID() {
			//TODO
			return null;
		}

		@Override
		public User getUserFor(String identifier) {
			//TODO
			return null;
		}
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
		User mainUser = new User("Main","Main");
		User otherUser = new User("Other","Other");
		UserManager userManager = new UserManager(mainUser);
		userManager.setAppHandler(emptyAppHandler);
		userManager.processActive(otherUser);
		Assert.assertEquals("User manager should contains two users",2,userManager.getActiveUsers().size());
		Assert.assertTrue(userManager.isActiveUser(otherUser.getIdentifier()));
		Assert.assertTrue(userManager.isActiveUser(mainUser.getIdentifier()));
		Assert.assertTrue(userManager.getPseudoSet().contains(mainUser.getPseudo()));
		Assert.assertTrue(userManager.getPseudoSet().contains(otherUser.getPseudo()));
		Thread.sleep(DelayConstants.INACTIVE_DELAY_SEC*2000);
		Assert.assertEquals(1,userManager.getActiveUsers().size());
		Assert.assertTrue(userManager.isActiveUser(mainUser.getIdentifier()));
		Assert.assertFalse(userManager.isActiveUser(otherUser.getIdentifier()));
	}

	@Test
	public void pseudoConflictTest() throws InterruptedException {
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
}
