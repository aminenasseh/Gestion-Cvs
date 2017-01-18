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
import monpkg.services.ProfilManager;

public class TestProfilManager {
	
	@EJB
	ProfilManager profilManager;
	Activity activity = new Activity();
	
	public TestProfilManager() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);;
		assertNotNull(profilManager);
	}
	
	Nature nature;
	
	@Before
	public void setUp() throws Exception {

		assertNotNull(profilManager);
		activity.setYear(new Date());
		activity.setNature(nature);
		activity.setTitle("Stage de fin d etudes");
		activity.setDescription("Application JEE");
		profilManager.saveActivity(activity);

	}

	@Test
	public void testFindOneActivity() throws SQLException {
		Activity findActivity = profilManager.findOneActivity(activity);
		assertEquals("Stage", findActivity.getNature());
	}
	
	@Test
	public void testFindActivity() throws SQLException {
		assertNotNull(profilManager.findActivities());
		assertEquals(1, profilManager.findActivities().size());
	}

}
