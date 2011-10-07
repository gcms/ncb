package cvm.model;
import java.util.EventListener;


/**
 * Listener interface for up calls for module
 * A contract between a UCIDownEvent source and listener classes.
 */
public interface Uses_Listener extends EventListener {
	public void use(Handles_Event event);
}