package clavtests.unit;

import org.clav.config.Installer;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class InstallerUnitTest {
	@Test
	public void constructorTest() {
		Installer installer = new Installer();
	}

	@Test
	public void initTest() {
		Installer installer = new Installer();
		installer.install();
		File config = new File("./Objconfig.ser");
		File db = new File("./db/mydb.script");
		Assert.assertTrue(config.exists());
		Assert.assertTrue(db.exists());
	}
}
