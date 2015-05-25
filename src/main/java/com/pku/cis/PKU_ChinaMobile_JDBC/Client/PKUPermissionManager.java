package com.pku.cis.PKU_ChinaMobile_JDBC.Client;

import com.pku.cis.PKU_ChinaMobile_JDBC.GUI.Global;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.P_Users;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PermissionManagerInterface;

import java.rmi.Naming;

/**
 * PKUPermissionManager - This class implements the PermissionManagerInterface and act as the
 * PermissionManager of the client. It communicate with the PermissionManager on
 * the server by RMI.
 * Created by mrpen on 2015/5/25.
 */
public class PKUPermissionManager {
    private PermissionManagerInterface remotePermissionManager;
    P_Users[] users;
    public PKUPermissionManager() throws Exception
    {
        if( remotePermissionManager == null)
            remotePermissionManager = (PermissionManagerInterface) Naming.lookup("rmi://" + Global.IP + ":1099" + "/RemotePermissionManager");
    }

    public boolean insert(String userName, int permission, String password) throws Exception
    {
        return remotePermissionManager.insert(userName, permission, password);
    }
    public boolean remove(String userName) throws  Exception
    {
        return remotePermissionManager.remove(userName);
    }
    public int login(String userName, String passWord) throws  Exception
    {
        return remotePermissionManager.login(userName, passWord);
    }
    public boolean editPermission(String userName, int permission) throws  Exception
    {
        return remotePermissionManager.editPermission(userName, permission);
    }
    public boolean editPassword(String userName, String newPassWord) throws  Exception
    {
        return remotePermissionManager.editPassword(userName, newPassWord);
    }
    public int getUserCount() throws Exception
    {
        return remotePermissionManager.getUserCount();
    }
    public Object[] getUsers() throws Exception
    {
        users = (P_Users[])remotePermissionManager.getUsers();
        return users;
    }
}
