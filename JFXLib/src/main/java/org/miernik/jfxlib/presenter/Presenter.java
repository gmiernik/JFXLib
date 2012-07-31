/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.presenter;

import javafx.scene.Parent;

/**
 *
 * @author Miernik
 */
public interface Presenter {

    /**
     * Getting view object of the presenter
     * @return 
     */
    Parent getView();

    /**
     * Calling every time when view is shown
     */
    void show();
    
    /**
     * Calling every time when view is closed
     */
    void dispose();
        
}
