package org.miernik.jfxlib.dialogs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import org.junit.BeforeClass;
import org.junit.Test;

public class MessageBoxTest {

	@BeforeClass
	public static void initJFX() {
		new JFXPanel();
	}

	@Test
	public void testShow() throws Throwable {
		final CountDownLatch latch = new CountDownLatch(1);
		final Throwable error = new Throwable();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				final String title = "Test";
				final String message = "Test message!";
				MessageBox mb = new MessageBox(title, message);
				mb.show();

				try {
					assertNotNull(mb.getStage());
					assertTrue(mb.getStage().isShowing());
					assertEquals(title, mb.getStage().getTitle());
					assertEquals(Modality.APPLICATION_MODAL, mb.getStage()
							.getModality());
					assertNotNull(mb.getStage().getScene());
					Parent root = mb.getStage().getScene().getRoot();
					assertNotNull(root);
					assertEquals(Label.class, root.getChildrenUnmodifiable()
							.get(0).getClass());
					Label l = (Label) root.getChildrenUnmodifiable().get(0);
					assertEquals(message, l.getText());
				} catch (AssertionError | Exception e) {
					error.initCause(e);
					latch.countDown();
				}

				Platform.exit();
				latch.countDown();
			}

		});
		assertTrue(latch.await(2, TimeUnit.SECONDS));
		if (error.getCause() != null)
			throw error.getCause();
	}
}
