/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.presenter;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.miernik.jfxlib.Service;

/**
 * 
 * @author Miernik
 */
public class DialogPresenter<T extends Service> extends AbstractPresenter<T> {

	private Stage stage;

	public DialogPresenter(String name, boolean modal) {
		this.stage = new Stage();
		this.stage.setTitle(name);
		if (modal) {
			this.stage.initModality(Modality.APPLICATION_MODAL);
		} else {
			this.stage.initModality(Modality.NONE);
		}
	}

	public Stage getStage() {
		if (stage.getScene() == null) {
			if (getView() == null) {
				throw new IllegalStateException(
						"Cannot initiate Scene object when Root object of View is null");
			}
			stage.setScene(new Scene(this.getView()));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent t) {
					stage.close();
				}
			});
		}
		return stage;
	}

	/**
	 * Create separate window for showing new dialog (Stage and Scene objects)
	 */
	public void showDialog() {
		getStage().show();
		this.show();
	}
}
