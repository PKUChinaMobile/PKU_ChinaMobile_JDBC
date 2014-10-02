/**
 * PKUResultSetMetadata - This class implements the java.sql.ResultSetMetaData Interface and act as the 
 * ResultSet Object of JDBC to the client. It communicate with the PKUResultSetMetaDataServer on
 * the server by RMI. 
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Client;

import java.sql.*;
import java.rmi.RemoteException;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUResultSetMetaDataInterface;

/**
 * A ResultSetMetaData object can be used to find out about the types 
 * and properties of the columns in a ResultSet.
 * 
 *  @see java.sql.ResultSetMetaData
 */

public class PKUResultSetMetaData
implements java.sql.ResultSetMetaData, java.io.Serializable {
  //Remote ResultSetMetaData
  PKUResultSetMetaDataInterface rmiMetaData_;

  /**
   * Constructor for creating the PKUResultSetMetaData with PKUResultSetMetaDataInterface
   */
  public PKUResultSetMetaData(PKUResultSetMetaDataInterface r) {
    rmiMetaData_ = r;
  }

  /**
   * What's the number of columns in the ResultSet?
   *
   * @return the number
   */
  public int getColumnCount() throws SQLException {
    try {
      return rmiMetaData_.getColumnCount();
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * Is the column automatically numbered, thus read-only?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return true if so
   */
  public boolean isAutoIncrement(int column) throws SQLException {
    try {
      return rmiMetaData_.isAutoIncrement(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * Does a column's case matter?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return true if so
   */
  public boolean isCaseSensitive(int column) throws SQLException {
    try {
      return rmiMetaData_.isCaseSensitive(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }	

  /**
   * Can the column be used in a where clause?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return true if so
   */
  public boolean isSearchable(int column) throws SQLException {
    try {
      return rmiMetaData_.isSearchable(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * Is the column a cash value?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return true if so
   */
  public boolean isCurrency(int column) throws SQLException {
    try {
      return rmiMetaData_.isCurrency(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * Can you put a NULL in this column?		
   *
   * @param column the first column is 1, the second is 2, ...
   * @return columnNoNulls, columnNullable or columnNullableUnknown
   */
  public int isNullable(int column) throws SQLException {
    try {
      return rmiMetaData_.isNullable(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * Is the column a signed number?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return true if so
   */
  public boolean isSigned(int column) throws SQLException {
    try {
      return rmiMetaData_.isSigned(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's the column's normal max width in chars?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return max width
   */
  public int getColumnDisplaySize(int column) throws SQLException {
    try {
      return rmiMetaData_.getColumnDisplaySize(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's the suggested column title for use in printouts and
   * displays?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return true if so 
   */
  public String getColumnLabel(int column) throws SQLException {	
    try {
      return rmiMetaData_.getColumnLabel(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's a column's name?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return column name
   */
  public String getColumnName(int column) throws SQLException {
    try {
      return rmiMetaData_.getColumnName(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's a column's table's schema?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return schema name or "" if not applicable
   */
  public String getSchemaName(int column) throws SQLException {
    try {
      return rmiMetaData_.getSchemaName(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's a column's number of decimal digits?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return precision
   */
  public int getPrecision(int column) throws SQLException {
    try {
      return rmiMetaData_.getPrecision(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's a column's number of digits to right of the decimal point?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return scale
   */
  public int getScale(int column) throws SQLException {	
    try {
      return rmiMetaData_.getScale(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's a column's table name? 
   *
   * @return table name or "" if not applicable
   */
  public String getTableName(int column) throws SQLException {
    try {
      return rmiMetaData_.getTableName(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's a column's table's catalog name?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return column name or "" if not applicable.
   */
  public String getCatalogName(int column) throws SQLException {
    try {
      return rmiMetaData_.getCatalogName(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's a column's SQL type?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return SQL type
   * @see Types
   */
  public int getColumnType(int column) throws SQLException {
    try {
      return rmiMetaData_.getColumnType(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * What's a column's data source specific type name?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return type name
   */
  public String getColumnTypeName(int column) throws SQLException {
    try {
      return rmiMetaData_.getColumnTypeName(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * Is a column definitely not writable?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return true if so
   */
  public boolean isReadOnly(int column) throws SQLException {
    try {
      return rmiMetaData_.isReadOnly(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * Is it possible for a write on the column to succeed?
   *
   * @param column the first column is 1, the second is 2, ...
   * @return true if so
   */
  public boolean isWritable(int column) throws SQLException {
    try {
      return rmiMetaData_.isWritable(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

  /**
   * Will a write on the column definitely succeed?	
   *
   * @param column the first column is 1, the second is 2, ...
   * @return true if so
   */
  public boolean isDefinitelyWritable(int column) throws SQLException {
    try {
      return rmiMetaData_.isDefinitelyWritable(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

// JDBC 2.0 methods

public String getColumnClassName(int column) throws SQLException
  {
    try {
      return rmiMetaData_.getColumnClassName(column);
    } catch(RemoteException e) {
      throw new java.sql.SQLException(e.getMessage());
    }
  }

@Override
public boolean isWrapperFor(Class<?> arg0) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public <T> T unwrap(Class<T> arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

};

