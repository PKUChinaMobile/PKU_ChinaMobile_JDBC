package com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Hive;

/**
 * Created by linyy on 2015/1/4.
 */

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleOrderBy;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.*;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.pku.cis.PKU_ChinaMobile.DataSource.DataSourceType;
import com.pku.cis.PKU_ChinaMobile.DataSource.Function;
import com.pku.cis.PKU_ChinaMobile.DataSource.FunctionMapping;
import com.pku.cis.PKU_ChinaMobile.DataSource.FunctionType;


import java.util.ArrayList;
import java.util.List;

public class ToHiveOutputVisitor extends OracleOutputVisitor {

    public ToHiveOutputVisitor(Appendable appender, boolean printPostSemi){
        super(appender, printPostSemi);
    }

    public ToHiveOutputVisitor(Appendable appender){
        super(appender);
    }


    @Override
    public boolean visit(SQLSelect x) {
        System.out.println("来过");
        return x instanceof OracleSelect?this.visit((OracleSelect)((OracleSelect)x)):super.visit(x);
    }

    @Override
    public boolean visit(OracleSelectQueryBlock x) {
        this.print("SELECT ");
        if(x.getHints().size() > 0) {
            this.printAndAccept(x.getHints(), ", ");
            this.print(' ');
        }

        if(1 == x.getDistionOption()) {
            this.print("ALL ");
        } else if(2 == x.getDistionOption()) {
            this.print("DISTINCT ");
        } else if(3 == x.getDistionOption()) {
            this.print("UNIQUE ");
        }

        this.printSelectList(x.getSelectList());
        if(x.getInto() != null) {
            this.println();
            this.print("INTO ");
            x.getInto().accept(this);
        }

        this.println();
        this.print("FROM ");
        if(x.getFrom() == null) {
            this.print("DUAL");
        } else {
            x.getFrom().setParent(x);
            x.getFrom().accept(this);
        }

        if(x.getWhere() != null) {
            this.println();
            if (!(x.getWhere() instanceof SQLInSubQueryExpr)) {
                this.print("WHERE ");
            }
            x.getWhere().setParent(x);
            x.getWhere().accept(this);
        }

        if(x.getHierachicalQueryClause() != null) {
            this.println();
            x.getHierachicalQueryClause().accept(this);
        }

        if(x.getGroupBy() != null) {
            this.println();
            x.getGroupBy().accept(this);
        }

        if(x.getModelClause() != null) {
            this.println();
            x.getModelClause().accept(this);
        }

        return false;
    }

    @Override
    public boolean visit(SQLMethodInvokeExpr x) {
        if (x.getOwner() != null) {
            x.getOwner().accept(this);
            print(".");
        }

        Function fun = FunctionMapping.getFuncByOracleFuncName(x.getMethodName(), DataSourceType.Hive);
        String name = "NULL";
        if(fun == null)
            return false;

        name = fun.name;
       // System.out.println("name = [" +x.getMethodName() + " " + name);
        if(fun.funcType == FunctionType.function) {
            print(name);
            print("(");
            printAndAccept(x.getParameters(), ", ");
            print(")");
        } else {
            //print("(");
           // System.out.println("[[[[[" + name);
            if(x.getParameters().size() > 0)
                print(x.getParameters().get(0).toString());
            print(name);
            //printAndAccept(x.getParameters(), ", ");
            if(x.getParameters().size() > 1)
                print(x.getParameters().get(1).toString());
            //print(")");
        }

        return false;
        //return super.visit(x);
    }

    @Override
    public boolean visit(SQLAggregateExpr x) {
        Function fun = FunctionMapping.getFuncByOracleFuncName(x.getMethodName(), DataSourceType.Hive);
        String name = "NULL";
        if(fun != null)
            name = fun.name;

       // System.out.println("aggregate=" + name);
        print(name);
        //print(x.getMethodName());
        print("(");

        if (x.getOption() != null) {
            print(x.getOption().toString());
            print(' ');
        }

        printAndAccept(x.getArguments(), ", ");

        visitAggreateRest(x);

        print(")");

        if (x.getWithinGroup() != null) {
            print(" WITHIN GROUP (");
            x.getWithinGroup().accept(this);
            print(")");
        }

        if (x.getOver() != null) {
            print(" ");
            x.getOver().accept(this);
        }
        return false;
    }


    @Override
    public boolean visit(SQLBinaryOpExpr x) {
        SQLObject parent = x.getParent();
        boolean isRoot = parent instanceof SQLSelectQueryBlock;
        boolean relational = x.getOperator() == SQLBinaryOperator.BooleanAnd
                || x.getOperator() == SQLBinaryOperator.BooleanOr;

        if (isRoot && relational) {
            incrementIndent();
        }

        List<SQLExpr> groupList = new ArrayList<SQLExpr>();
        SQLExpr left = x.getLeft();
        for (;;) {
            if (left instanceof SQLBinaryOpExpr && ((SQLBinaryOpExpr) left).getOperator() == x.getOperator()) {
                SQLBinaryOpExpr binaryLeft = (SQLBinaryOpExpr) left;
                groupList.add(binaryLeft.getRight());
                left = binaryLeft.getLeft();
            } else {
                groupList.add(left);
                break;
            }
        }

        for (int i = groupList.size() - 1; i >= 0; --i) {
            SQLExpr item = groupList.get(i);
            visitBinaryLeft(item, x.getOperator());

            if (relational) {
                println();
            } else {
                print(" ");
            }
            print(x.getOperator().name);
            print(" ");
        }

        visitorBinaryRight(x);

        if (isRoot && relational) {
            decrementIndent();
        }

        return false;
    }

