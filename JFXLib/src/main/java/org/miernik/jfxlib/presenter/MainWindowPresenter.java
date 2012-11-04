package org.miernik.jfxlib.presenter;

import org.miernik.jfxlib.Service;

import javafx.stage.Stage;

public class MainWindowPresenter<S extends Service> extends WindowPresenter<S> {

	public MainWindowPresenter(Stage s) {
		super(s);
	}
}
