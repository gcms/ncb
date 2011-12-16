package cvm.ncb.adapters;

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 05/12/11
 * Time: 01:49
 * To change this template use File | Settings | File Templates.
 */
public interface NCBUserInfoStore {
    public String getFwUserName(String fwName);
    public String getFwPassword(String fwName);
}
