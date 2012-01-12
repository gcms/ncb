package cvm.ncb.adapters;

import cvm.model.Event;
import cvm.model.EventException;

/**
 * Created by IntelliJ IDEA.
 * User: gustavosousa
 * Date: 04/01/12
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public interface EventNotifier {
    public void notify(Event event);
    public void throwEvent(Event event) throws EventException;
}
