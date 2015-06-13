package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PermissionManagerInterface;

import java.io.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.sql.DriverManager;
import java.lang.Class;
import java.util.ArrayList;
import java.util.HashMap;

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
    private static String url ="jdbc:mysql://162.105.71.87:3306/";//"jdbc:mysql://162.105.71.247:3306/";

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

    public String getField(String sql) throws Exception
    {
        //System.out.println("getUsers");
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        String field = null;
        while(rs.next()) {
            field = new String((rs.getString(1).trim()).getBytes("ISO-8859-1"));
            break;
        }
        rs.close();
        connection.close();

        return field;
    }

    public ArrayList<String> getFieldArray(String sql) throws Exception
    {
        //System.out.println("getUsers");
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<String> ans = new ArrayList<String>();
        while(rs.next()) {
           ans.add(new String((rs.getString(1).trim()).getBytes("ISO-8859-1")));
        }
        rs.close();
        connection.close();

        return ans;
    }

    //grant username the permission on the database named databaseName
    public boolean grantPermissionOnDatabase(String username, String databaseName) throws Exception
    {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String databaseId = getField("select UID from ldatabase where DBName=\'" + databaseName + "\'");
        if (databaseId == null)
        {
            connection.close();
            throw new Exception("Can not find the database " + databaseName);
        }

        String userId = getField("select UID from users where username=\'" + username + "\'");
        if (userId == null)
        {
            connection.close();
            throw new Exception("Can not find the user " + username);
        }

        String sql = "insert into ldatabaseusers(userId, databaseId) values(";
        sql += "\'" + userId + "\'" ;
        sql += ",\'" + databaseId + "\');";
        int rs = statement.executeUpdate(sql);
        connection.close();
        return rs == 1;
    }

    //cancel username the permission on the database named databaseName
    public boolean canceltPermissionOnDatabase(String username, String databaseName) throws Exception
    {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String databaseId = getField("select UID from ldatabase where DBName=\'" + databaseName + "\'");
        if (databaseId == null)
        {
            connection.close();
            throw new Exception("Can not find the database " + databaseName);
        }

        String userId = getField("select UID from users where username=\'" + username + "\'");
        if (userId == null)
        {
            connection.close();
            throw new Exception("Can not find the user " + username);
        }

        String sql = "delete from ldatabaseusers where userId=";
        sql += "\'" + userId + "\'" ;
        sql += " and databaseId=\'" + databaseId + "\';";
        int rs = statement.executeUpdate(sql);
        connection.close();
        return rs == 1;
    }

    public  String[][] getUsersDatabasePermission() throws Exception
    {
        //System.out.println("getUsers");
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT users.username, ldatabase.DBName FROM ldatabase, ldatabaseusers, users " +
                "where ldatabase.uid=ldatabaseusers.databaseid AND " +
                "ldatabaseusers.userid=users.uid" ;
        ResultSet rs = statement.executeQuery(sql);
        HashMap<String, String>mp = new HashMap<String, String>();
        while(rs.next()) {
            String userName = new String((rs.getString(1).trim()).getBytes("ISO-8859-1"));
            String databaseName = new String((rs.getString(2).trim()).getBytes("ISO-8859-1"));
            mp.put(userName, databaseName);
        }
        rs.close();
        connection.close();

        sql = "select username from users;";
        ArrayList<String> usernames = getFieldArray(sql);
        sql = "select DBName from ldatabase;";
        ArrayList<String> databasenames = getFieldArray(sql);
        String [][] ans = new String[usernames.size() * databasenames.size()][3];
        for (int i = 0, k = 0; i < usernames.size(); ++i)
            for (int j = 0; j < databasenames.size(); ++j)
            {
                ans[k][0] = usernames.get(i);
                ans[k][1] = databasenames.get(j);
                ans[k][2] = "false";
                if (ans[k][1].equals(mp.get(usernames.get(i))))
                    ans[k][2] = "true";

                ++k;
            }

        return ans;
    }


    //only for testing
/*
    public static void main(String[]args) throws  Exception
    {
        PermissionManager pm = new PermissionManager();
        PrintStream out = System.out;
        out.println("-------------------");
        String usersdatabase = "DD";
        //pm.remove(usersdatabase);
        pm.insert(usersdatabase, 2, "123456");
        String [][] ans = pm.getUsersDatabasePermission();
        for (int i = 0; i < ans.length; ++i)
            if(ans[i][0].equals(usersdatabase))
            {
                for (int j = 0; j < 3; ++j)
                    out.print(ans[i][j] + " ");
                out.println("");
            }
        pm.grantPermissionOnDatabase(usersdatabase, "mytest");
        ans = pm.getUsersDatabasePermission();
        for (int i = 0; i < ans.length; ++i)
            if(ans[i][0].equals(usersdatabase))
            {
                for (int j = 0; j < 3; ++j)
                    out.print(ans[i][j] + " ");
                out.println("");
            }
        pm.canceltPermissionOnDatabase(usersdatabase, "mytest");
        ans = pm.getUsersDatabasePermission();
        for (int i = 0; i < ans.length; ++i)
            if(ans[i][0].equals(usersdatabase))
            {
                for (int j = 0; j < 3; ++j)
                    out.print(ans[i][j] + " ");
                out.println("");
            }
        pm.remove(usersdatabase);
        out.println("-------------------");

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

