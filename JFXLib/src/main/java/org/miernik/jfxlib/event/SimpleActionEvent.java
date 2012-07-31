/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.event;

/**
 * 
 * @author Miernik
 */
public class SimpleActionEvent implements Event {

	private String actionName;

	public String getActionName() {
		return actionName;
	}

	public SimpleActionEvent(String actionName) {
		this.actionName = actionName;
	}
}
