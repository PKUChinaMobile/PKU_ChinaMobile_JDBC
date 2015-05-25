package com.pku.cis.PKU_ChinaMobile_JDBC.Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created by mrpen on 2015/5/25.
 */
public interface PermissionManagerInterface extends Remote
{
    public boolean insert(String userName, int permission, String password) throws Exception;
    public boolean remove(String userName) throws  Exception;
    public int login(String userName, String passWord) throws  Exception;
    public boolean editPermission(String userName, int permission) throws  Exception;
    public boolean editPassword(String userName, String newPassWord) throws  Exception;
    public int getUserCount() throws Exception;
    public Object[] getUsers() throws Exception;
}
