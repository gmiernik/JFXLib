package org.miernik.jfxlib.presenter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.jodah.concurrentunit.junit.ConcurrentTestCase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jfxlib.Service;

public class MainWindowPresenterTest extends ConcurrentTestCase {

	final static Logger logger = Logger.getLogger(MainWindowPresenterTest.class);
	
	@BeforeClass
	public static void initJFX() throws Exception {
		new JFXPanel();
	}
	
	@Test
	public void testConstructor() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final Stage stage = new Stage();
				final MainWindowPresenter<Service> p = new MainWindowPresenter<Service>(stage) {

				};
				p.setView(new AnchorPane());

				threadAssertNotNull(p.getStage());
				threadAssertEquals(stage, p.getStage());
				threadAssertFalse(p.getStage().isShowing());
				threadAssertEquals(Modality.NONE, p.getStage().getModality());
				
				resume();
			}
		});
		threadWait(1000);		
	}

	@Test
	public void testSetView() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final Parent view = new AnchorPane();
				final Stage stage = new Stage();				
				final MainWindowPresenter<Service> p = new MainWindowPresenter<Service>(stage) {

				};
				p.setView(view);

				threadAssertNotNull(p.getView());
				threadAssertEquals(view, p.getView());
				threadAssertNotNull(p.getView().getScene());
				threadAssertEquals(view.getScene(), p.getStage().getScene());
				
				resume();
			}
		});
		threadWait(1000);		
	}

	@Test
	public void testShow() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final Stage stage = new Stage();
				final MainWindowPresenter<Service> p = new MainWindowPresenter<Service>(stage) {
				};
				p.setView(new AnchorPane());

				p.show();

				threadAssertNotNull(p.getStage());
				threadAssertTrue(p.getStage().isShowing());
				resume();
			}
		});
		threadWait(1000);
	}
	
	@Test
	public void testHide() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final Stage stage = new Stage();
				final MainWindowPresenter<Service> p = new MainWindowPresenter<Service>(stage) {

				};
				p.setView(new AnchorPane());

				p.show();
				p.hide();

				threadAssertNotNull(p.getStage());
				threadAssertFalse(p.getStage().isShowing());
				
				resume();
			}
		});
		threadWait(1000);
	}

	class TestMainWindowPresenter extends MainWindowPresenter<Service> {
		public TestMainWindowPresenter(Stage s) {
			super(s);
		}

		public int result = 0;
	}
	
	@Test
	public void testOnInit() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnInit");
				final Stage stage = new Stage();
				final TestMainWindowPresenter p = new TestMainWindowPresenter(stage) {
					@Override
					protected void onInit() {
						result++;
					}
				};
				threadAssertEquals(0, p.result);
				p.setView(new AnchorPane());
				threadAssertEquals(1, p.result);

				resume();
			}
		});
		threadWait(1000);
	}
	
	@Test
	public void testOnShow() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnShow");
				final Stage stage = new Stage();
				final TestMainWindowPresenter p = new TestMainWindowPresenter(stage) {

					@Override
					protected void onShow() {
						result++;
					}
				};
				p.setView(new AnchorPane());

				threadAssertFalse(stage.isShowing());
				threadAssertEquals(0, p.result);

				p.show();
				threadAssertTrue(stage.isShowing());
				threadAssertEquals(1, p.result);
				
				p.hide();
				p.show();
				threadAssertEquals(2, p.result);

				resume();
			}
		});
		threadWait(1000);
	}
}
