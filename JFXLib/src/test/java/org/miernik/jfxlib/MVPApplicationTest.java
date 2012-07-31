/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib;

import javafx.stage.Stage;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jfxlib.event.SimpleActionEvent;
import org.miernik.jfxlib.presenter.AbstractMainPresenter;

/**
 *
 * @author Miernik
 */
public class MVPApplicationTest {
    
    public MVPApplicationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    class TestApp extends MVPApplication<Service> {
    	public boolean flag = false;

		@Override
		public Service getService() {
			return null;
		}

		@Override
		public AbstractMainPresenter<?> getMainPresenter() {
			return null;
		}

		@Override
		public void start(Stage arg0) throws Exception {
		}
		
		public void actionTest123() {
			flag = true;
		}
    	
    }
        
    @Test
    public void testSimpleAction() {
        final TestApp app = new TestApp();
        final String actionName = "Test123";
        final SimpleActionEvent sae = new SimpleActionEvent(actionName);
        
        app.getEventBus().fireEvent(sae);
        assertTrue(app.flag);
    }
}
