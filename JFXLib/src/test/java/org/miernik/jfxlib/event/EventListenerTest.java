package org.miernik.jfxlib.event;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.Test;

public class EventListenerTest {

	class test {
		boolean flag;

		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

	}

	@Test
	public void testNotify() {
		final test t = new test();
		EventListener<TestEvent1> listener = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 event) {
				t.setFlag(true);
			}			
		};
		
		listener.performed(new TestEvent1());
		assertTrue(t.isFlag());
	}

	@Test
	public void testType() {
		EventListener<TestEvent1> listener = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 event) {
			}
		};
		ParameterizedType type = (ParameterizedType) listener.getClass().getGenericInterfaces()[0];
		Type resutl = type.getActualTypeArguments()[0];
		assertEquals(TestEvent1.class, resutl);
		
	}
	
	@Test
	public void testInvokePerformed() {
		final test t = new test();
		EventListener<TestEvent1> listener = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 event) {
				t.setFlag(true);
			}			
		};
		
		listener.performed(new TestEvent1());
		assertTrue(t.isFlag());
		ParameterizedType type = (ParameterizedType) listener.getClass().getGenericInterfaces()[0];
		Type et = type.getActualTypeArguments()[0];
		Class<?> etClass = (Class<?>)et; 
		TestEvent1 event = new TestEvent1();

		Class<?> params[] = new Class[] {etClass};

		try {
			Method m = listener.getClass().getDeclaredMethod("performed", params);
			m.invoke(listener, new Object[] {event});
		} catch (Exception e) {
			e.printStackTrace();
			fail("cannot invoke proper method: performed(String, Class<?>...)");
		}
		assertTrue(t.isFlag());
		assertEquals(etClass, event.getClass());
	}

}
