/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.*;
import java.lang.*;

/**
 * @author dilshan.r
 * @created 4/11/2022 - 11:14 AM
 * @project OracleToPOSJar
 * @ide IntelliJ IDEA
 */

public class OracleConnector {
    private Connection conn;
    private Statement _stmnt;
    private String _dbType;

    public OracleConnector(String p_ip_address, String p_db_name, String p_db_user, String p_db_pwd) {
        // Load Oracle driver
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@" + p_ip_address +
                    ":1521:" + p_db_name;
            conn = java.sql.DriverManager.getConnection(url, p_db_user, p_db_pwd);

            System.out.println("\nConnecting to oracle server....\n");

            conn.setAutoCommit(false);
            setDbType("Oracle");
        } catch (Exception e) {
            System.out.println("Could Not Connect To Oracle - " + e.getMessage());
            e.printStackTrace();
        }
    }

    public OracleConnector(String p_ip_address, String p_db_name, String p_db_user, String p_db_pwd,boolean p_autoCommit) {
        // Load Oracle driver
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@" + p_ip_address +
                    ":1521:" + p_db_name;
            conn = java.sql.DriverManager.getConnection(url, p_db_user, p_db_pwd);

            conn.setAutoCommit(p_autoCommit);
            setDbType("Oracle");
        } catch (Exception e) {
            System.out.println("Could Not Connect To Oracle" + e.getMessage());
            e.printStackTrace();
        }
    }


    public void executeUpdate(String sqlCommand) throws SQLException {
        Statement stmt = getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        stmt.executeUpdate(sqlCommand);
        stmt.close();
        stmt = null;
    }

    public ResultSet executeQuery(String sqlCommand) throws SQLException {
        _stmnt = getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rst;
        rst = _stmnt.executeQuery(sqlCommand);
        return rst;

    }

    public void closeStatement() {
        try {
            _stmnt.close();
            _stmnt = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return getConn().prepareCall(sql);

    }

    public void tranStart() throws SQLException {
        getConn().setTransactionIsolation(8);

    }

    public void tranClose() throws SQLException {
        getConn().setTransactionIsolation(2);

    }

    public void commit() throws SQLException {
        getConn().commit();
    }

    public void rollback() throws SQLException {
        getConn().rollback();
    }

    public void finalize() throws SQLException {
        getConn().close();
    }

    /**
     * @return the _dbType
     */
    public String getDbType() {
        return _dbType;
    }

    /**
     * @param dbType the _dbType to set
     */
    public void setDbType(String dbType) {
        this._dbType = dbType;
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }
}
