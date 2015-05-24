package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

/**
 * Created by lyy on 2015/5/24.
 */
public class PermissionManager {

    public static P_Users[] getUsers(){return null;}
    public static boolean insert(String userName, String permission, String password){return false;}
    public static boolean remove(String userName){return false;}
    public static int login(String userName, String passWord){return 0;}
    public static boolean editPermission(String userName, String permission){return false;}
    public static boolean editPassword(String userName, String newPassWord){return false;}
}
class P_Users
{
    String userName;
    int permission;//0-非法；1-普通；2-管理员
}
