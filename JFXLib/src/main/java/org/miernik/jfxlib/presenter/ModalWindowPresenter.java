/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.presenter;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.miernik.jfxlib.Service;

/**
 * 
 * @author Miernik
 */
public abstract class ModalWindowPresenter<T extends Service> extends WindowPresenter<T> {

	@Override
	public Stage getStage() {
		Stage s = super.getStage();
		s.initModality(Modality.APPLICATION_MODAL);
		return s;
	}
}
