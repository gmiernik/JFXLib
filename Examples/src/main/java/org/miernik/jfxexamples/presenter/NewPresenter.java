package org.miernik.jfxexamples.presenter;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import org.miernik.jfxexamples.ExampleService;
import org.miernik.jfxlib.presenter.DialogPresenter;

public class NewPresenter extends DialogPresenter<ExampleService> implements Initializable {

	@FXML private Button buttonOk;
	
	public NewPresenter() {
		super("New info", true);		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		buttonOk.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				getStage().close();
			}
		});
	}

}
