package com.pku.cis.PKU_ChinaMobile_JDBC.examples;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.stat.TableStat;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.SQLParse;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.SQLTranslate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;

/**
 * Created by yyx on 2015/1/6.
 */
public class LocalTest {
    public static void main(String[] args){
        String query = "";

        //case 1
        query = "select * from table where filed1 > 100";
        SQLParse sp = new SQLParse(query);
        sp.modifyTableName("table", "employees");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 2
        query = "select * from table where table.filed1 > 100";
        sp = new SQLParse(query);
        sp.modifyTableName("table", "employees");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 3
        query = "select * from table where 100 > table.filed1";
        sp = new SQLParse(query);
        sp.modifyTableName("table", "employees");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 4
        query = "select * from table t where t.filed1 > 100";
        sp = new SQLParse(query);
        sp.modifyTableName("table", "employees");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 5
        query = "select d.department_name, SUM(d.salary) AS dept_total " +
                "from employees, departments d " +
                "where employees.department_id = d.department_id and employees.level = 'A' and d.salary > 10000 " +
                "group by d.department_name";
        sp = new SQLParse(query);
        sp.modifyTableName("employees", "e");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 6
        query = "select d.department_name " +
                "from employees, d " +
                "where employees.department_id = d.department_id and employees.level = 'A' and d.salary > 10000 ";
        sp = new SQLParse(query);
        sp.modifyTableName("d", "departments");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 7
        query = "select d.department_name, SUM(d.salary) AS dept_total " +
                "from employees, d " +
                "where employees.department_id = d.department_id and employees.level = 'A' and d.salary > 10000 ";
        sp = new SQLParse(query);
        sp.modifyTableName("d", "departments");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 8
        query = "select d.department_name, SUM(d.salary) AS dept_total " +
                "from employees, d " +
                "where employees.department_id = d.department_id and employees.level = 'A' and SUM(d.salary) > 10000 ";
        sp = new SQLParse(query);
        sp.modifyTableName("d", "departments");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 9
        query = "select employees.name " +
                "from employees " +
                "where employees.salary > 1000 " +
                "group by employees.department_id";
        sp = new SQLParse(query);
        sp.modifyTableName("employees", "ee");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 10
        query = "select employees.name " +
                "from employees " +
                "where employees.salary > 1000 " +
                "order by employees.department_id, employees.level";

        sp = new SQLParse(query);
        sp.modifyTableName("employees", "e");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 11
        query = "select d.department_name, SUM(d.salary) AS dept_total " +
                "from employees, departments d " +
                "where employees.department_id = d.department_id and employees.level = 'A' and d.salary > 10000 " +
                "group by d.department_name " +
                "order by d.department_id";
        sp = new SQLParse(query);
        sp.modifyTableName("d", "dept");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        //case 12
        query = "SELECT COUNT(*) " +
                "FROM employees e " +
                "WHERE e.dept_no = 100 " +
                "HAVING COUNT(*) > 10";
        sp = new SQLParse(query);
        sp.modifyTableName("d", "dept");
        System.out.println(SQLUtils.toOracleString(sp.getAST())+"\n");

        List<TableStat.Condition> a1 = sp.getConditions();
        Map<TableStat.Name, TableStat> a2 = sp.getTableName();
        Set<TableStat.Column> a3 = sp.getFields();
        Set<TableStat.Relationship> a4 = sp.getRelationships();
    }
}
