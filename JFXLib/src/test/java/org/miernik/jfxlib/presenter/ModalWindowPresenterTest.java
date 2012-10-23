package org.miernik.jfxlib.presenter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
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
				final ModalWindowPresenter<Service> p = new ModalWindowPresenter<Service>() {

					@Override
					public void initialize() {
						// TODO Auto-generated method stub
						
					}
				};
				p.setView(new AnchorPane());

				threadAssertNotNull(p.getStage());
				threadAssertEquals(Modality.APPLICATION_MODAL, p.getStage().getModality());
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

					@Override
					public void initialize() {
						// TODO Auto-generated method stub
						
					}
				};
				p.setView(new AnchorPane());
				
				p.show();
				p.getStage().close();
				p.show();

				threadAssertNotNull(p.getStage());
				threadAssertEquals(Modality.APPLICATION_MODAL, p.getStage().getModality());
				resume();
			}
		});
		threadWait(1000);
	}
}
