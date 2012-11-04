package org.miernik.jfxlib.presenter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.jodah.concurrentunit.junit.ConcurrentTestCase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jfxlib.Service;

public class ModalWindowPresenterTest extends ConcurrentTestCase {

	@BeforeClass
	public static void initJFX() throws Exception {
		new JFXPanel();
	}

	@Test
	public void testCheckModality() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final Parent view = new AnchorPane();
				final ModalWindowPresenter<Service> p = new ModalWindowPresenter<Service>() {
				};
				p.setView(view);

				threadAssertNotNull(p.getStage());
				threadAssertEquals(Modality.APPLICATION_MODAL, p.getStage()
						.getModality());
				threadAssertNotNull(view.getScene());
				threadAssertEquals(view.getScene().getWindow(), p.getStage());
				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testOnceModality() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final ModalWindowPresenter<Service> p = new ModalWindowPresenter<Service>() {
				};
				p.setView(new AnchorPane());

				p.show();
				p.getStage().close();
				p.show();

				threadAssertNotNull(p.getStage());
				threadAssertEquals(Modality.APPLICATION_MODAL, p.getStage()
						.getModality());
				resume();
			}
		});
		threadWait(1000);
	}
}
