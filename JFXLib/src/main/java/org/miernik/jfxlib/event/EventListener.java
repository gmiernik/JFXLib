/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.event;


/**
 *
 * @author Miernik
 */
public interface EventListener<T extends Event> extends java.util.EventListener {
	void performed(T event);
}