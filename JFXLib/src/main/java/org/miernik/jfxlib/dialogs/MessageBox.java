package org.miernik.jfxlib.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MessageBox {
	
	private Stage stage;
	
	public MessageBox(String title, String message) {
		stage = new Stage();
		stage.setTitle(title);
		stage.setScene(new Scene(buildLayout(message)));
		//TODO: add modal dialog to the class
	}
	
	private Parent buildLayout(String message) {
		VBox mainBox = new VBox(8);
		mainBox.setPadding(new Insets(8));
		mainBox.setMinWidth(200);
		Label messageLabel = new Label(message);
		HBox buttonBox = new HBox(8);
		buttonBox.setAlignment(Pos.CENTER_RIGHT);
		mainBox.getChildren().addAll(messageLabel, buttonBox);
		Button btnOK = new Button("OK");
		btnOK.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});
		buttonBox.getChildren().add(btnOK);
		return mainBox;
	}
	
	public void show() {
		stage.show();
	}

}
