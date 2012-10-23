/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.stage.Stage;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jfxlib.event.SimpleActionEvent;
import org.miernik.jfxlib.presenter.BaseMainPresenter;

/**
 * 
 * @author Miernik
 */
public class MVPApplicationTest {

	public MVPApplicationTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	class TestApp extends MVPApplication<Service> {
		public boolean flag = false;

		@Override
		public Service getService() {
			return new Service() {
			};
		}

		@Override
		public BaseMainPresenter<?> getMainPresenter() {
			return null;
		}

		@Override
		public void start(Stage arg0) throws Exception {
		}

		public void actionTest123() {
			flag = true;
		}

	}

	@Test
	public void testSimpleAction() {
		final TestApp app = new TestApp();
		final String actionName = "Test123";
		final SimpleActionEvent sae = new SimpleActionEvent(actionName);

		app.getEventBus().fireEvent(sae);
		assertTrue(app.flag);
	}

	@Test
	public void testLoadWithResource() throws Exception {
		final TestApp app = new TestApp();
		final String viewName = "TestView";
		Locale.setDefault(Locale.ROOT);

		URL path = app.getClass().getResource("/views/"+viewName +".fxml");
		assertNotNull(path);
		File file = new File(path.getFile());
		assertTrue(file.exists());
		
		ResourceBundle resource = ResourceBundle.getBundle("views." + viewName);
		assertNotNull(resource);

		TestPresenter result = app.load(TestPresenter.class, viewName, true);
		assertNotNull(result);
		assertNotNull(result.getResource());
	}
	
	@Test
	public void testLoad() throws Exception {
		final TestApp app = new TestApp();
		final String viewName = "TestView";

		URL path = app.getClass().getResource("/views/"+viewName +".fxml");
		assertNotNull(path);
		File file = new File(path.getFile());
		assertTrue(file.exists());
		
		TestPresenter result = app.load(TestPresenter.class, viewName);
		assertNotNull(result);
		assertEquals(TestPresenter.class, result.getClass());
		assertNotNull(result.getView());
		assertTrue(result.getView() instanceof Parent);
		assertNotNull(result.testLabel);
		assertEquals("initiated", result.testLabel.getText());
	}
	
}
