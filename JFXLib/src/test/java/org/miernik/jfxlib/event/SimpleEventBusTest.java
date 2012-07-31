/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 * 
 * @author Miernik
 */
public class SimpleEventBusTest {

	public SimpleEventBusTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testConstructor() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		assertNotNull(eventBus);
		assertNotNull(eventBus.getListenersMap());
	}

	@Test
	public void testAddListener() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final Type type = TestEvent1.class;
		final EventListener<TestEvent1> listener = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 event) {
			}
		};

		eventBus.addListener(listener);

		// check
		Map<Type, List<EventListener<? extends Event>>> listeners = eventBus
				.getListenersMap();
		assertNotNull(listeners);
		assertTrue(listeners.containsKey(type));
		assertNotNull(listeners.get(type));
		assertTrue(listeners.get(type).contains(listener));
	}

	@Test
	public void testAddListener2() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final Type type = TestEvent1.class;
		final EventListener<TestEvent1> listener = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 event) {
			}

		};

		eventBus.addListener(listener);
		eventBus.addListener(listener);

		// check
		Map<Type, List<EventListener<? extends Event>>> listeners = eventBus
				.getListenersMap();
		assertNotNull(listeners);
		assertEquals(1, listeners.keySet().size());
		assertTrue(listeners.containsKey(type));
		assertNotNull(listeners.get(type));
		assertTrue(listeners.get(type).contains(listener));
	}

	@Test
	public void testAddListener3() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final Type type = TestEvent1.class;
		final EventListener<TestEvent1> listener1 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};
		final EventListener<TestEvent1> listener2 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};

		eventBus.addListener(listener1);
		eventBus.addListener(listener2);

		// check
		Map<Type, List<EventListener<? extends Event>>> listeners = eventBus
				.getListenersMap();
		assertNotNull(listeners);
		assertTrue(listeners.containsKey(type));
		assertNotNull(listeners.get(type));
		assertEquals(2, listeners.get(type).size());
		assertTrue(listeners.get(type).contains(listener1));
		assertTrue(listeners.get(type).contains(listener2));
	}

	protected class ResultListener {
		public boolean executed = false;
	}

	@Test
	public void testFireEvent() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final ResultListener result1 = new ResultListener();
		final ResultListener result2 = new ResultListener();
		final EventListener<TestEvent1> listener1 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 arg0) {
				result1.executed = true;
			}
		};
		final EventListener<TestEvent1> listener2 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 arg0) {
				result2.executed = true;
			}
		};

		eventBus.addListener(listener1);
		eventBus.addListener(listener2);

		eventBus.fireEvent(new TestEvent1());

		// check
		assertTrue(result1.executed);
		assertTrue(result2.executed);
	}

	@Test
	public void testFireEvent2() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final TestEvent1 event1 = new TestEvent1();
		final ResultListener result1 = new ResultListener();
		final ResultListener result2 = new ResultListener();
		final EventListener<TestEvent1> listener1 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 arg0) {
				result1.executed = true;
			}
		};
		final EventListener<TestEvent2> listener2 = new EventListener<TestEvent2>() {

			@Override
			public void performed(TestEvent2 arg0) {
				result2.executed = true;
			}
		};

		eventBus.addListener(listener1);
		eventBus.addListener(listener2);

		eventBus.fireEvent(event1);

		// check
		assertTrue(result1.executed);
		assertFalse(result2.executed);
	}

	@Test
	public void testRemoveListener() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final Type type = TestEvent1.class;
		final EventListener<TestEvent1> listener = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};
		eventBus.addListener(listener);

		eventBus.removeListener(listener);

		// check
		Map<Type, List<EventListener<? extends Event>>> listeners = eventBus
				.getListenersMap();
		assertNotNull(listeners);
		assertTrue(listeners.containsKey(type));
		assertNotNull(listeners.get(type));
		assertFalse(listeners.get(type).contains(listener));
	}

	@Test
	public void testRemoveListener2() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final Type type = TestEvent1.class;
		final EventListener<TestEvent1> listener1 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};
		final EventListener<TestEvent1> listener2 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};
		eventBus.addListener(listener1);
		eventBus.addListener(listener2);

		eventBus.removeListener(listener1);

		// check
		Map<Type, List<EventListener<? extends Event>>> listeners = eventBus
				.getListenersMap();
		assertNotNull(listeners);
		assertTrue(listeners.containsKey(type));
		assertNotNull(listeners.get(type));
		assertFalse(listeners.get(type).contains(listener1));
		assertTrue(listeners.get(type).contains(listener2));
	}

	@Test
	public void testGetEventType() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final EventListener<TestEvent2> el = new EventListener<TestEvent2>() {

			@Override
			public void performed(TestEvent2 event) {
			}
		};

		Class<?> result = eventBus.getEventType(el);
		assertNotNull(result);
		assertEquals(TestEvent2.class, result);
	}

	abstract class AEventListener implements EventListener<TestEvent1> {
		public boolean flag = false;

		@Override
		public void performed(TestEvent1 event) {
			flag = true;
		}
	}

	class MyEventListener extends AEventListener {

	}

	@Test
	public void testFindEventListenerType() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final MyEventListener el1 = new MyEventListener();
		final EventListener<TestEvent1> el2 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 event) {
			}
		};

		ParameterizedType result = eventBus.findEventListenerType(el1
				.getClass());
		assertNotNull(result);
		assertEquals(EventListener.class, result.getRawType());

		ParameterizedType result2 = eventBus.findEventListenerType(el2
				.getClass());
		assertNotNull(result2);
		assertEquals(EventListener.class, result2.getRawType());
	}

	@Test
	public void testGetEventType2() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final MyEventListener el = new MyEventListener();

		Class<?> result = eventBus.getEventType(el);
		assertNotNull(result);
		assertEquals(TestEvent1.class, result);
	}

	@Test
	public void testFireEvent3() {
		final SimpleEventBus eventBus = new SimpleEventBus();
		final MyEventListener listener = new MyEventListener();

		eventBus.addListener(listener);

		eventBus.fireEvent(new TestEvent1());

		// check
		assertTrue(listener.flag);
	}

}
