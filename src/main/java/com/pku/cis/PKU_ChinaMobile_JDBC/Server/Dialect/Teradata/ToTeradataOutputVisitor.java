package com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Teradata;

/**
 * Created by linyy on 2015/1/4.
 */

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.pku.cis.PKU_ChinaMobile.DataSource.DataSourceType;
import com.pku.cis.PKU_ChinaMobile.DataSource.Function;
import com.pku.cis.PKU_ChinaMobile.DataSource.FunctionMapping;
import com.pku.cis.PKU_ChinaMobile.DataSource.FunctionType;

import java.util.ArrayList;
import java.util.List;

public class ToTeradataOutputVisitor extends OracleOutputVisitor {

    public ToTeradataOutputVisitor(Appendable appender, boolean printPostSemi){
        super(appender, printPostSemi);
    }

    public ToTeradataOutputVisitor(Appendable appender){
        super(appender);
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
            this.print("UNIQUE ");
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
            this.print("WHERE ");
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

    static boolean isRowNumber(SQLExpr expr) {
        if (expr instanceof SQLIdentifierExpr) {
            String lownerName = ((SQLIdentifierExpr) expr).getLowerName();
            return "rownum".equals(lownerName);
        }

        return false;
    }

    @Override
    public boolean visit(SQLMethodInvokeExpr x) {
        if (x.getOwner() != null) {
            x.getOwner().accept(this);
            print(".");
        }

        Function fun = FunctionMapping.getFuncByOracleFuncName(x.getMethodName(), DataSourceType.TD);
        String name = "NULL";
        if(fun == null)
            return false;

        name = fun.name;
         System.out.println("name = [" +x.getMethodName() + " " + name);
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
        Function fun = FunctionMapping.getFuncByOracleFuncName(x.getMethodName(), DataSourceType.TD);
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
    public boolean visit(SQLUnionQuery x) {
        x.getLeft().accept(this);
        println();
        if(x.getOperator().name.toUpperCase()=="INTERSECT"){
            print("EXCEPT");
        }
        else {
            print(x.getOperator().name);
        }
        println();

        boolean needParen = false;

        if (x.getOrderBy() != null) {
            needParen = true;
        }

        if (needParen) {
            print('(');
            x.getRight().accept(this);
            print(')');
        } else {
            x.getRight().accept(this);
        }

        if (x.getOrderBy() != null) {
            println();
            x.getOrderBy().accept(this);
        }

        return false;
    }

    @Override
    public boolean visit(SQLWithSubqueryClause x) {
        print("CREATE VIEW");
        if (x.getRecursive() == Boolean.TRUE) {
            print(" RECURSIVE");
        }
        incrementIndent();
        println();
        printlnAndAccept(x.getEntries(), ", ");
        decrementIndent();
        return false;
    }
}
