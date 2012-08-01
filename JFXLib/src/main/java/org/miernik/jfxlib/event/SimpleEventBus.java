/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Miernik
 */
public class SimpleEventBus implements EventBus {

	private Map<Type, List<EventListener<? extends Event>>> listenersMap;

	protected Map<Type, List<EventListener<? extends Event>>> getListenersMap() {
		return listenersMap;
	}

	public SimpleEventBus() {
		listenersMap = new HashMap<>();
	}

	protected final ParameterizedType findEventListenerType(Class<?> classObj) {

		Type[] types = classObj.getGenericInterfaces();
		if (types.length == 0) {
			return findEventListenerType(classObj.getSuperclass());
		} else {
			for (int i = 0; i < types.length; i++) {
				ParameterizedType type = (ParameterizedType) types[i];
				if (EventListener.class.equals(type.getRawType()))
					return type;
			}
		}
		throw new IllegalStateException("cannot find EventListener interface");
	}

	protected final <T extends EventListener<? extends Event>> Class<?> getEventType(
			T obj) {
		ParameterizedType type = findEventListenerType(obj.getClass());
		return (Class<?>) type.getActualTypeArguments()[0];
	}

	@Override
	public void addListener(EventListener<? extends Event> listener) {
		Type type = getEventType(listener);
		if (!listenersMap.containsKey(type)) {
			listenersMap.put(type,
					new ArrayList<EventListener<? extends Event>>());
		}
		List<EventListener<? extends Event>> listeners = listenersMap.get(type);
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void fireEvent(Event event) {
		if (listenersMap.containsKey(event.getClass())) {
			List<EventListener<? extends Event>> listeners = listenersMap
					.get(event.getClass());
			for (Iterator<EventListener<? extends Event>> it = listeners
					.iterator(); it.hasNext();) {
				EventListener<? extends Event> eventListener = it.next();

				/*
				 * // check listener event type to event type Class<?>
				 * listenerEventClass = getEventType(eventListener); if
				 * (!listenerEventClass.equals(event.getClass())) { throw new
				 * IllegalStateException
				 * ("wrong event type was connected to listener"); }
				 */

				// run method of listener by reflection
				Method m = null;
				try {
					m = eventListener.getClass().getMethod("performed",
							new Class[] { event.getClass() });

				} catch (NoSuchMethodException | SecurityException ex) {
					throw new IllegalStateException(
							"cannot find method: performed() of the listener",
							ex);
				}

				try {
					m.invoke(eventListener, new Object[] { event });
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException ex) {
					throw new IllegalStateException(
							"error while invoke method: performed() of the listener",
							ex);
				}

			}
		}
	}

	@Override
	public void removeListener(EventListener<? extends Event> listener) {
		Type type = getEventType(listener);
		if (listenersMap.containsKey(type)) {
			List<EventListener<? extends Event>> listeners = listenersMap
					.get(type);
			if (listeners.contains(listener))
				listeners.remove(listener);
		}
	}
}
