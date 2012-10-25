/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.presenter;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.miernik.jfxlib.Service;

/**
 * 
 * @author Miernik
 */
public abstract class WindowPresenter<T extends Service> extends BasePresenter<T> {

	private Stage stage;

	public WindowPresenter() {
		this.stage = new Stage();
	}

	public Stage getStage() {
		if (stage.getScene() == null) {
			if (getView() == null) {
				throw new IllegalStateException(
						"Cannot initiate Scene object when Root object of View is null");
			}
			stage.setScene(new Scene(this.getView()));
		}
		return stage;
	}
}
