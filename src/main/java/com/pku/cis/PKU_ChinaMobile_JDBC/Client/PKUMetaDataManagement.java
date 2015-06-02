package com.pku.cis.PKU_ChinaMobile_JDBC.Client;

import com.pku.cis.PKU_ChinaMobile_JDBC.examples.Test;
import com.sun.tools.internal.xjc.api.Mapping;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by Ravy on 2015/5/21.
 */
public class PKUMetaDataManagement {
    /*
    static String userName ="C##MYTEST";
    static String userPasswd ="123456";
    static String urlPrefix = "jdbc:PKUDriver:";
    static String IP = "127.0.0.1";
    static PKUDriver d = new PKUDriver();
    static PKUConnection con;
    */

    static String userName ="root";
    static String userPasswd ="06948859";
    static String urlPrefix = "jdbc:mysql:";
    static String IP = "162.105.71.128:3306/pku_chinamobile_jdbc_metadata";
    //static PKUDriver d = new PKUDriver();
    static Connection con;

    static String UDBName = "test";
    static String UTableName[] = {"callRecords", "smsRecords", "USERS", "U_Background", "U_Communication", "U_Status"};
    static String UColumnName[][] = {{"biSessID", "dualTime", "intYear", "intMonth", "intDay", "intHour", "intMinute", "vcCallingIMSI", "vcCalledIMSI", "intLocation"},
                                      {"biSessID", "intYear", "intMonth", "intDay", "intHour", "intMinute", "vcTextingIMSI", "vcTextedIMSI", "intLocation"},
                                      {"IMSI", "LOCATION", "GENDER", "AGE"},
                                      {"IMSI", "IndustryType", "WorkType", "WorkUnit", "WorkPosition", "MaritalStatus", "Education", "Credit", "ID", "Asset", "Salary"},
                                      {"IMSI", "PhoneNumber", "ZipCode", "Email", "DeliveryAddress", "FaxNumber", "Preference"},
                                      {"IMSI", "Status", "IsImportant", "BonusPoint", "UserLevel", "VIPID"}};
    static int UTableNum = 6;
    static int UColumnNum[] = {10, 9, 4, 11, 7, 6};

    static String LDSType[] = {"oracle", "teradata", "hive2"};
    static String LDSIP[] = {"162.105.71.128", "162.105.71.170", "162.105.71.247"};
    static String LDSPort[] = {"1521", "NULL", "10000"};
    static String LDSName[] = {"Oracle_1", "Teradata_1", "Hive_1"};
    static String LDBName[][] = {{"mytest"}, {"vmtest"}, {"test"}};
    static String LDBUserName[][] = {{"SYSTEM"}, {"vmtest"}, {"hadoop"}};
    static String LDBPassword[][] = {{"oracle1ORACLE"}, {"vmtest"}, {""}};
    static String ColumnType[] = {"varchar(32)", "int", "varchar(45)"};
    static int LColumnType[][] = {{0, 1, 1, 1, 1, 1, 1, 0, 0, 1},
                                   {0, 1, 1, 1, 1, 1, 0, 0, 1},
                                   {0, 1, 1, 1},
                                   {0, 1, 1, 2, 2, 1, 1, 1, 0, 1, 1},
                                   {0, 0, 0, 0, 0, 0, 1},
                                   {0, 1, 1, 1, 1, 0}};
    static int LColumnConType[] = {1, 1, 0, 0, 0, 0};
    static int LColumnMax[][][] = {{{Integer.MAX_VALUE, Integer.MAX_VALUE, 10, 10, 10, 10}}, {{2014, 2014, 21, 21, 21, 21}}, {{2013, 2013, 20, 20, 20, 20}}};
    static int LColumnMin[][][] = {{{2015, 2015, -1, -1, -1, -1}}, {{2014, 2014, -1, -1, -1, -1}}, {{0, 0, -1, -1, -1, -1}}};
    static int LDSNum = 3;
    static int LDBNum[] = {1, 1, 1};

    /*
    static PKUResultSet rs;
    static PKUResultSetMetaData rmeta;
    */

    static ResultSet rs;
    static ResultSetMetaData rmeta;

    static int LColumnNum;
    static String LDataSourceURLList[];
    static String LDataSourceTypeList[];
    static String LDataBaseList[];
    static String LDataBaseUserList[];
    static String LDataBasePwdList[];
    static String LTableList[];
    static String LColumnList[];

    static String tarList[];
    static String tableList[];
    static String conList[];
    static String valList[];

    /**/
    static String showDS[][];

    static String showUTable[][];
    static String showUColumn[][];
    static String showLDS[][];
    static String showLDB[][];
    static String showLTable[][];
    static String showLColumn[][];

    static String showMappingU[][];
    static String showMappingL[][];

    /*Tree Can Fetch*/
    static String UT[];
    static String UC[][];
    static String LSID[];
    static String LS[];
    static String LB[][];
    static String LT[][][];
    static String LC[][][][];

    static int NoMapUCID[];
    static String NoMapUCName[];
    static String NoMapUTName[];
    static int NoMapLCID[];
    static String NoMapLCName[];
    static String NoMapLTName[];
    static String NoMapLBName[];
    static String NoMapLSName[];

