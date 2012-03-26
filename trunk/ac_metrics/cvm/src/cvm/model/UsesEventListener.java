package cvm.model;


import cvm.ncb.oem.pe.SignalInstance;
import java.util.EventListener;


/**
 * Listener interface for up calls for module
 * A contract between a UCIDownEvent source and listener classes.
 */
public interface UsesEventListener extends EventListener {
	public void use(SignalInstance event);
}