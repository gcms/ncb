package org.jivesoftware.smackx.jingle.mediaimpl.jmf;
import java.util.EventListener;

import javax.media.protocol.DataSource;



public interface ImageEventReadyListener extends EventListener {

	public void ImageEventReady(DataSource ds);
}
