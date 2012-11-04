/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.presenter.BasePresenter;

/**
 *
 * @author Miernik
 */
public class TestPresenter extends BasePresenter<Service> {

	@FXML
	protected Label testLabel;
	
	@Override
	public void initialize() {
		testLabel.setText("initiated");
		if (getEventBus()==null)
			throw new IllegalStateException("EventBus object cannot be NULL");
		if (getService()==null)
			throw new IllegalStateException("Service object cannot be NULL");
	}
	
	public EventBus getEventBus() {
		return super.getEventBus();
	}
    
	public Service getService() {
		return super.getService();
	}
}
