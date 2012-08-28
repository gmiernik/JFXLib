package org.miernik.jfxlib.presenter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
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
	public void testShow() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final WindowPresenter<Service> p = new WindowPresenter<Service>() {
				};
				p.setView(new AnchorPane());

				p.show();

				threadAssertNotNull(p.getStage());
				threadAssertTrue(p.getStage().isShowing());
				threadAssertEquals(Modality.NONE, p.getStage().getModality());
				resume();
			}
		});
		threadWait(1000);
	}
}
