/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxexamples.presenter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.miernik.jfxexamples.ExampleService;
import org.miernik.jfxlib.dialogs.MessageBox;
import org.miernik.jfxlib.presenter.MainWindowPresenter;

/**
 *
 * @author Miernik
 */
public class MainPresenter extends MainWindowPresenter<ExampleService> {

    public MainPresenter(Stage s) {
		super(s);
	}

	@FXML
    private MenuItem menuClose;
    @FXML
    private MenuItem menuAbout;
    @FXML
    private MenuItem menuMessage;
    @FXML
    private MenuItem menuNew;
    @FXML
    private AnchorPane mainContent;
    @FXML private MenuItem sampleWindow;

    
	@Override
	public void onInit() {
		getStage().setTitle(getResource().getString("windowTitle"));
    	sampleWindow.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				fireAction("OpenSampleWindow");
			}
		});
        menuClose.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                getStage().close();
            }
        });
        menuAbout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                fireAction("About");
            }
        });
        menuMessage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MessageBox msg = new MessageBox("MessageBox", "Hello world!");
				msg.show();
			}
		});
        menuNew.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				fireAction("NewInfo");
			}
		});
	}    
    
}
