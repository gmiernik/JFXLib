package org.miernik.jfxlib;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.miernik.jfxlib.event.Event;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.event.SimpleEventBus;
import org.miernik.jfxlib.event.TestEvent1;


/**
 * Unit test for simple App.
 */
@SuppressWarnings("unused")
public class SimpleEventBus2Test {
	
	@Test
	public void testFireEventOutOfDefaultPackage() {
		EventBus bus = new SimpleEventBus();
		EventListener<Event> l1 = new EventListener<Event>() {

			@Override
			public void performed(Event arg0) {
			}
		};
		EventListener<TestEvent1> l2 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 arg0) {
			}
		};

		bus.addListener(l1);
		bus.addListener(l2);
		bus.fireEvent(new Event() {
		});
		bus.fireEvent(new TestEvent1());
	}
}
