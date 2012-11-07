/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.presenter;

import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;
import org.miernik.jfxlib.Service;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.SimpleActionEvent;

/**
 * 
 * @author Miernik
 */
public abstract class BasePresenter<T extends Service> implements Presenter {

	final static Logger logger = Logger.getLogger(BasePresenter.class);

	private Parent view;
	private T service;
	private EventBus eventBus;
	private ResourceBundle resource;
	private Window window;
	private Scene scene;
	private boolean initiated = false;
	private ChangeListener<Window> windowChangeListener = new ChangeListener<Window>() {

		@Override
		public void changed(ObservableValue<? extends Window> arg0,
				Window oldValue, Window newValue) {
			logger.debug("changed Window object");
			setWindow(newValue);
		}
	};
	private ChangeListener<Scene> sceneChangeListener = new ChangeListener<Scene>() {

		@Override
		public void changed(ObservableValue<? extends Scene> arg0,
				Scene oldValue, Scene newValue) {
			logger.debug("changed Scene object");
			setScene(newValue);
		}
	};
	private EventHandler<WindowEvent> onShowingHandler = new EventHandler<WindowEvent>() {

		@Override
		public void handle(WindowEvent arg0) {
			logger.debug("run OnShowing handler");
			onShow();
		}
	};

	private EventHandler<WindowEvent> onHidingHandler = new EventHandler<WindowEvent>() {

		@Override
		public void handle(WindowEvent arg0) {
			logger.debug("run OnHiding handler");
			onHide();
		}
	};

	protected void setWindow(Window newWindow) {
		if (this.window != newWindow) {
			if (this.window != null) {
				this.window.removeEventHandler(WindowEvent.WINDOW_SHOWING, onShowingHandler);
				this.window.removeEventHandler(WindowEvent.WINDOW_HIDING, onHidingHandler);
			}
			if (newWindow != null) {
				newWindow.addEventHandler(WindowEvent.WINDOW_SHOWING, onShowingHandler);
				newWindow.addEventHandler(WindowEvent.WINDOW_HIDING, onHidingHandler);
			}
			this.window = newWindow;
			checkOnInit();
		}
	}

	protected void setScene(Scene newScene) {
		if (this.scene != newScene) {
			if (this.scene != null)
				this.scene.windowProperty()
						.removeListener(windowChangeListener);
			if (newScene != null)
				newScene.windowProperty().addListener(windowChangeListener);
			this.scene = newScene;
			if (this.scene != null) {
				if (this.scene.getWindow() != null)
					setWindow(this.scene.getWindow());
				else
					checkOnInit();
			} else
				setWindow(null);
		}
	}

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
		if (this.view != view) {
			logger.debug("set view object");
			this.view = view;
			this.view.sceneProperty().addListener(sceneChangeListener);
			checkOnInit();
		}
	}

	@Override
	public Parent getView() {
		logger.debug("get view object");
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

	/**
	 * Call every time when the hide() method is invoked
	 */
	protected void onHide() {

	}

	/**
	 * Call every time when the show() method is invoked
	 */
	protected void onShow() {
	}

	protected Window getWindow() {
		return window;
	}

	public Scene getScene() {
		return scene;
	}

	void checkOnInit() {
		if (window != null && scene != null && view != null
				&& initiated == false) {
			onInit();
			if (window.isShowing())
				onShow();
			initiated = true;
		}
	}

	/**
	 * Invoke after initialize window, scene and view objects
	 */
	protected void onInit() {
	}
}
