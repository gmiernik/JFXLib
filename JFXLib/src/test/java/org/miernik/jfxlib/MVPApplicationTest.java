/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jodah.concurrentunit.junit.ConcurrentTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jfxlib.event.SimpleActionEvent;
import org.miernik.jfxlib.presenter.MainWindowPresenter;

/**
 * 
 * @author Miernik
 */
public class MVPApplicationTest extends ConcurrentTestCase {

	public MVPApplicationTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		new JFXPanel();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	class MainWindow extends MainWindowPresenter<Service> {

		public MainWindow(Stage s) {
			super(s);
		}
	}

	class TestApp extends MVPApplication<Service> {
		public boolean flag = false;
		public MainWindowPresenter<Service> mainPresenter;

		@Override
		public Service getService() {
			return new Service() {
			};
		}

		public void actionTest123() {
			flag = true;
		}

		@Override
		public MainWindowPresenter<?> initMainPresenter(Stage s) {
			MainWindowPresenter<Service> p = new MainWindow(s);
			mainPresenter = initPresenter(p, "MainView");
			return mainPresenter;
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

		URL path = app.getClass().getResource("/views/" + viewName + ".fxml");
		assertNotNull(path);
		File file = new File(path.getFile());
		assertTrue(file.exists());

		ResourceBundle resource = ResourceBundle.getBundle("views." + viewName);
		assertNotNull(resource);

		TestPresenter result = app.loadPresenter(TestPresenter.class, viewName,
				true);
		assertNotNull(result);
		assertNotNull(result.getResource());
	}

	@Test
	public void testLoad() throws Throwable {
		final TestApp app = new TestApp();
		final String viewName = "TestView";

		URL path = app.getClass().getResource("/views/" + viewName + ".fxml");
		
		assertNotNull(path);
		File file = new File(path.getFile());
		assertTrue(file.exists());

		Platform.runLater(new Runnable() {
			public void run() {
				Stage stage = new Stage();
				TestPresenter result = app.loadPresenter(TestPresenter.class, viewName);
				threadAssertNotNull(result);
				threadAssertEquals(TestPresenter.class, result.getClass());
				threadAssertNotNull(result.getView());

				stage.setScene(new Scene(result.getView()));
				threadAssertTrue(result.getView() instanceof Parent);
				
				assertNotNull(result.testLabel);
				assertEquals("initiated", result.testLabel.getText());

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testInitPresenter() throws Exception {
		final TestApp app = new TestApp();
		final String viewName = "TestView";
		final TestPresenter presenter = new TestPresenter();

		URL path = app.getClass().getResource("/views/" + viewName + ".fxml");
		assertNotNull(path);
		File file = new File(path.getFile());
		assertTrue(file.exists());

		assertNull(presenter.getView());
		app.initPresenter(presenter, viewName);
		assertTrue(presenter.getView() instanceof Parent);
		assertNotNull(presenter.getEventBus());
		assertNotNull(presenter.getService());
	}

	@Test
	public void testStart() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final TestApp app = new TestApp();
				final Stage stage = new Stage();

				app.start(stage);
				MainWindowPresenter<Service> p = app.mainPresenter;
				threadAssertNotNull(p);
				threadAssertTrue(stage.isShowing());
				threadAssertEquals(stage, p.getView().getScene().getWindow());

				resume();
			}
		});
		threadWait(1000);
	}

}
