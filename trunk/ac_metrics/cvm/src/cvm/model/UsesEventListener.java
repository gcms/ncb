package cvm.model;

import java.util.EventListener;


/**
 * Listener interface for up calls for module
 * A contract between a UCIDownEvent source and listener classes.
 */
public interface UsesEventListener extends EventListener {
	public void use(Event event);
}