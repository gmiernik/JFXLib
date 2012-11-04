/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.presenter;

import javafx.stage.Modality;
import org.miernik.jfxlib.Service;

/**
 * 
 * @author Miernik
 */
public abstract class ModalWindowPresenter<T extends Service> extends
		WindowPresenter<T> {

	public ModalWindowPresenter() {
		super();
		getStage().initModality(Modality.APPLICATION_MODAL);
	}

}
