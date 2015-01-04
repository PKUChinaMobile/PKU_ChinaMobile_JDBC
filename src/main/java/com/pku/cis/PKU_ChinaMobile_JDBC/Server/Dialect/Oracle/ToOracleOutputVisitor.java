package com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Oracle;

/**
 * Created by linyy on 2015/1/4.
 */

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;

import java.util.ArrayList;
import java.util.List;

public class ToOracleOutputVisitor extends OracleOutputVisitor{
    public ToOracleOutputVisitor(Appendable appender, boolean printPostSemi){
        super(appender, printPostSemi);
    }

    public ToOracleOutputVisitor(Appendable appender){
        super(appender);
    }
}
