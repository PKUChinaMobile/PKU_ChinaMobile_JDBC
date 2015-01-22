package com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalDay;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalYear;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeTimestamp;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleOrderBy;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.*;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.*;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.*;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcUtils;
import org.datanucleus.store.rdbms.sql.SQLTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yyx on 2015/1/9.
 */
public class OracleModifyVisitor extends OracleSchemaStatVisitor{

    private Map<String, String> tableNameMapping;

    public OracleModifyVisitor(){
        super();
        tableNameMapping = new HashMap<String, String>();
    }

    @Override
    public String getDbType(){
        return JdbcUtils.ORACLE;
    }

    public void modifyTableName(SQLStatement stmt, String oldName, String newName){
        tableNameMapping.put(oldName, newName);
        accept(stmt);
    }

    @Override
    public boolean visit(OracleSelectTableReference x) {
//        System.out.println("In function visit(OracleSelectTableReference x)");

        SQLExpr expr = x.getExpr();

        if (expr instanceof SQLMethodInvokeExpr) {
            SQLMethodInvokeExpr methodInvoke = (SQLMethodInvokeExpr) expr;
            if ("TABLE".equalsIgnoreCase(methodInvoke.getMethodName()) && methodInvoke.getParameters().size() == 1) {
                expr = methodInvoke.getParameters().get(0);
            }
        }

        String oldName = "";

        if (expr instanceof SQLName) {
            if (expr instanceof SQLPropertyExpr) {
                oldName = ((SQLPropertyExpr) expr).getName().toUpperCase();
                if (tableNameMapping.containsKey(oldName)) {
                    ((SQLPropertyExpr) expr).setName(tableNameMapping.get(oldName));
                }
            } else if (expr instanceof SQLIdentifierExpr) {
                oldName = ((SQLIdentifierExpr) expr).getName().toUpperCase();
                if (tableNameMapping.containsKey(oldName)) {
                    ((SQLIdentifierExpr) expr).setName(tableNameMapping.get(oldName));
                }

                String alias = x.getAlias();
                if(alias != null){
//                    System.out.println(alias);
                    alias = alias.toUpperCase().replaceFirst(oldName, "");
                    if(tableNameMapping.containsKey(alias)){
                        x.setAlias(tableNameMapping.get(alias));
                    }
                }
            }
        }

        accept(x.getExpr());

//        System.out.println("Out function visit(OracleSelectTableReference x)");

        return false;
    }

    @Override
    public boolean visit(SQLBinaryOpExpr x) {
        SQLExpr left = x.getLeft();
        SQLExpr right = x.getRight();

        if(left instanceof SQLPropertyExpr){
            SQLExpr expr = ((SQLPropertyExpr) left).getOwner();
            if(expr instanceof SQLIdentifierExpr){
                String oldName = ((SQLIdentifierExpr) expr).getName().toUpperCase();
                if(tableNameMapping.containsKey(oldName)){
                    ((SQLIdentifierExpr) expr).setName(tableNameMapping.get(oldName));
                }
            }
        }

        if(right instanceof SQLPropertyExpr){
            SQLExpr expr = ((SQLPropertyExpr) right).getOwner();
            if(expr instanceof SQLIdentifierExpr){
                String oldName = ((SQLIdentifierExpr) expr).getName().toUpperCase();
                if(tableNameMapping.containsKey(oldName)){
                    ((SQLIdentifierExpr) expr).setName(tableNameMapping.get(oldName));
                }
            }
        }

        return true;
    }

    @Override
    public boolean visit(SQLAggregateExpr x){
        List<SQLExpr> arguments = x.getArguments();
        for(int i=0; i<arguments.size(); ++i){
            if(arguments.get(i) instanceof SQLPropertyExpr){
                SQLExpr exp = ((SQLPropertyExpr) arguments.get(i)).getOwner();
                if(exp instanceof SQLIdentifierExpr){
                    String oldName = ((SQLIdentifierExpr) exp).getName().toUpperCase();
                    if(tableNameMapping.containsKey(oldName)){
                        ((SQLIdentifierExpr) exp).setName(tableNameMapping.get(oldName));
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean visit(SQLSelectItem x){
//        System.out.println("In visit(SQLSelectItem x)");
        SQLExpr expr = x.getExpr();

        if (expr instanceof SQLPropertyExpr) {
            SQLExpr owner = ((SQLPropertyExpr) expr).getOwner();
            if(owner instanceof SQLIdentifierExpr){
                String oldName = ((SQLIdentifierExpr) owner).getName().toUpperCase();
                if (tableNameMapping.containsKey(oldName)) {
                    ((SQLIdentifierExpr) owner).setName(tableNameMapping.get(oldName));
                }
            }

        } else if (expr instanceof SQLAggregateExpr) {
            accept(expr);
        }

//        System.out.println("Out visit(SQLSelectItem x)");
        return false;
    }

    @Override
    public boolean visit(SQLPropertyExpr x){
        if(x.getParent() instanceof SQLSelectGroupByClause){
            SQLExpr owner = x.getOwner();
            String oldName = ((SQLIdentifierExpr) owner).getName().toUpperCase();
            if (tableNameMapping.containsKey(oldName)) {
                ((SQLIdentifierExpr) owner).setName(tableNameMapping.get(oldName));
            }
        }

        return false;
    }

    @Override
    public boolean visit(OracleOrderByItem x){
        SQLExpr expr = x.getExpr();

        if (expr instanceof SQLPropertyExpr) {
            SQLExpr owner = ((SQLPropertyExpr) expr).getOwner();
            if (owner instanceof SQLIdentifierExpr) {
                String oldName = ((SQLIdentifierExpr) owner).getName().toUpperCase();
                if (tableNameMapping.containsKey(oldName)) {
                    ((SQLIdentifierExpr) owner).setName(tableNameMapping.get(oldName));
                }
            }

        }

        return false;
    }
}
