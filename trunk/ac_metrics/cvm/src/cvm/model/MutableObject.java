/* @author Mario J Lorenzo */ package cvm.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public abstract class MutableObject implements Serializable {
	
	protected final transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	protected MutableObject() {
		
	}
	
	public void addPropertyChangeListener( final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener(final String property, final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(property, listener);
	}

	public void removePropertyChangeListener( final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(final String property, final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(property, listener);
	}
	
	public abstract boolean equals(Object obj);
	
	public abstract int hashCode();
	
}
