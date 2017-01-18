package monpkg.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.Before;
import org.junit.Test;

import monpkg.entities.Activity;
import monpkg.entities.Nature;
import monpkg.services.ActivityManager;

public class TestActivityManager {

	@EJB
	ActivityManager activityManager;
	Activity activity = new Activity();

	public TestActivityManager() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
		;
		assertNotNull(activityManager);
	}

	Nature nature;

	@Before
	public void setUp() throws Exception {

	}

	// @Test
	// public void testSaveActivity() throws SQLException {
	// activity.setYear(new Date());
	// activity.setNature(Nature.EXPERIENCES_PROFESSIONNELLES);
	// activity.setTitle("Développeur application Hybride");
	// activity.setDescription("Analyse des besoins");
	// activity.setWebAddress("https://github.com/aminenasseh/Gestion-Cvs");
	// activityManager.saveActivity(activity);
	// }

	@Test
	 public void testFindOneActivity() throws SQLException {
	 Activity findActivity = activityManager.findOneActivity("Développeur application Hybride");
	 assertEquals("Développeur application Hybride", findActivity.getTitle());
	 }

	@Test
	public void testFindActivity() throws SQLException {
		assertNotNull(activityManager.findActivities());
		assertEquals(1, activityManager.findActivities().size());
	}

}
