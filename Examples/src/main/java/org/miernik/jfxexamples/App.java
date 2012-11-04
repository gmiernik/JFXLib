/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxexamples;

import javafx.stage.Stage;

import org.miernik.jfxexamples.presenter.MainPresenter;
import org.miernik.jfxexamples.presenter.NewPresenter;
import org.miernik.jfxexamples.presenter.SampleWindowPresenter;
import org.miernik.jfxlib.MVPApplication;
import org.miernik.jfxlib.presenter.MainWindowPresenter;

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
    
    private SampleWindowPresenter sampleWindowPresenter;
    private ExampleService service = new ExampleService();
                    
	@Override
	public ExampleService getService() {
		return this.service;
	}
	
	public void actionNewInfo() {
		NewPresenter pres = loadPresenter(NewPresenter.class, "New", true);
		pres.show();
	}
	
	public void actionOpenSampleWindow() {
		getSampleWindowPresenter().show();
	}

	public SampleWindowPresenter getSampleWindowPresenter() {
		if (sampleWindowPresenter==null) {
			sampleWindowPresenter = loadPresenter(SampleWindowPresenter.class, "SampleWindow");
		}
		return sampleWindowPresenter;
	}

	@Override
	public MainWindowPresenter<?> initMainPresenter(Stage s) {
		return initPresenter(new MainPresenter(s), "Main", true);
	}

}
