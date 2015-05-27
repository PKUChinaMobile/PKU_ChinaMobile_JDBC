package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PermissionManagerInterface;

import java.io.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.sql.DriverManager;
import java.lang.Class;
import java.util.ArrayList;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.P_Users;

/**
 * Created by lyy on 2015/5/24.
 */
public class PermissionManager extends UnicastRemoteObject
        implements PermissionManagerInterface
{
    private static String driverName ="com.mysql.jdbc.Driver";
    private static String userName ="root";
    private static String userPasswd ="06948859";//"root";
    private static String dbName ="pku_chinamobile_jdbc_metadata";
    private static String url ="jdbc:mysql://162.105.71.128:3306/";//"jdbc:mysql://162.105.71.247:3306/";

    PermissionManager() throws Exception{}
    private static Connection getConnection() throws  Exception
    {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        return DriverManager.getConnection(url + dbName, userName, userPasswd);
    }

    public  P_Users[] getUsers() throws Exception
    {
        //System.out.println("getUsers");
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT username, permission FROM users" ;
        ResultSet rs = statement.executeQuery(sql);
        ArrayList ans = new ArrayList();
        while(rs.next()) {
            String userName = new String((rs.getString(1).trim()).getBytes("ISO-8859-1"));
            int permission = rs.getInt(2);
            ans.add(new P_Users(userName, permission));
        }
        rs.close();
        connection.close();

        return (P_Users[])ans.toArray(new P_Users[0]);
    }

    public int getUserCount() throws Exception{
        return getUsers().length;
    }
    public boolean insert(String userName, int permission, String password) throws  Exception
    {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "insert into users(username, permission, password) values(";
        sql += "\'" + userName + "\'" ;
        sql += "," + permission;
        sql += ",md5(\'" + password + "\'));" ;
        int rs = statement.executeUpdate(sql);
        connection.close();
        return rs == 1;
    }

    public boolean remove(String userName) throws  Exception {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "delete from users where username=";
        sql += "\'" + userName + "\';";
        int rs = statement.executeUpdate(sql);
        connection.close();
        return rs == 1;
    }

    public int login(String userName, String passWord) throws  Exception
    {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT permission FROM users where username=\'" ;
        sql += userName + "\'";
        sql += " and password=md5(\'";
        sql += passWord + "\');";
        ResultSet rs = statement.executeQuery(sql);
        int permission = 0;
        if (rs.next())
            permission = rs.getInt(1);
        rs.close();
        connection.close();
        return permission;
    }
    public boolean editPermission(String userName, int permission) throws  Exception
    {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "update users set permission=" + permission + "  where username=";
        sql += "\'" + userName + "\';";
        int rs = statement.executeUpdate(sql);
        connection.close();
        return rs == 1;
    }

    public boolean editPassword(String userName, String newPassWord) throws  Exception
    {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "update users set password=md5(\'" + newPassWord + "\')  where username=";
        sql += "\'" + userName + "\';";
        int rs = statement.executeUpdate(sql);
        connection.close();
        return rs == 1;
    }

    //only for testing
    /*
    public static void main(String[]args) throws  Exception
    {
        PermissionManager pm = new PermissionManager();
        PrintStream out = System.out;
        P_Users[] users = (P_Users[])pm.getUsers();
        out.println("test GetUsers:");
        for (int i = 0; i < users.length; ++i)
            out.println(users[i].userName + " " + users[i].permission);

        out.println("test insert:");
        out.println("remove:" + pm.remove("userName"));
        out.println("insert:" + pm.insert("userName", 1, "password"));

        out.println("test remove:");
        out.println("remove:" + pm.remove("userName"));

        out.println("test login:");
        out.println("login userName:" + pm.login("userName", "password"));
        out.println("insert userName:" +pm.insert("userName", 1, "password"));
        out.println("login userName:" + pm.login("userName", "password"));
        out.println("remove userName:" + pm.remove("userName"));
        out.println("insert userName:" + pm.insert("userName", 2, "password"));
        out.println("login userName:" + pm.login("userName", "password"));
        out.println("remove userName:" + pm.remove("userName"));

        out.println("test editPermission:");
        out.println("insert userName:" + pm.insert("userName", 1, "password"));
        users = (P_Users[])pm.getUsers();
        out.println("GetUsers:");
        for (int i = 0; i < users.length; ++i)
            out.println(users[i].userName + " " + users[i].permission);
        users = (P_Users[])pm.getUsers();
        out.println("editPermission:" + pm.editPermission("userName", 2));
        out.println("GetUsers:");
        for (int i = 0; i < users.length; ++i)
            out.println(users[i].userName + " " + users[i].permission);
        out.println("remove userName:" + pm.remove("userName"));

        out.println("test editPassword:");
        out.println("insert userName:" + pm.insert("userName", 1, "password"));
        out.println("login userName:" + pm.login("userName", "password"));
        out.println("editPassword:" + pm.editPassword("userName", "passWord"));
        out.println("login userName:" + pm.login("userName", "password"));
        out.println("remove userName:" + pm.remove("userName"));
    }*/
}

