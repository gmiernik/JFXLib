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
import javafx.stage.Stage;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.event.SimpleActionEvent;
import org.miernik.jfxlib.event.SimpleEventBus;
import org.miernik.jfxlib.presenter.BasePresenter;
import org.miernik.jfxlib.presenter.MainWindowPresenter;

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

	public abstract MainWindowPresenter<S> initMainPresenter(Stage s);
	
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

	protected <P extends BasePresenter<S>> P loadPresenter(
			Class<P> controllerClass, String fxmlName) {
		return loadPresenter(controllerClass, fxmlName, false);
	}

	protected <P extends BasePresenter<S>> P loadPresenter(
			Class<P> controllerClass, String fxmlName, boolean loadResource) {
		return loadPresenter(controllerClass, fxmlName,
				loadResource ? getResourceBundleOfView(fxmlName) : null);
	}

	/**
	 * Load presenter and view object.
	 * 
	 * @param presenterClass
	 *            presenter class
	 * @param fxmlName
	 *            name of view file in FXML notation
	 * @param resource
	 *            resource object included texts and static data
	 * @return controller (presenter) object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected <P extends BasePresenter<S>> P loadPresenter(
			Class<P> presenterClass, String fxmlName, ResourceBundle resource) {
		P presenter = null;
		try {
			presenter = presenterClass.newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			throw new RuntimeException("Unable to create presenter object"
					+ fxmlName + ", error: "
					+ getFirstException(ex).getMessage(), ex);
		}
		if (presenter != null)
			loadView(presenter, fxmlName, resource);
		return presenter;
	}
	
	protected <P extends BasePresenter<S>> P initPresenter(P presenter,
			String fxmlName, ResourceBundle resource) {
		if (presenter==null)
			throw new IllegalArgumentException("Presenter object cannot be null");
		presenter.setEventBus(getEventBus());
		presenter.setService(getService());
		return loadView(presenter, fxmlName, resource);
	}

	protected <P extends BasePresenter<S>> P initPresenter(P presenter,
			String fxmlName) {
		return initPresenter(presenter, fxmlName, false);
	}

	protected <P extends BasePresenter<S>> P initPresenter(P presenter,
			String fxmlName, boolean loadResource) {
		return initPresenter(presenter, fxmlName,
				loadResource ? getResourceBundleOfView(fxmlName) : null);
	}

	private <P extends BasePresenter<S>> P loadView(P presenter,
			String fxmlName, ResourceBundle resource) {
		try {
			presenter.setEventBus(getEventBus());
			presenter.setService(getService());
			FXMLLoader loader = new FXMLLoader();
			loader.setController(presenter);
			if (resource != null) {
				loader.setResources(resource);
				presenter.setResource(resource);
			}
			loader.load(this.getClass().getResourceAsStream(
					"/views/" + fxmlName + ".fxml"));
			presenter.setView((Parent) loader.getRoot());
			return presenter;
		} catch (Exception ex) {
			throw new RuntimeException("Unable to load view from FXML: "
					+ fxmlName + ", error: "
					+ getFirstException(ex).getMessage(), ex);
		}
	}

	private Throwable getFirstException(Throwable ex) {
		return ex.getCause() == null ? ex : getFirstException(ex.getCause());
	}

	protected ResourceBundle getResourceBundleOfView(String fxmlName) {
		return ResourceBundle.getBundle("views." + fxmlName);
	}
	
	@Override
	public final void start(Stage stage) {
		initMainPresenter(stage);
		stage.show();
	}

}
