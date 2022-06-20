/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.*;
import java.lang.*;


/**
 *
 * @author dilshan.r
 */
public class MysqlConnector {
    private Connection conn;
    private Statement stmt;
    private String _dbType;

    public MysqlConnector(String p_ip_address, String p_db_name, String p_db_user, String p_db_pwd) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://" + p_ip_address + "/" + p_db_name + "?user=" + p_db_user + "&password=" + p_db_pwd);
            System.out.println("Connecting to mysql server....");
            stmt = (Statement) conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            setDbType("MySql");
        } catch (Exception ex) {
            System.out.println("Could not Connect to " + p_ip_address + " " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void executeUpdate(String sqlCommand)
            throws SQLException {
        stmt.executeUpdate(sqlCommand);
    }

    public ResultSet executeQuery(String sqlCommand)
            throws SQLException {
        return stmt.executeQuery(sqlCommand);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return getConn().prepareCall(sql);

    }

    public void commit() throws SQLException {
        getConn().commit();
    }

    public void rollback() throws SQLException {
        getConn().rollback();
    }

    public void finalize() throws SQLException {
        stmt.close();
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