    public static void main(String args[]) {
        PKUMetaDataManagement PKU_MDM = new PKUMetaDataManagement();
        Scanner input = new Scanner(System.in);
        PKU_MDM.Init();

        String UDBName;
        String UTableName;
        String UColumnName;
        int conType;
        int conMax;
        int conMin;
        System.out.println("请输入数据库名（UDataBase）：");
        UDBName = input.nextLine();
        System.out.println("请输入表名（UTable）：");
        UTableName = input.nextLine();
        System.out.println("请输入列名（UColumn）：");
        UColumnName = input.nextLine();
        /*System.out.println("请输入划分属性类型（conType），location(0)，intYear(1)：");
        conType = input.nextInt();*/
        System.out.println("请输入划分属性最大值（conMax）：");
        conMax = input.nextInt();
        System.out.println("请输入划分属性最小值（conMin）：");
        conMin = input.nextInt();

        PKU_MDM.Mapping(UDBName, UTableName, UColumnName, conMax, conMin);

        for (int i = 0; i < LColumnNum; i++) {
            System.out.println(LColumnList[i] + " " + LTableList[i] + " " + LDataBaseList[i] + " " + LDataSourceURLList[i] + " " + LDataSourceTypeList[i]);
        }

        PKU_MDM.CloseCon();
    }