    private void visitorBinaryRight(SQLBinaryOpExpr x) {
        if (x.getRight() instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr right = (SQLBinaryOpExpr) x.getRight();
            boolean rightRational = right.getOperator() == SQLBinaryOperator.BooleanAnd
                    || right.getOperator() == SQLBinaryOperator.BooleanOr;

            if (right.getOperator().priority >= x.getOperator().priority) {
                if (rightRational) {
                    incrementIndent();
                }

                print('(');
                right.accept(this);
                print(')');

                if (rightRational) {
                    decrementIndent();
                }
            } else {
                right.accept(this);
            }
        } else {
            x.getRight().accept(this);
        }
    }

    private void visitBinaryLeft(SQLExpr left, SQLBinaryOperator op) {
        if (left instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr binaryLeft = (SQLBinaryOpExpr) left;
            boolean leftRational = binaryLeft.getOperator() == SQLBinaryOperator.BooleanAnd
                    || binaryLeft.getOperator() == SQLBinaryOperator.BooleanOr;

            if (binaryLeft.getOperator().priority > op.priority) {
                if (leftRational) {
                    incrementIndent();
                }
                print('(');
                left.accept(this);
                print(')');

                if (leftRational) {
                    decrementIndent();
                }
            } else {
                left.accept(this);
            }
        } else {
            left.accept(this);
        }
    }

    @Override
    public boolean visit(OracleOrderBy x) {
        if(x.getItems().size() > 0) {
            this.print("SORT ");
            if(x.isSibings()) {
                this.print("SIBLINGS ");
            }

            this.print("BY ");
            this.printAndAccept(x.getItems(), ", ");
        }

        return false;
    }

    @Override
    public boolean visit(OracleSelectJoin x) {
        x.getLeft().accept(this);
        if(x.getJoinType() == SQLJoinTableSource.JoinType.COMMA) {
            this.print(", ");
            x.getRight().accept(this);
        } else if (x.getJoinType() == SQLJoinTableSource.JoinType.INNER_JOIN){
            boolean isRoot = x.getParent() instanceof SQLSelectQueryBlock;
            if(isRoot) {
                this.incrementIndent();
            }

            this.println();
            this.print(SQLJoinTableSource.JoinType.JOIN.toString());
            this.print(" ");
            x.getRight().accept(this);
            if(isRoot) {
                this.decrementIndent();
            }

            if(x.getCondition() != null) {
                this.print(" ON (");
                x.getCondition().accept(this);
                this.print(") ");
            }

            if(x.getUsing().size() > 0) {
                this.print(" USING (");
                this.printAndAccept(x.getUsing(), ", ");
                this.print(")");
            }

            if(x.getFlashback() != null) {
                this.println();
                x.getFlashback().accept(this);
            }
        }else {
            boolean isRoot = x.getParent() instanceof SQLSelectQueryBlock;
            if (isRoot) {
                incrementIndent();
            }

            println();
            print(SQLJoinTableSource.JoinType.toString(x.getJoinType()));
            print(" ");

            x.getRight().accept(this);

            if (isRoot) {
                decrementIndent();
            }

            if (x.getCondition() != null) {
                print(" ON ");
                x.getCondition().accept(this);
                print(" ");
            }

            if (x.getUsing().size() > 0) {
                print(" USING (");
                printAndAccept(x.getUsing(), ", ");
                print(")");
            }

            if (x.getFlashback() != null) {
                println();
                x.getFlashback().accept(this);
            }
        }

        return false;
    }

    @Override
    public boolean visit(SQLInSubQueryExpr x) {
        this.print(" LEFT OUTER JOIN ");
        OracleSelectQueryBlock oracleSelectQueryBlock = (OracleSelectQueryBlock)x.getSubQuery().getQuery();
        OracleSelectTableReference subTableReference = (OracleSelectTableReference)oracleSelectQueryBlock.getFrom();
        OracleSelectQueryBlock parentOracleSelectQueryBlock = (OracleSelectQueryBlock)x.getParent();
        OracleSelectTableReference parentTableReference = (OracleSelectTableReference)parentOracleSelectQueryBlock.getFrom();
        subTableReference.getExpr().accept(this);
        this.print(" ON ");
        parentTableReference.getExpr().accept(this);
        this.print(".");
        x.getExpr().accept(this);
        this.print("=");
        subTableReference.getExpr().accept(this);
        this.print(".");
        this.printAndAccept( oracleSelectQueryBlock.getSelectList(), ", ");
        this.print(" WHERE ");
        subTableReference.getExpr().accept(this);
        this.print(".");
        this.printAndAccept( oracleSelectQueryBlock.getSelectList(), ", ");

        if(x.isNot()) {
            this.print((String)" IS NULL");
        } else {
            this.print((String)" IS NOT NULL");
        }

        this.incrementIndent();
//        x.getSubQuery().accept(this);
        this.decrementIndent();
//        this.print((String)")");
        return false;
    }

}
