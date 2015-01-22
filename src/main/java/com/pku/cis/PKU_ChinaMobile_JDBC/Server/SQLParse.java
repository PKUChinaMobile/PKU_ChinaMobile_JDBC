/**
 * SQLParse - SQL解析类，和JDBC接口部分只要求构造函数保存query语句，其余接口和SQLTranslate，ConnectionManager沟通
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor.OracleModifyVisitor;

public class SQLParse {

	String query;
    OracleSchemaStatVisitor statVisitor;
    OracleModifyVisitor anotherVisitor;
    StringBuilder AST = new StringBuilder();
    SQLStatement statemen;

	public SQLParse(String _query)
	{
		this.query = _query;
        OracleStatementParser parser = new OracleStatementParser(query);
        List<SQLStatement> statementList = parser.parseStatementList();
        this.statemen = statementList.get(0);

        this.statVisitor = new OracleSchemaStatVisitor();
        statemen.accept(statVisitor);

        OracleOutputVisitor outputVisitor = new OracleOutputVisitor(this.AST);
        statemen.accept(outputVisitor);

        this.anotherVisitor = new OracleModifyVisitor();
	}

	public Map<TableStat.Name, TableStat> getTableName()
	{
        return statVisitor.getTables();
	}

    public Set<TableStat.Column> getFields() {
        return statVisitor.getColumns();
    }

    public Set<TableStat.Relationship> getRelationships() {
        return statVisitor.getRelationships();
    }

    public List<TableStat.Condition> getConditions() {
        return statVisitor.getConditions();
    }

    public List<TableStat.Column> getOrderByColumns() {
        return statVisitor.getOrderByColumns();
    }

    public SQLStatement getAST(){
        return statemen;
    }

    public boolean modifyTableName(String oldName, String newName){
        anotherVisitor.modifyTableName(statemen, oldName.toUpperCase(), newName);

        this.statVisitor = new OracleSchemaStatVisitor();
        statemen.accept(statVisitor);

        return true;
    }
}
