package com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Teradata;

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

    public boolean visit(OracleSelectQueryBlock x) {
        boolean parentIsSelectStatment = false;
        {
            if (x.getParent() instanceof SQLSelect) {
                SQLSelect select = (SQLSelect) x.getParent();
                if (select.getParent() instanceof SQLSelectStatement || select.getParent() instanceof  SQLSubqueryTableSource) {
                    parentIsSelectStatment = true;
                }
            }
        }

        if (!parentIsSelectStatment) {
            return super.visit(x);
        }

        if (x.getWhere() instanceof SQLBinaryOpExpr //
                && x.getFrom() instanceof SQLSubqueryTableSource //
                ) {
            int rownum;
            String ident;
            SQLBinaryOpExpr where = (SQLBinaryOpExpr) x.getWhere();
            if (where.getRight() instanceof SQLIntegerExpr && where.getLeft() instanceof SQLIdentifierExpr) {
                rownum = ((SQLIntegerExpr) where.getRight()).getNumber().intValue();
                ident = ((SQLIdentifierExpr) where.getLeft()).getName();
            } else {
                return super.visit(x);
            }

            SQLSelect select = ((SQLSubqueryTableSource) x.getFrom()).getSelect();
            SQLSelectQueryBlock queryBlock = null;
            SQLSelect subSelect = null;
            SQLBinaryOpExpr subWhere = null;
            boolean isSubQueryRowNumMapping = false;

            if (select.getQuery() instanceof SQLSelectQueryBlock) {
                queryBlock = (SQLSelectQueryBlock) select.getQuery();
                if (queryBlock.getWhere() instanceof SQLBinaryOpExpr) {
                    subWhere = (SQLBinaryOpExpr) queryBlock.getWhere();
                }

                for (SQLSelectItem selectItem : queryBlock.getSelectList()) {
                    if (isRowNumber(selectItem.getExpr())) {
                        if (where.getLeft() instanceof SQLIdentifierExpr
                                && ((SQLIdentifierExpr) where.getLeft()).getName().equals(selectItem.getAlias())) {
                            isSubQueryRowNumMapping = true;
                        }
                    }
                }

                SQLTableSource subTableSource = queryBlock.getFrom();
                if (subTableSource instanceof SQLSubqueryTableSource) {
                    subSelect = ((SQLSubqueryTableSource) subTableSource).getSelect();
                }
            }

            if ("ROWNUM".equalsIgnoreCase(ident)) {
                SQLBinaryOperator op = where.getOperator();
                Integer limit = null;
                if (op == SQLBinaryOperator.LessThanOrEqual) {
                    limit = rownum;
                } else if (op == SQLBinaryOperator.LessThan) {
                    limit = rownum - 1;
                }

                if (limit != null) {
                    select.accept(this);
                    println();
                    print("LIMIT ");
                    print(limit);
                    return false;
                }
            } else if (isSubQueryRowNumMapping) {
                SQLBinaryOperator op = where.getOperator();
                SQLBinaryOperator subOp = subWhere.getOperator();

                if (isRowNumber(subWhere.getLeft()) //
                        && subWhere.getRight() instanceof SQLIntegerExpr) {

                    int subRownum = ((SQLIntegerExpr) subWhere.getRight()).getNumber().intValue();

                    Integer offset = null;
                    if (op == SQLBinaryOperator.GreaterThanOrEqual) {
                        offset = rownum + 1;
                    } else if (op == SQLBinaryOperator.GreaterThan) {
                        offset = rownum;
                    }

                    if (offset != null) {
                        Integer limit = null;
                        if (subOp == SQLBinaryOperator.LessThanOrEqual) {
                            limit = subRownum - offset;
                        } else if (subOp == SQLBinaryOperator.LessThan) {
                            limit = subRownum - 1 - offset;
                        }

                        if (limit != null) {
                            subSelect.accept(this);
                            println();
                            print("LIMIT ");
                            print(offset);
                            print(", ");
                            print(limit);
                            return false;
                        }
                    }
                }
            }
        }
        return super.visit(x);
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
}
