/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.event.SimpleActionEvent;
import org.miernik.jfxlib.event.SimpleEventBus;
import org.miernik.jfxlib.presenter.BaseMainPresenter;
import org.miernik.jfxlib.presenter.BasePresenter;

/**
 * 
 * @author Miernik
 */
public abstract class MVPApplication<S extends Service> extends Application
		implements EventListener<SimpleActionEvent> {

	private EventBus eventBus;

	public EventBus getEventBus() {
		return eventBus;
	}

	public abstract S getService();

	public abstract BaseMainPresenter<?> getMainPresenter();

	public MVPApplication() {
		super();
		this.eventBus = new SimpleEventBus();
		this.eventBus.addListener(this);
	}

	@Override
	public void performed(SimpleActionEvent e) {
		try {
			Method result = this.getClass().getMethod(
					"action" + e.getActionName(), new Class[] {});
			if (result != null) {
				result.invoke(this, new Object[] {});
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException ex) {
			throw new IllegalArgumentException(
					"cannot find proper method to deal with action: "
							+ e.getActionName(), ex);
		}
	}

	protected <P extends BasePresenter<S>> P load(Class<P> controllerClass,
			String fxmlName) throws InstantiationException,
			IllegalAccessException {
		return load(controllerClass, fxmlName, false);
	}

	protected <P extends BasePresenter<S>> P load(Class<P> controllerClass,
			String fxmlName, boolean loadResource)
			throws InstantiationException, IllegalAccessException {
		return load(controllerClass, fxmlName,
				loadResource ? getResourceBundleOfView(fxmlName) : null);
	}

	/**
	 * Load presenter and view object.
	 * 
	 * @param controllerClass
	 *            presenter class
	 * @param fxmlName
	 *            name of view file in FXML notation
	 * @param resource
	 *            resource object included texts and static data
	 * @return controller (presenter) object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected <P extends BasePresenter<S>> P load(Class<P> controllerClass,
			String fxmlName, ResourceBundle resource) {
		try {
			final P controller = controllerClass.newInstance();
			controller.setEventBus(getEventBus());
			controller.setService(getService());
			FXMLLoader loader = new FXMLLoader();
			loader.setController(controller);
			if (resource != null) {
				loader.setResources(resource);
				controller.setResource(resource);
			}
			loader.load(this.getClass().getResourceAsStream(
					"/views/" + fxmlName + ".fxml"));
			controller.setView((Parent) loader.getRoot());
			return controller;
		} catch (Exception ex) {
			throw new RuntimeException("Unable to load FXML: " + fxmlName
					+ ", error: " + getFirstException(ex).getMessage(), ex);
		}
	}

	private Throwable getFirstException(Throwable ex) {
		return ex.getCause() == null ? ex : getFirstException(ex.getCause());
	}

	protected ResourceBundle getResourceBundleOfView(String fxmlName) {
		return ResourceBundle.getBundle("views." + fxmlName);
	}

}
