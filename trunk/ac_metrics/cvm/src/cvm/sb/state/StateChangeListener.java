package cvm.sb.state;

/**
 * Created by IntelliJ IDEA.
 * User: gustavo
 * Date: Aug 3, 2012
 * Time: 5:29:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateChangeListener {
    void registerCreated(StateHolder register);
    void registerDestroyed(StateHolder register);
    void registerChanged(StateHolder register, String[] properties);
}
