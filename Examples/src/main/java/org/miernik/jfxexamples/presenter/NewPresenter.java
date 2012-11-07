package org.miernik.jfxexamples.presenter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.miernik.jfxexamples.ExampleService;
import org.miernik.jfxlib.presenter.ModalWindowPresenter;

public class NewPresenter extends ModalWindowPresenter<ExampleService> {

	@FXML private Button buttonOk;
	
	@Override
	public void onInit() {
		buttonOk.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				hide();
			}
		});
	}

}
