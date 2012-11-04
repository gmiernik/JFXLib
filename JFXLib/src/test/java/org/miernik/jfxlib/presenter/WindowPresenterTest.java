package org.miernik.jfxlib.presenter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.jodah.concurrentunit.junit.ConcurrentTestCase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jfxlib.Service;

public class WindowPresenterTest extends ConcurrentTestCase {

	@BeforeClass
	public static void initJFX() throws Exception {
		new JFXPanel();
	}
	
	@Test
	public void testConstructor() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final Stage stage = new Stage();
				final WindowPresenter<Service> p = new WindowPresenter<Service>(stage) {

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
				final WindowPresenter<Service> p = new WindowPresenter<Service>() {

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
				final WindowPresenter<Service> p = new WindowPresenter<Service>() {

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
				final WindowPresenter<Service> p = new WindowPresenter<Service>() {

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
	
}
