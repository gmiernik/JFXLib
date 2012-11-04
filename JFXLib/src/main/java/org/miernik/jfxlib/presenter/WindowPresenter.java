/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.presenter;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.miernik.jfxlib.Service;

/**
 * 
 * @author Miernik
 */
public abstract class WindowPresenter<T extends Service> extends
		BasePresenter<T> {

	private Stage stage;

	public WindowPresenter() {
		this(new Stage());
	}
	
	public WindowPresenter(Stage stage) {
		this.stage = stage;
		setWindow(this.stage);
	}

	public void show() {
		stage.show();
	}

	public void hide() {
		stage.hide();
	}

	protected Stage getStage() {
		return stage;
	}
	
	@Override
	public void setView(Parent view) {
		getStage().setScene(new Scene(view));
		super.setView(view);
	}
}
