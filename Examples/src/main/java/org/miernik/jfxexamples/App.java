/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxexamples;

import javafx.stage.Stage;

import org.miernik.jfxexamples.presenter.MainPresenter;
import org.miernik.jfxexamples.presenter.NewPresenter;
import org.miernik.jfxlib.MVPApplication;
import org.miernik.jfxlib.presenter.AbstractMainPresenter;

/**
 *
 * @author Miernik
 */
public class App extends MVPApplication<ExampleService> {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private MainPresenter mainPresenter;
    private ExampleService service = new ExampleService();
        
    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("JFX Examples");
        getMainPresenter().setMainView(primaryStage);
        primaryStage.show();
    }

    @Override
    public AbstractMainPresenter<ExampleService> getMainPresenter() {
        if (mainPresenter==null) {
            mainPresenter = (MainPresenter) load("Main", true);
        }
        return mainPresenter;
    }
            
	@Override
	public ExampleService getService() {
		return this.service;
	}
	
	public void actionNewInfo() {
		NewPresenter pres = (NewPresenter) load("New", true);
		pres.showDialog();
	}
}
