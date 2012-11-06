package org.miernik.jfxlib.presenter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.jodah.concurrentunit.junit.ConcurrentTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jfxlib.Service;

public class BasePresenterTest extends ConcurrentTestCase {

	final static Logger logger = Logger.getLogger(BasePresenterTest.class);

	boolean result;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@BeforeClass
	public static void initJFX() throws Exception {
		new JFXPanel();
	}

	@Test
	public void testOnShow() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnShow");
				result = false;
				final Stage stage = new Stage();
				final BasePresenter<Service> p = new BasePresenter<Service>() {

					@Override
					protected void onShow() {
						result = true;
					}
				};
				p.setView(new AnchorPane());
				stage.setScene(new Scene(p.getView()));

				threadAssertFalse(stage.isShowing());
				threadAssertFalse(result);

				logger.debug("invoke show on stage object");
				stage.show();
				threadAssertTrue(stage.isShowing());
				threadAssertTrue(result);

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testOnHide() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnHide");
				result = false;
				final Stage stage = new Stage();
				final BasePresenter<Service> p = new BasePresenter<Service>() {

					@Override
					protected void onHide() {
						result = true;
					}
				};
				p.setView(new AnchorPane());
				stage.setScene(new Scene(p.getView()));

				logger.debug("invoke show on stage object");
				stage.show();
				threadAssertTrue(stage.isShowing());
				threadAssertFalse(result);

				logger.debug("invoke hide on stage object");
				stage.hide();
				threadAssertFalse(stage.isShowing());
				threadAssertTrue(result);

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testGetWindow() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testGetWindow");
				final Stage stage = new Stage();
				final BasePresenter<Service> p = new BasePresenter<Service>() {
				};
				p.setView(new AnchorPane());
				stage.setScene(new Scene(p.getView()));

				threadAssertEquals(stage, p.getWindow());

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testGetScene() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testGetScene");
				final BasePresenter<Service> p = new BasePresenter<Service>() {
				};
				p.setView(new AnchorPane());
				Scene scene = new Scene(p.getView());

				threadAssertEquals(scene, p.getScene());

				resume();
			}
		});
		threadWait(1000);
	}
	
	class TestBasePresenter extends BasePresenter<Service> {
		public int result = 0;
	}
	
	@Test
	public void testOnInitScene() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnInitScene");
				final TestBasePresenter p = new TestBasePresenter() {
					@Override
					protected void onInitScene() {
						super.onInitScene();
						result++;
					}
				};
				threadAssertEquals(0, p.result);
				p.setView(new AnchorPane());
				new Scene(p.getView());
				threadAssertEquals(1, p.result);

				resume();
			}
		});
		threadWait(1000);
	}
	
	@Test
	public void testOnInitWindow() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnInitWindow");
				final Stage stage = new Stage();
				final TestBasePresenter p = new TestBasePresenter() {
					@Override
					protected void onInitWindow() {
						super.onInitWindow();
						result++;
					}
				};
				p.setView(new AnchorPane());
				stage.setScene(new Scene(p.getView()));
				threadAssertEquals(1, p.result);

				resume();
			}
		});
		threadWait(1000);
	}
}
