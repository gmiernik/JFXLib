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
import org.miernik.jfxlib.presenter.AbstractMainPresenter;
import org.miernik.jfxlib.presenter.AbstractPresenter;

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

	public abstract AbstractMainPresenter<?> getMainPresenter();

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

	protected <P extends AbstractPresenter<S>> P load(String fxmlName) {
		return load(fxmlName, false);
	}

	protected <P extends AbstractPresenter<S>> P load(String fxmlName,
			boolean bundle) {
		if (bundle) {
			return load(fxmlName, ResourceBundle.getBundle("views." + fxmlName));
		} else {
			return load(fxmlName, null);
		}
	}

	/**
	 * 
	 * @param <S>
	 *            type of Presenter
	 * @param fxmlFile
	 *            name of FXML file
	 * @param bundle
	 *            resource bundle including texts
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <P extends AbstractPresenter<S>> P load(String fxmlName,
			ResourceBundle bundle) {
		P presenter = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			if (bundle != null) {
				loader.setResources(bundle);
			}
			loader.load(this.getClass().getResourceAsStream(
					"/views/" + fxmlName + ".fxml"));
			presenter = (P) loader.getController();
			presenter.setView((Parent) loader.getRoot());
			presenter.setService(getService());
			presenter.setEventBus(getEventBus());
		} catch (Exception ex) {
			throw new RuntimeException("Unable to load FXML: " + fxmlName, ex);
		}
		return presenter;
	}
}
