/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.presenter;

import java.util.ResourceBundle;
import javafx.scene.Parent;
import org.miernik.jfxlib.Service;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.SimpleActionEvent;

/**
 * 
 * @author Miernik
 */
public abstract class BasePresenter<T extends Service> implements Presenter {

	private Parent view;
	private T service;
	private EventBus eventBus;
	private ResourceBundle resource;

	public void setResource(ResourceBundle resource) {
		this.resource = resource;
	}

	public ResourceBundle getResource() {
		return resource;
	}

	protected EventBus getEventBus() {
		return eventBus;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	protected T getService() {
		return service;
	}

	public void setService(T service) {
		this.service = service;
	}

	public void setView(Parent view) {
		this.view = view;
	}

	@Override
	public Parent getView() {
		return this.view;
	}

	/**
	 * Execute simple action by main application controler. In result the mathod
	 * which has name action[actionName] will we executed.
	 * 
	 * @param actionName
	 *            action name without whitespace and special characters
	 */
	protected final void fireAction(String actionName) {
		getEventBus().fireEvent(new SimpleActionEvent(actionName));
	}

	@Override
	public void dispose() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Calling always when you need to update view
	 */
	protected void refresh() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public abstract void show();

	/**
	 * Initialize controller after loading the view
	 */
	public abstract void initialize();

}