    public void Init() {
        String fullURL = urlPrefix + IP; //
        String userName ="root";//root
        String userPasswd ="06948859";//06948859
        String dbName ="pku_chinamobile_jdbc_metadata";
        String url ="jdbc:mysql://162.105.71.128:3306/";

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection( url + dbName, userName, userPasswd );

        }
        catch( Exception ex ){
            System.out.println( ex );
            System.exit(0);
        }
    }
    public void CloseCon() {
        try {
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Insert(String Table, int valNum) {
        String sql = "INSERT INTO " + Table + " values(null";
        for (int i = 0; i < valNum; i++) {
            sql += "," + valList[i];
        }
        sql += ")";
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            System.out.println("Executing " + sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void Select(int tarNum, int tableNum, int conNum) {
        String sql = "SELECT ";
        if (tarNum > 1) {
            for (int i = 0; i < tarNum - 1; i++) {
                sql += tarList[i] + ",";
            }
        }
        sql += tarList[tarNum - 1] + " FROM ";
        if (tableNum > 1) {
            for (int i = 0; i < tableNum - 1; i++) {
                sql += tableList[i] + ",";
            }
        }
        sql += tableList[tableNum - 1];
        if (conNum > 0) {
            sql += " WHERE ";
            if (conNum > 1) {
                for (int i = 0; i < conNum - 1; i++) {
                    sql += conList[i] + " AND ";
                }
            }
            sql += conList[conNum - 1];
        }
        Statement stmt;
        try {
            stmt = con.createStatement();
            System.out.println("Executing " + sql);
            rs = stmt.executeQuery(sql);
            rmeta = rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void Update(String Table, int tarNum, int conNum) {
        String sql = "Update " + Table + " Set ";
        if (tarNum > 1) {
            for (int i = 0; i < tarNum - 1; i++) {
                sql += tarList[i] + "=" + valList[i] + ",";
            }
        }
        sql += tarList[tarNum - 1] + "=" + valList[tarNum - 1];
        if (conNum > 0) {
            sql += " WHERE ";
            if (conNum > 1) {
                for (int i = 0; i < conNum - 1; i++) {
                    sql += conList[i] + " AND ";
                }
            }
            sql += conList[conNum - 1];
        }
        Statement stmt;
        try {
            stmt = con.createStatement();
            System.out.println("Executing " + sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void Delete(String Table, int conNum) {
        String sql = "Delete from " + Table;
        if (conNum > 0) {
            sql += " WHERE ";
            if (conNum > 1) {
                for (int i = 0; i < conNum - 1; i++) {
                    sql += conList[i] + " AND ";
                }
            }
            sql += conList[conNum - 1];
        }
        Statement stmt;
        try {
            stmt = con.createStatement();
            System.out.println("Executing " + sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addLDataSource(String Type, String IP, String Name, String Port) {
        tarList = new String[1];
        tableList = new String[1];
        conList = new String[1];

        int typeID = -1;
        int DSID = -1;
        try {
            tarList[0] = "UID";
            tableList[0] = "LDataSourceType";
            conList[0] = "Type='" + Type + "'";
            Select(1, 1, 1);
            if (!rs.next()) {
                typeID = addLDataSourceType(Type);
            }
            else {
                rs.beforeFirst();
                while (rs.next()) {
                    typeID = Integer.parseInt(new String((rs.getString(1).trim())));
                }
            }
            valList = new String[4];
            valList[0] = typeID + "";
            valList[1] = "'" + IP + "'";
            valList[2] = "'" + Name + "'";
            valList[3] = "'" + Port + "'";
            Insert("LDataSource", 4);

            tarList[0] = "UID";
            tableList[0] = "LDataSource";
            conList[0] = "Name='" + Name + "'";
            Select(1, 1, 1);
            while(rs.next()) {
                DSID = Integer.parseInt(new String((rs.getString(1).trim())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DSID;
    }
    public int addLDataSourceType(String Type) {
        tarList = new String[1];
        tableList = new String[1];
        conList = new String[1];
        valList = new String[1];

        int typeID = -1;
        try {
            valList[0] = "'" + Type + "'";
            Insert("LDataSourceType", 1);

            tarList[0] = "UID";
            tableList[0] = "LDataSourceType";
            conList[0] = "Type='" + Type + "'";
            Select(1, 1, 1);
            while(rs.next()) {
                typeID = Integer.parseInt(new String((rs.getString(1).trim())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeID;
    }
    public int addLDataBase(int DSID, String DBName, String UserName, String Password) {
        tarList = new String[1];
        tableList = new String[1];
        conList = new String[1];
        valList = new String[4];

        int DBID = -1;
        try {
            valList[0] = DSID + "";
            valList[1] = "'" + DBName + "'";
            valList[2] = "'" + UserName + "'";
            valList[3] = "'" + Password + "'";
            Insert("LDataBase", 4);

            tarList[0] = "UID";
            tableList[0] = "LDataBase";
            conList[0] = "DBName='" + DBName + "'";
            Select(1, 1, 1);
            while(rs.next()) {
                DBID = Integer.parseInt(new String((rs.getString(1).trim())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DBID;
    }
    public int addLTable(int DBID, String TableName) {
        tarList = new String[1];
        tableList = new String[1];
        conList = new String[2];
        valList = new String[2];

        int TableID = -1;
        try {
            valList[0] = DBID + "";
            valList[1] = "'" + TableName + "'";
            Insert("LTable", 2);

            tarList[0] = "UID";
            tableList[0] = "LTable";
            conList[0] = "TableName='" + TableName + "'";
            conList[1] = "DBID=" + DBID;
            Select(1, 1, 2);
            while(rs.next()) {
                TableID = Integer.parseInt(new String((rs.getString(1).trim())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TableID;
    }
    public int addLColumn(int TableID, String LColumn, String LColumnType) {
        tarList = new String[1];
        tableList = new String[1];
        conList = new String[1];

        int typeID = -1;
        int LCID = -1;
        try {
            tarList[0] = "UID";
            tableList[0] = "LColumnType";
            conList[0] = "LColumnType='" + LColumnType + "'";
            Select(1, 1, 1);
            if (!rs.next()) {
                typeID = addLColumnType(LColumnType);
            }
            else {
                rs.beforeFirst();
                while (rs.next()) {
                    typeID = Integer.parseInt(new String((rs.getString(1).trim())));
                }
            }
            valList = new String[3];
            valList[0] = TableID + "";
            valList[1] = "'" + LColumn + "'";
            valList[2] = typeID + "";
            Insert("LColumn", 3);

            tarList[0] = "UID";
            tableList[0] = "LColumn";
            conList = new String[2];
            conList[0] = "ColumnName='" + LColumn + "'";
            conList[1] = "TableID=" + TableID;
            Select(1, 1, 2);
            while(rs.next()) {
               LCID = Integer.parseInt(new String((rs.getString(1).trim())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LCID;
    }
    public int addLColumnType(String LColumnType) {
        tarList = new String[1];
        tableList = new String[1];
        conList = new String[1];
        valList = new String[1];

        int typeID = -1;
        try {
            valList[0] = "'" + LColumnType + "'";
            Insert("LColumnType", 1);

            tarList[0] = "UID";
            tableList[0] = "LColumnType";
            conList[0] = "LColumnType='" + LColumnType + "'";
            Select(1, 1, 1);
            while(rs.next()) {
                typeID = Integer.parseInt(new String((rs.getString(1).trim())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeID;
    }

    public int addUDataBase(String DBName) {
        tarList = new String[1];
        tableList = new String[1];
        conList = new String[1];
        valList = new String[1];

        int UID = -1;
        try {
            valList[0] = "'" + DBName + "'";
            Insert("UDataBase", 1);

            tarList[0] = "UID";
            tableList[0] = "UDataBase";
            conList[0] = "DBName='" + DBName + "'";
            Select(1, 1, 1);
            while(rs.next()) {
                UID = Integer.parseInt(new String((rs.getString(1).trim())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UID;
    }
    public int addUTable(String TableName, int DBID) throws Exception{
        tarList = new String[1];
        tableList = new String[1];
        conList = new String[2];
        valList = new String[2];

        int UID = -1;
        try {
            valList[0] = "'" + TableName + "'";
            valList[1] = DBID + "";
            Insert("UTable", 2);

            tarList[0] = "UID";
            tableList[0] = "UTable";
            conList[0] = "TableName='" + TableName + "'";
            conList[1] = "DBID=" + DBID;
            Select(1, 1, 2);
            while(rs.next()) {
                UID = Integer.parseInt(new String((rs.getString(1).trim())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return UID;
    }
    public int addUColumn(String ColumnName, int UTableID) {
        tarList = new String[1];
        tableList = new String[1];
        conList = new String[2];
        valList = new String[2];

        int UID = -1;
        try {
            valList[0] = "'" + ColumnName + "'";
            valList[1] = UTableID + "";
            Insert("UColumn", 2);

            tarList[0] = "UID";
            tableList[0] = "UColumn";
            conList[0] = "ColumnName='" + ColumnName + "'";
            conList[1] = "UTableID=" + UTableID;
            Select(1, 1, 2);
            while(rs.next()) {
                UID = Integer.parseInt(new String((rs.getString(1).trim())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UID;
    }

    public void Mapping(String UDataBase, String UTable, String UColumn, int conMax, int conMin) {
        int UCID = -1;
        int UTID = -1;
        int UDID = -1;
        int conType = -1;

        int LColumnIDList[];
        int count = 0;

        try {
            tarList = new String[1];
            tarList[0] = "UID";
            tableList = new String[1];
            tableList[0] = "UDataBase";
            conList = new String[1];
            conList[0] = "DBName='" + UDataBase + "'";

            Select(1, 1, 1);
            while(rs.next()) {
                UDID = Integer.parseInt(new String((rs.getString(1).trim())));
            }

            tarList[0] = "UID";
            tableList[0] = "UTable";
            conList = new String[2];
            conList[0] = "TableName='" + UTable + "'";
            conList[1] = "DBID=" + UDID;

            Select(1, 1, 2);
            while(rs.next()) {
                UTID = Integer.parseInt(new String((rs.getString(1).trim())));
            }

            tarList[0] = "UID";
            tableList[0] = "UColumn";
            conList[0] = "ColumnName='" + UColumn + "'";
            conList[1] = "UTableID=" + UTID;

            Select(1, 1, 2);
            while(rs.next()) {
                UCID = Integer.parseInt(new String((rs.getString(1).trim())));
            }

            tarList[0] = "*";
            tableList[0] = "ColumnMapping";
            conList = new String[2];
            conList[0] = "UCID=" + UCID;
            conList[1] = "Location is not null";
            Select(1, 1, 2);
            if (rs.next())
                conType = 0;
            else
                conType = 1;

            int conNum = conType + 2;
            tarList[0] = "LCID";
            tableList[0] = "ColumnMapping";
            conList = new String[conNum];
            conList[0] = "UCID=" + UCID;
            switch (conType) {
                case 0:
                    if (conMax != Integer.MAX_VALUE)
                        conList[1] = "Location=" + conMax;
                    else
                        conList[1] = "Location is not null";
                    break;
                case 1:
                    conList[1] = "Max>=" + conMin;
                    conList[2] = "Min<=" + conMax;
                    break;
                default: break;
            }
            Select(1, 1, conNum);
            LColumnNum = 0;
            while(rs.next()) {
                LColumnNum++;
            }
            rs.beforeFirst();
            LColumnIDList = new int[LColumnNum];
            count = 0;
            while (rs.next()) {
                LColumnIDList[count++] = Integer.parseInt(new String((rs.getString(1).trim())));
            }

            LColumnList = new String[LColumnNum];
            LTableList = new String[LColumnNum];
            LDataBaseList = new String[LColumnNum];
            LDataBaseUserList = new String[LColumnNum];
            LDataBasePwdList = new String[LColumnNum];
            LDataSourceTypeList = new String[LColumnNum];
            LDataSourceURLList = new String[LColumnNum];
            int tableID = -1;
            int databaseID = -1;
            int dataSourceID = -1;
            int typeID = -1;
            String DBName = "";
            String DSIP = "";
            String DSPort = "";
            for (int i = 0; i < LColumnNum; i++) {
                tarList = new String[2];
                tarList[0] = "TableID";
                tarList[1] = "ColumnName";
                tableList[0] = "LColumn";
                conList = new String[1];
                conList[0] = "UID=" + LColumnIDList[i];
                Select(2, 1, 1);
                while(rs.next()) {
                    tableID = Integer.parseInt(new String((rs.getString(1).trim())));
                    LColumnList[i] = new String((rs.getString(2).trim()));
                }

                tarList[0] = "DBID";
                tarList[1] = "TableName";
                tableList[0] = "LTable";
                conList[0] = "UID=" + tableID;
                Select(2, 1, 1);
                while(rs.next()) {
                    databaseID = Integer.parseInt(new String((rs.getString(1).trim())));
                    LTableList[i] = new String((rs.getString(2).trim()));
                }

                tarList = new String[4];
                tarList[0] = "DSID";
                tarList[1] = "DBName";
                tarList[2] = "UserName";
                tarList[3] = "Password";
                tableList[0] = "LDataBase";
                conList[0] = "UID=" + databaseID;
                Select(4, 1, 1);
                while(rs.next()) {
                    dataSourceID = Integer.parseInt(new String((rs.getString(1).trim())));
                    LDataBaseList[i] = new String((rs.getString(2).trim()));
                    LDataBaseUserList[i] = new String((rs.getString(3).trim()));
                    LDataBasePwdList[i] = new String((rs.getString(4).trim()));
                }

                tarList = new String[3];
                tarList[0] = "TypeID";
                tarList[1] = "IP";
                tarList[2] = "Port";
                tableList[0] = "LDataSource";
                conList[0] = "UID=" + dataSourceID;
                Select(3, 1, 1);
                while(rs.next()) {
                    typeID = Integer.parseInt(new String((rs.getString(1).trim())));
                    DSIP = new String((rs.getString(2).trim()));
                    DSPort = new String((rs.getString(3).trim()));
                }

                tarList = new String[1];
                tarList[0] = "Type";
                tableList[0] = "LDataSourceType";
                conList[0] = "UID=" + typeID;
                Select(1, 1, 1);
                while(rs.next()) {
                    LDataSourceTypeList[i] = new String((rs.getString(1).trim()));
                }

                LDataSourceURLList[i] = "jdbc:" + LDataSourceTypeList[i] + ":" + DSIP;
                if (!DSPort.equals("NULL"))
                    LDataSourceURLList[i] += ":" + DSPort;
                LDataSourceURLList[i] += "/" + LDataBaseList[i];
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMapping(String LDSType, String LDSIP, String LDSName, String LDSPort, String LDBName, String LDBUserName, String LDBPassword, String LTableName, String LColumnName, String LColumnType, int UCID, int conMax, int conMin, int conType) {
        valList = new String[5];

        int DSID = -1;
        int DBID = -1;
        int TableID = -1;
        int LCID = -1;

        try {
            tarList = new String[1];
            tarList[0] = "UID";
            tableList = new String[1];
            tableList[0] = "LDataSource";
            conList = new String[1];
            conList[0] = "Name='" + LDSName + "'";
            Select(1, 1, 1);
            if (!rs.next()) {
                DSID = addLDataSource(LDSType, LDSIP, LDSName, LDSPort);
            }
            else {
                rs.beforeFirst();
                while (rs.next()) {
                    DSID = Integer.parseInt(new String((rs.getString(1).trim())));
                }
            }

            tableList[0] = "LDataBase";
            conList = new String[2];
            conList[0] = "DSID=" + DSID;
            conList[1] = "DBName='" + LDBName + "'";
            Select(1, 1, 2);
            if (!rs.next()) {
                DBID = addLDataBase(DSID, LDBName, LDBUserName, LDBPassword);
            }
            else {
                rs.beforeFirst();
                while (rs.next()) {
                    DBID = Integer.parseInt(new String((rs.getString(1).trim())));
                }
            }

            tableList[0] = "LTable";
            conList[0] = "DBID=" + DBID;
            conList[1] = "TableName='" + LTableName + "'";
            Select(1, 1, 2);
            if (!rs.next()) {
                TableID = addLTable(DBID, LTableName);
            }
            else {
                rs.beforeFirst();
                while (rs.next()) {
                    TableID = Integer.parseInt(new String((rs.getString(1).trim())));
                }
            }

            tableList[0] = "LColumn";
            conList[0] = "TableID=" + TableID;
            conList[1] = "ColumnName='" + LColumnName + "'";
            Select(1, 1, 2);
            if (!rs.next()) {
                LCID = addLColumn(TableID, LColumnName, LColumnType);
            }
            else {
                rs.beforeFirst();
                while (rs.next()) {
                    LCID = Integer.parseInt(new String((rs.getString(1).trim())));
                }
            }

            valList[0] = UCID + "";
            valList[1] = LCID + "";
            switch (conType) {
                case 0:
                    valList[2] = valList[3] = "null";
                    valList[4] = conMax + "";
                    break;
                case 1:
                    valList[2] = conMax + "";
                    valList[3] = conMin + "";
                    valList[4] = "null";
                    break;
                default: break;
            }
            Insert("ColumnMapping", 5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[][] showDataSource() {
        int rowNum = 0;

        try {
            tarList = new String[5];
            tarList[0] = "LDataSource.UID";
            tarList[1] = "LDataSource.Name";
            tarList[2] = "LDataSourceType.Type";
            tarList[3] = "LDataSource.IP";
            tarList[4] = "LDataSource.Port";
            tableList = new String[2];
            tableList[0] = "LDataSource";
            tableList[1] = "LDataSourceType";
            conList = new String[1];
            conList[0] = "LDataSource.Type=LDataSourceType.UID";
            Select(5, 2, 1);
            while (rs.next()) {
                rowNum++;
            }
            rs.beforeFirst();

            showDS = new String[rowNum][];
            int i = 0;
            while (rs.next()){
                showDS[i] = new String[5];
                for (int j = 1; j <= 5; j++ ) {
                    showDS[i][j - 1] = new String((rs.getString(1).trim()));
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showDS;
    }
    public boolean addLDS(String Type, String IP, String Name, String Port) {
        addLDataSource(Type, IP, Name, Port);
        return true;
    }
    public boolean updateDS(String UID, String Type, String IP, String Name, String Port) {
        int TypeID = -1;
        try {
            tarList = new String[1];
            tarList[0] = "UID";
            tableList = new String[1];
            tableList[0] = "LDataSourceType";
            conList = new String[1];
            conList[0] = "Type='" + Type + "'";
            Select(1, 1, 1);
            while(rs.next()) {
                TypeID = Integer.parseInt(new String((rs.getString(1).trim())));
            }

            tarList = new String[4];
            valList = new String[4];
            conList = new String[1];
            tarList[0] = "TypeID";
            tarList[1] = "IP";
            tarList[2] = "Name";
            tarList[3] = "Port";
            valList[0] = TypeID + "";
            valList[1] = IP;
            valList[2] = Name;
            valList[3] = Port;
            conList[0] = "UID=" + UID;
            Update("LDataSourceType", 4, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean deleteDS(int UID) {
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Delete("LDataSource", 1);
        return true;
    }

    public String[][] showUTable() {
        int rowNum = 0;

        try {
            tarList = new String[2];
            tarList[0] = "UID";
            tarList[1] = "TableName";
            tableList = new String[1];
            tableList[0] = "UTable";
            Select(2, 1, 0);
            while (rs.next()) {
                rowNum++;
            }
            rs.beforeFirst();

            showUTable = new String[rowNum][];
            int i = 0;
            while (rs.next()){
                showUTable[i] = new String[2];
                showUTable[i][0] = new String((rs.getString(1).trim()));
                showUTable[i][1] = new String((rs.getString(2).trim()));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showUTable;
    }
    public String[][] showUColumn(int tableID) {
        int rowNum = 0;

        try {
            tarList = new String[1];
            tarList[0] = "*";
            tableList = new String[1];
            tableList[0] = "UColumn";
            conList = new String[1];
            conList[0] = "UTableID=" + tableID;
            Select(1, 1, 1);
            while (rs.next()) {
                rowNum++;
            }
            rs.beforeFirst();

            showUColumn = new String[rowNum][];
            int i = 0;
            while (rs.next()){
                showUColumn[i] = new String[3];
                showUColumn[i][0] = new String((rs.getString(1).trim()));
                showUColumn[i][1] = new String((rs.getString(2).trim()));
                showUColumn[i][2] = new String((rs.getString(3).trim()));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showUColumn;
    }
    public String[][] showLDS() {
        int rowNum = 0;

        try {
            tarList = new String[2];
            tarList[0] = "UID";
            tarList[1] = "Name";
            tableList = new String[1];
            tableList[0] = "ldatasource";
            Select(2, 1, 0);
            while (rs.next()) {
                rowNum++;
            }
            rs.beforeFirst();

            showLDS = new String[rowNum][];
            int i = 0;
            while (rs.next()){
                showLDS[i] = new String[2];
                showLDS[i][0] = new String((rs.getString(1).trim()));
                showLDS[i][1] = new String((rs.getString(1).trim()));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showLDS;
    }
    public String[][] showLDB(int DSID) {
        int rowNum = 0;

        try {
            tarList = new String[2];
            tarList[0] = "UID";
            tarList[1] = "DBName";
            tableList = new String[1];
            tableList[0] = "LDataBase";
            conList = new String[1];
            conList[0] = "DSID=" + DSID;
            Select(2, 1, 1);
            while (rs.next()) {
                rowNum++;
            }
            rs.beforeFirst();

            showLDB = new String[rowNum][];
            int i = 0;
            while (rs.next()){
                showLDB[i] = new String[3];
                showLDB[i][0] = new String((rs.getString(1).trim()));
                showLDB[i][1] = new String((rs.getString(2).trim()));
                showLDB[i][2] = "" + DSID;
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showLDB;
    }
    public String[][] showLTable(int DBID) {
        int rowNum = 0;

        try {
            tarList = new String[2];
            tarList[0] = "UID";
            tarList[1] = "TableName";
            tableList = new String[1];
            tableList[0] = "LTable";
            conList = new String[1];
            conList[0] = "DBID=" + DBID;
            Select(2, 1, 1);
            while (rs.next()) {
                rowNum++;
            }
            rs.beforeFirst();

            showLTable = new String[rowNum][];
            int i = 0;
            while (rs.next()){
                showLTable[i] = new String[3];
                showLTable[i][0] = new String((rs.getString(1).trim()));
                showLTable[i][1] = new String((rs.getString(2).trim()));
                showLTable[i][2] = "" + DBID;
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showLTable;
    }
    public String[][] showLColumn(int TableID) {
        int rowNum = 0;
        int typeNum = 0;
        String typeName[];

        try {
            tarList = new String[1];
            tarList[0] = "LColumnType";
            tableList = new String[1];
            tableList[0] = "LColumnType";
            Select(1, 1, 0);
            while (rs.next()) {
                typeNum++;
            }
            rs.beforeFirst();
            typeName = new String[typeNum];
            int i = 0;
            while (rs.next()){
                typeName[i] = new String((rs.getString(1).trim()));
                i++;
            }

            tarList = new String[3];
            tarList[0] = "UID";
            tarList[1] = "ColumnName";
            tarList[2] = "ColumnTypeID";
            tableList[0] = "LColumn";
            conList = new String[1];
            conList[0] = "TableID=" + TableID;
            Select(3, 1, 1);
            while (rs.next()) {
                rowNum++;
            }
            rs.beforeFirst();

            showLColumn = new String[rowNum][];
            i = 0;
            while (rs.next()){
                showLColumn[i] = new String[4];
                showLColumn[i][0] = new String((rs.getString(1).trim()));
                showLColumn[i][1] = new String((rs.getString(2).trim()));
                showLColumn[i][2] = typeName[Integer.parseInt(new String((rs.getString(3).trim()))) - 1];
                showLColumn[i][3] = "" + TableID;

                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showLColumn;
    }
    public String[][] showMappingByU(int UCID) {
        int rowNum = 0;

        try {
            tarList = new String[1];
            tarList[0] = "*";
            tableList = new String[1];
            tableList[0] = "ColumnMapping";
            conList = new String[1];
            conList[0] = "UCID=" + UCID;
            Select(1, 1, 1);
            while (rs.next()) {
                rowNum++;
            }
            rs.beforeFirst();

            showMappingU = new String[rowNum][];
            int i = 0;
            while (rs.next()){
                showMappingU[i] = new String[6];
                for (int j = 1; j <= 6; j++) {
                    if (rs.getString(j) != null)
                        showMappingU[i][j - 1] = new String((rs.getString(j).trim()));
                    else
                        showMappingU[i][j - 1] = "";
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showMappingU;
    }
    public String[][] showMappingByL(int LCID) {
        try {
            tarList = new String[1];
            tarList[0] = "*";
            tableList = new String[1];
            tableList[0] = "ColumnMapping";
            conList = new String[1];
            conList[0] = "LCID=" + LCID;
            Select(1, 1, 1);

            showMappingL = new String[1][];
            showMappingL[0] = new String[6];
            while (rs.next()){
                for (int i = 1; i <= 6; i++) {
                    if (rs.getString(i) != null)
                        showMappingL[0][i - 1] = new String((rs.getString(i).trim()));
                    else
                        showMappingL[0][i - 1] = "";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showMappingL;
    }

    public void showUTree() {
        int UTNum = 0;
        int UTID[];
        int UCNum = 0;

        try {
            tarList = new String[2];
            tarList[0] = "UID";
            tarList[1] = "TableName";
            tableList = new String[1];
            tableList[0] = "UTable";
            Select(2, 1, 0);
            while (rs.next()) {
                UTNum++;
            }
            rs.beforeFirst();

            UT = new String[UTNum];
            UC = new String[UTNum][];
            UTID = new int[UTNum];
            int i = 0;
            while (rs.next()){
                UTID[i] = Integer.parseInt(new String((rs.getString(1).trim())));
                UT[i] = new String((rs.getString(2).trim()));
                i++;
            }

            for (i = 0; i < UTNum; i++) {
                UCNum = 0;
                tarList = new String[1];
                tarList[0] = "ColumnName";
                tableList[0] = "UColumn";
                conList = new String[1];
                conList[0] = "UTableID=" + UTID[i];
                Select(1, 1, 1);
                while (rs.next()) {
                    UCNum++;
                }
                rs.beforeFirst();

                UC[i] = new String[UCNum];
                int j = 0;
                while (rs.next()){
                    UC[i][j] = new String((rs.getString(1).trim()));
                    j++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showLTree() {
        int LSNum = 0;
        int LBNum = 0;
        int LBID[][];
        int LTNum = 0;
        int LTID[][][];
        int LCNum = 0;

        try {
            LSNum = 0;
            tarList = new String[2];
            tarList[0] = "UID";
            tarList[1] = "Name";
            tableList = new String[1];
            tableList[0] = "LDataSource";
            Select(2, 1, 0);
            while (rs.next()) {
                LSNum++;
            }
            rs.beforeFirst();

            LS = new String[LSNum];
            LB = new String[LSNum][];
            LT = new String[LSNum][][];
            LC = new String[LSNum][][][];
            LSID = new String[LSNum];
            LBID = new int[LSNum][];
            LTID = new int[LSNum][][];
            int i = 0;
            while (rs.next()){
                LSID[i] = new String((rs.getString(1).trim()));
                LS[i] = new String((rs.getString(2).trim()));
                i++;
            }

            for (i = 0; i < LSNum; i++) {
                LBNum = 0;
                tarList = new String[2];
                tarList[0] = "UID";
                tarList[1] = "DBName";
                tableList = new String[1];
                tableList[0] = "LDataBase";
                conList = new String[1];
                conList[0] = "DSID=" + LSID[i];
                Select(2, 1, 1);
                while (rs.next()) {
                    LBNum++;
                }
                rs.beforeFirst();

                LB[i] = new String[LBNum];
                LT[i] = new String[LBNum][];
                LC[i] = new String[LBNum][][];
                LBID[i] = new int[LBNum];
                LTID[i] = new int[LBNum][];
                int j = 0;
                while (rs.next()){
                    LBID[i][j] = Integer.parseInt(new String((rs.getString(1).trim())));
                    LB[i][j] = new String((rs.getString(2).trim()));
                    j++;
                }

                for (j = 0; j < LBNum; j++) {
                    LTNum = 0;
                    tarList = new String[2];
                    tarList[0] = "UID";
                    tarList[1] = "TableName";
                    tableList = new String[1];
                    tableList[0] = "LTable";
                    conList = new String[1];
                    conList[0] = "DBID=" + LBID[i][j];
                    Select(2, 1, 1);
                    while (rs.next()) {
                        LTNum++;
                    }
                    rs.beforeFirst();

                    LT[i][j] = new String[LTNum];
                    LC[i][j] = new String[LTNum][];
                    LTID[i][j] = new int[LTNum];
                    int k = 0;
                    while (rs.next()) {
                        LTID[i][j][k] = Integer.parseInt(new String((rs.getString(1).trim())));
                        LT[i][j][k] = new String((rs.getString(2).trim()));
                        k++;
                    }

                    for (k = 0; k < LTNum; k++) {
                        LCNum = 0;
                        tarList = new String[1];
                        tarList[0] = "ColumnName";
                        tableList = new String[1];
                        tableList[0] = "LColumn";
                        conList = new String[1];
                        conList[0] = "TableID=" + LTID[i][j][k];
                        Select(1, 1, 1);
                        while (rs.next()) {
                            LCNum++;
                        }
                        rs.beforeFirst();

                        LC[i][j][k] = new String[LCNum];
                        int l = 0;
                        while (rs.next()) {
                            LC[i][j][k][l] = new String((rs.getString(1).trim()));
                            l++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showNoMapUC(int LCID) {
        int num = 0;
        try {
            tarList = new String[1];
            tarList[0] = "UID";
            tableList = new String[1];
            tableList[0] = "UColumn";
            conList = new String[1];
            conList[0] = "Where UID not in (Select UCID From columnmapping Where LCID=" + LCID + ")";
            Select(1, 1, 1);
            while (rs.next()) {
                num++;
            }
            rs.beforeFirst();

            int i = 0;
            NoMapUCID = new int[num];
            NoMapUCName = new String[num];
            NoMapUTName = new String[num];
            while (rs.next()) {
                NoMapUCID[i] = Integer.parseInt(new String((rs.getString(1).trim())));
                i++;
            }

            int tableID = -1;
            int databaseID = -1;
            int dataSourceID = -1;
            int typeID = -1;
            for (i = 0; i < num; i++) {
                tarList = new String[2];
                tarList[0] = "UTableID";
                tarList[1] = "ColumnName";
                tableList[0] = "UColumn";
                conList = new String[1];
                conList[0] = "UID=" + NoMapUCID[i];
                Select(2, 1, 1);
                while(rs.next()) {
                    tableID = Integer.parseInt(new String((rs.getString(1).trim())));
                    NoMapUCName[i] = new String((rs.getString(2).trim()));
                }

                tarList = new String[1];
                tarList[0] = "TableName";
                tableList[0] = "UTable";
                conList[0] = "UID=" + tableID;
                Select(1, 1, 1);
                while(rs.next()) {
                    NoMapUTName[i] = new String((rs.getString(2).trim()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showNoMapLC(int UCID) {
        int num = 0;
        try {
            tarList = new String[1];
            tarList[0] = "UID";
            tableList = new String[1];
            tableList[0] = "LColumn";
            conList = new String[1];
            conList[0] = "UID not in (Select LCID From columnmapping Where UCID=" + UCID + ")";
            Select(1, 1, 1);
            while (rs.next()) {
                num++;
            }
            rs.beforeFirst();

            int i = 0;
            NoMapLCID = new int[num];
            NoMapLCName = new String[num];
            NoMapLTName = new String[num];
            NoMapLBName = new String[num];
            NoMapLSName = new String[num];
            while (rs.next()) {
                NoMapLCID[i] = Integer.parseInt(new String((rs.getString(1).trim())));
                i++;
            }

            int tableID = -1;
            int databaseID = -1;
            int dataSourceID = -1;
            int typeID = -1;
            for (i = 0; i < num; i++) {
                tarList = new String[2];
                tarList[0] = "TableID";
                tarList[1] = "ColumnName";
                tableList[0] = "LColumn";
                conList = new String[1];
                conList[0] = "UID=" + NoMapLCID[i];
                Select(2, 1, 1);
                while(rs.next()) {
                    tableID = Integer.parseInt(new String((rs.getString(1).trim())));
                    NoMapLCName[i] = new String((rs.getString(2).trim()));
                }

                tarList[0] = "DBID";
                tarList[1] = "TableName";
                tableList[0] = "LTable";
                conList[0] = "UID=" + tableID;
                Select(2, 1, 1);
                while(rs.next()) {
                    databaseID = Integer.parseInt(new String((rs.getString(1).trim())));
                    NoMapLTName[i] = new String((rs.getString(2).trim()));
                }

                tarList[0] = "DSID";
                tarList[1] = "DBName";
                tableList[0] = "LDataBase";
                conList[0] = "UID=" + databaseID;
                Select(2, 1, 1);
                while(rs.next()) {
                    dataSourceID = Integer.parseInt(new String((rs.getString(1).trim())));
                    NoMapLBName[i] = new String((rs.getString(2).trim()));
                }

                tarList = new String[1];
                tarList[0] = "Name";
                tableList[0] = "LDataSource";
                conList[0] = "UID=" + dataSourceID;
                Select(1, 1, 1);
                while(rs.next()) {
                    NoMapLSName[i] = new String((rs.getString(1).trim()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUT(String TableName, String DBID) throws Exception {
        addUTable(TableName, Integer.parseInt(DBID));
        return true;
    }
    public boolean addUC(String ColumnName, String TID) {
        addUColumn(ColumnName, Integer.parseInt(TID));
        return true;
    }
    public boolean addLDB(String DSID, String DBName, String UserName, String Password) {
        addLDataBase(Integer.parseInt(DSID), DBName, UserName, Password);
        return true;
    }
    public boolean addLT(String DBID, String TableName) {
        addLTable(Integer.parseInt(DBID), TableName);
        return true;
    }
    public boolean addLC(String TableID, String LColumn, String LColumnType) {
        addLColumn(Integer.parseInt(TableID), LColumn, LColumnType);
        return true;
    }
    public boolean addMap(int UCID, int LCID, int Max, int Min, int Location) {
        valList = new String[5];
        valList[0] = UCID + "";
        valList[1] = LCID + "";
        valList[2] = Max + "";
        valList[3] = Min + "";
        valList[4] = Location + "";
        Insert("ColumnMapping", 5);
        return true;
    }

    public boolean updateUTable_Name(int UID, String UTable_New) {
        tarList = new String[1];
        tarList[0] = "TableName";
        valList = new String[1];
        valList[0] = "'" + UTable_New + "'";
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Update("UTable", 1, 1);
        return true;
    }
    public boolean updateUColumn_Name(int UID, String UColumn_New) {
        tarList = new String[1];
        tarList[0] = "ColumnName";
        valList = new String[1];
        valList[0] = "'" + UColumn_New + "'";
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Update("UColumn", 1, 1);
        return true;
    }
    public boolean updateLDB_Name(int UID, String LDB_New) {
        tarList = new String[1];
        tarList[0] = "DBName";
        valList = new String[1];
        valList[0] = "'" + LDB_New + "'";
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Update("LDataBase", 1, 1);
        return true;
    }
    public boolean updateLTable_Name(int UID, String LTable_New) {
        tarList = new String[1];
        tarList[0] = "TableName";
        valList = new String[1];
        valList[0] = "'" + LTable_New + "'";
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Update("LTable", 1, 1);
        return true;
    }
    public boolean updateLColumn_Name(int UID, String LColumn_New) {
        tarList = new String[1];
        tarList[0] = "ColumnName";
        valList = new String[1];
        valList[0] = "'" + LColumn_New + "'";
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Update("LColumn", 1, 1);
        return true;
    }
    public boolean updateMap(int UID, int Max, int Min, int Location) {
        tarList = new String[3];
        tarList[0] = "Max";
        tarList[1] = "Min";
        tarList[2] = "Location";
        valList = new String[3];
        valList[0] = Max + "";
        valList[1] = Min + "";
        valList[2] = Location + "";
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Update("ColumnMapping", 3, 1);
        return true;
    }

    public boolean deleteUTable(int UID) {
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Delete("UTable", 1);
        return true;
    }
    public boolean deleteUColumn(int UID) {
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Delete("UColumn", 1);
        return true;
    }
    public boolean deleteLDB(int UID) {
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Delete("LDataBase", 1);
        return true;
    }
    public boolean deleteLTable(int UID) {
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Delete("LTable", 1);
        return true;
    }
    public boolean deleteLColumn(int UID) {
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Delete("LColumn", 1);
        return true;
    }
    public boolean deleteMap(int UID) {
        conList = new String[1];
        conList[0] = "UID=" + UID;
        Delete("ColumnMapping", 1);
        return true;
    }

    public String[] FetchUT() { return UT;}
    public String[][] FetchUC() { return UC;}
    public String[] FetchLSID() { return LSID;}
    public String[] FetchLS() { return LS;}
    public String[][] FetchLB() { return LB;}
    public String[][][] FetchLT() { return LT;}
    public String[][][][] FetchLC() { return LC;}
    public int[] FetchNoMapUCID() { return NoMapUCID;}
    public int[] FetchNoMapLCID() { return NoMapLCID;}
    public String[] FetchNoMapUCName() { return NoMapUCName;}
    public String[] FetchNoMapUTName() { return NoMapUTName;}
    public String[] FetchNoMapLCName() { return NoMapLCName;}
    public String[] FetchNoMapLTName() { return NoMapLTName;}
    public String[] FetchNoMapLBName() { return NoMapLBName;}
    public String[] FetchNoMapLSName() { return NoMapLSName;}
    public String[] FetchLDataSourceURLList() { return LDataSourceURLList;}
    public String[] FetchLDataSourceTypeList() { return LDataSourceTypeList;}
    public String[] FetchLDataBaseList() { return LDataBaseList;}
    public String[] FetchLDataBaseUserList() { return LDataBaseUserList;}
    public String[] FetchLDataBasePwdList() { return LDataBasePwdList;}
    public String[] FetchLTableList() { return LTableList;}
    public String[] FetchLColumnList() { return LColumnList;}
}
