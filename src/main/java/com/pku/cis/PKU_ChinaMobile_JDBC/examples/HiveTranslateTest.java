package com.pku.cis.PKU_ChinaMobile_JDBC.examples;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;

import java.util.List;

/**
 * Created by linyy on 2015/1/4.
 */
import java.util.List;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.pku.cis.PKU_ChinaMobile.DataSource.DataSourceType;
import com.pku.cis.PKU_ChinaMobile.DataSource.Function;
import com.pku.cis.PKU_ChinaMobile.DataSource.FunctionMapping;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Hive.ToHiveOutputVisitor;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Teradata.ToTeradataOutputVisitor;

public class HiveTranslateTest {
    static String translate(String sql){
        // parser得到AST
        System.out.println("\n"+sql + " ->");
        SQLStatementParser parser = new OracleStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList(); //

        // 将AST通过visitor输出
        StringBuilder out = new StringBuilder();
        StringBuilder out2 = new StringBuilder();
        ToHiveOutputVisitor visitor = new ToHiveOutputVisitor(out);
        ToTeradataOutputVisitor visitor2 = new ToTeradataOutputVisitor(out2);

        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor2);
            out2.append(";");
        }

        System.out.println("TD: " + out2.toString());

        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
            out.append(";");
        }
        System.out.print("Hive:");
        return out.toString();
    }
    public static void main(String args[])
    {
        //Function fun = FunctionMapping.getFuncByOracleFuncName("", DataSourceType.Hive);
        //String name = "NULL";
        //String sql = "SELECT * FROM table OFFSET 4 ROWS FETCH NEXT 4 ROWS ONLY";//运行不了
        //String sql = "select * from student s inner join class c on s.classid=c.id";
      //  String sql = "select sin(a, b, c), sum(a) from student s group by a";
      //  System.out.println(translate("SELECT dualTime, vcCallingIMSI, vcCalledIMSI, intLocation FROM callRecords WHERE intLocation = 3 ORDER BY intYear"));
        System.out.println(translate("SELECT count(1) FROM callRecords WHERE intLocation=1 GROUP BY intYear, intMonth, intDay"));
        System.out.println(translate("WITH COUNT_2CALL AS (SELECT count(1) FROM callRecords WHERE intLocation = 2), COUNT_3CALL AS (SELECT\n" +
                "            count(1) FROM callRecords WHERE intLocation = 3), COUNT_4CALL AS (SELECT count(1) FROM\n" +
                "            callRecords WHERE intLocation = 4) SELECT biSessID FROM callRecords where COUNT_2CALL >\n" +
                "        COUNT_3CALL / COUNT_4CALL"));
        System.out.println(translate(" SELECT biSessID FROM callRecords where intLocation = 1 OR intLocation = 2 INTERSECT SELECT biSessID\n" +
                "        FROM callRecords WHERE intLocation = 1"));
        System.out.println(translate(" SELECT biSessID FROM (select * from callRecords) where rownum < 10"));
        System.out.println(translate(" SELECT CONCAT(biSessID, biSessID) FROM callRecords "));
        System.out.println(translate(" SELECT MAX(biSessID) FROM callRecords "));
    }
}
