package org.miernik.jfxlib.presenter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
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

					@Override
					public void initialize() {
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
					
					@Override
					public void initialize() {
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

					@Override
					public void initialize() {
						// TODO Auto-generated method stub

					}
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
	public void testSetParent() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testSetParent");
				final Stage stage = new Stage();
				final BasePresenter<Service> p = new BasePresenter<Service>() {

					@Override
					public void initialize() {
						// TODO Auto-generated method stub

					}
				};
				p.setView(new AnchorPane());
				Parent parent = p.getView();
				parent.parentProperty().addListener(new ChangeListener<Parent>() {

					@Override
					public void changed(ObservableValue<? extends Parent> arg0,
							Parent arg1, Parent arg2) {
						logger.debug("parent was changed");
					}
				});
				parent.sceneProperty().addListener(new ChangeListener<Scene>() {

					@Override
					public void changed(ObservableValue<? extends Scene> arg0,
							Scene arg1, Scene arg2) {
						logger.debug("scene was changed");
					}
				});
				stage.setScene(new Scene(p.getView()));
				//TODO: prepare universal method for show/hide window with listener invoking onShow/onHide methods
				
				
				threadAssertEquals(stage, p.getWindow());

				resume();
			}
		});
		threadWait(1000);
	}
}
