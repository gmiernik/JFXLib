package org.miernik.jfxlib.presenter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.jodah.concurrentunit.junit.ConcurrentTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jfxlib.Service;

public class BasePresenterTest extends ConcurrentTestCase {

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
	public void testShow() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
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

				p.show();
				threadAssertTrue(stage.isShowing());
				threadAssertTrue(result);

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testHide() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
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

				stage.show();
				threadAssertTrue(stage.isShowing());
				threadAssertFalse(result);

				p.hide();
				threadAssertFalse(stage.isShowing());
				threadAssertTrue(result);

				resume();
			}
		});
		threadWait(1000);
	}
	
	@Test
	public void testGetStage() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final Stage stage = new Stage();
				final BasePresenter<Service> p = new BasePresenter<Service>() {

					@Override
					public void initialize() {
						// TODO Auto-generated method stub

					}
				};
				p.setView(new AnchorPane());
				stage.setScene(new Scene(p.getView()));

				threadAssertEquals(stage, p.getStage());

				resume();
			}
		});
		threadWait(1000);
	}
}
