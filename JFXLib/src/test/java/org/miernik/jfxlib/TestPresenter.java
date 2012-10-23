/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.miernik.jfxlib.presenter.BasePresenter;

/**
 *
 * @author Miernik
 */
public class TestPresenter extends BasePresenter<Service> {

	@FXML
	protected Label testLabel;
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
		testLabel.setText("initiated");
		if (getEventBus()==null)
			throw new IllegalStateException("EventBus object cannot be NULL");
		if (getService()==null)
			throw new IllegalStateException("Service object cannot be NULL");
	}
    
}
