/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jfxlib.event;

/**
 *
 * @author Miernik
 */
public interface EventBus {
    public void addListener(EventListener<? extends Event> listener);
    public void removeListener(EventListener<? extends Event> listener);
    public void fireEvent(Event event);
}
