/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.presenter;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.miernik.jfxlib.Service;

/**
 *
 * @author Miernik
 */
public abstract class AbstractMainPresenter<T extends Service> extends AbstractPresenter<T> {
    
    public void setMainView(Stage stage) {
        stage.setScene(new Scene(this.getView()));
        stage.setOnShowing(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent arg0) {
                show();
            }
        });
    }
}
