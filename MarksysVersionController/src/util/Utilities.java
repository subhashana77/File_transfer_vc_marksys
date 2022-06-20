package util;

import dataaccess.OracleConnector;
import model.Locations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author dilshan.r
 * @created 4/20/2022 - 3:30 PM
 * @project OracleToPOSJar
 * @ide IntelliJ IDEA
 */
public class Utilities {

    //    oracle database
    String oracleLocalDbUser = "TMARKSYS";
    String oracleLocalPassword = "TESTMARKSYS";
    String oracleLocalIpAddress = "192.168.1.27";
    String oracleLocalDbName = "rpd2";

    Exception _exception = null;

    private final String sbucode = "830";
    private final String loccode = "100";
    private final String pathcode = "VCPATHS";
    private final String filecode = "VCFILES";

    OracleConnector oracleConnector = new OracleConnector(oracleLocalIpAddress, oracleLocalDbName, oracleLocalDbUser, oracleLocalPassword);

    public void logDataFunction(String category, Object description, String server_ip, String loc_code, String sbu_code, String loc_cate) {
        try {
            // start transaction
            if (startTransactionTMarksys() == 0) {
                throw new Exception(_exception.getLocalizedMessage());
            }

            Statement statement = oracleConnector.getConn().createStatement();

            String insertQuery = "INSERT INTO VC_DATA_LOG (LOG_DATE, TIMESTAMP, CATEGORY, DESCRIPTION, SERVER_IP, LOC_CODE, SBU_CODE, LOC_CATE) " +
                    "VALUES (SYSDATE, CURRENT_TIMESTAMP, '" + category + "', '" + description + "', '"+server_ip+"', '"+loc_code+"', '"+sbu_code+"', '"+loc_cate+"')";
            if (statement.executeUpdate(insertQuery) <= 0) {
                System.out.println("Something went wrong. Data cannot update to log database \n");
            }

            // end transaction
            if (endTransactionTMarksys() == 0) {
                throw new Exception(_exception.getLocalizedMessage());
            }

        } catch (SQLException exception) {
            System.out.println("Exception : " + exception);
        } catch (Exception exception) {
            System.out.println("Transaction Exception : " + exception);
            // abort transaction
            abortTransactionTMarksys();
        }
    }

    public HashMap<String, String> getParameters() {
        Utilities utilities = new Utilities();

        HashMap<String, String> parameters = new HashMap<>();
        try {
            Statement statement = oracleConnector.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT CVAL01, CVAL02, CVAL03, CCOMM, CREMA" +
                            " FROM SMSYSPARA" +
                            " WHERE SBUCOD = '" + sbucode + "' and LOCCOD = '" + loccode + "' and PARCOD = '" + pathcode + "'"
            );

            while (resultSet.next()) {
                parameters.put("_local_marksys_path", resultSet.getString("CVAL01"));
                parameters.put("_remote_marksys_path", resultSet.getString("CVAL02"));
                parameters.put("_local_pos_path", resultSet.getString("CCOMM"));
                parameters.put("_remote_pos_path", resultSet.getString("CVAL03"));
                parameters.put("_local_script_path", resultSet.getString("CREMA"));
            }
            resultSet.close();
            statement.close();

        } catch (SQLException exception) {
            System.out.println("ERROR - parameters fetching is fail!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - parameters fetching is fail!" + exception,
                    null,
                    null,
                    null,
                    null
            );
        }
        return parameters;
    }

    public HashMap<String, String> getServerParameters() {
        Utilities utilities = new Utilities();
        HashMap<String, String> svparams = new HashMap<>();
        try {
            Statement statement = oracleConnector.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT CVAL01, CCOMM, CREMA" +
                            " FROM SMSYSPARA" +
                            " WHERE SBUCOD = '" + sbucode + "' and LOCCOD = '" + loccode + "' and PARCOD = '" + filecode + "'"
            );

            while (resultSet.next()) {
                svparams.put("_marksys_file_name", resultSet.getString("CVAL01"));
                svparams.put("_pos_file_name", resultSet.getString("CCOMM"));
                svparams.put("_script_file_name", resultSet.getString("CREMA"));
            }
            resultSet.close();
            statement.close();

        } catch (SQLException exception) {
            System.out.println("ERROR - parameters fetching is fail!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - parameters fetching is fail!" + exception,
                    null,
                    null,
                    null,
                    null
            );
        }
        return svparams;
    }

    public ArrayList<Locations> getSRLocations() {
        Utilities utilities = new Utilities();

        ArrayList<Locations> locations = new ArrayList<>();
        try {
            Statement statement = oracleConnector.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT SBU_CODE, LOC_CODE, IP, CRE_BY, PASSWD, USERID " +
                            " FROM RMS_SITES" +
                            " WHERE MOD_BY = 'SR' AND SBU_CODE = '" + sbucode + "'"
            );

            while (resultSet.next()) {
                Locations location = new Locations(
                        resultSet.getString("SBU_CODE"),
                        resultSet.getString("LOC_CODE"),
                        resultSet.getString("IP"),
                        resultSet.getString("CRE_BY"),
                        resultSet.getString("PASSWD"),
                        resultSet.getString("USERID")
                );
                locations.add(location);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException exception) {
            System.out.println("ERROR - location fetching is fail!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - location fetching is fail!" + exception,
                    null,
                    null,
                    null,
                    null
            );
        }
        return locations;
    }

    public ArrayList<Locations> getSCLocations() {
        Utilities utilities = new Utilities();

        ArrayList<Locations> locations = new ArrayList<>();
        try {
            Statement statement = oracleConnector.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT SBU_CODE, LOC_CODE, IP, CRE_BY, PASSWD, USERID " +
                            " FROM RMS_SITES" +
                            " WHERE MOD_BY = 'SC' AND SBU_CODE = '" + sbucode + "'"
            );

            while (resultSet.next()) {
                Locations location = new Locations(
                        resultSet.getString("SBU_CODE"),
                        resultSet.getString("LOC_CODE"),
                        resultSet.getString("IP"),
                        resultSet.getString("CRE_BY"),
                        resultSet.getString("PASSWD"),
                        resultSet.getString("USERID")
                );
                locations.add(location);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException exception) {
            System.out.println("ERROR - location fetching is fail!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - location fetching is fail!" + exception,
                    null,
                    null,
                    null,
                    null
            );
        }
        return locations;
    }

    public ArrayList<Locations> getADALocations() {
        Utilities utilities = new Utilities();

        ArrayList<Locations> locations = new ArrayList<>();
        try {
            Statement statement = oracleConnector.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT SBU_CODE, LOC_CODE, IP, CRE_BY, PASSWD, USERID " +
                            " FROM RMS_SITES" +
                            " WHERE MOD_BY = 'ADA' AND SBU_CODE = '" + sbucode + "'"
            );

            while (resultSet.next()) {
                Locations location = new Locations(
                        resultSet.getString("SBU_CODE"),
                        resultSet.getString("LOC_CODE"),
                        resultSet.getString("IP"),
                        resultSet.getString("CRE_BY"),
                        resultSet.getString("PASSWD"),
                        resultSet.getString("USERID")
                );
                locations.add(location);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException exception) {
            System.out.println("ERROR - location fetching is fail!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - location fetching is fail!" + exception,
                    null,
                    null,
                    null,
                    null
            );
        }
        return locations;
    }

    public ArrayList<Locations> msqlSRConnection () {
        Utilities utilities = new Utilities();
        ArrayList<Locations> msqlconnection = new ArrayList<>();
        try {
            Statement statement = oracleConnector.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT SBU_CODE, LOC_CODE, SITE_NAME, IP, DB, MOD_BY, PASSWD, USERID, DBTYPE" +
                            " FROM RMS_SITES" +
                            " WHERE MOD_BY = 'SR' AND  SBU_CODE='" + sbucode + "'"
            );

            while (resultSet.next()) {
                Locations locations = new Locations(
                        resultSet.getString("SBU_CODE"),
                        resultSet.getString("LOC_CODE"),
                        resultSet.getString("SITE_NAME"),
                        resultSet.getString("IP"),
                        resultSet.getString("DB"),
                        resultSet.getString("MOD_BY"),
                        resultSet.getString("PASSWD"),
                        resultSet.getString("USERID"),
                        resultSet.getString("DBTYPE")
                );
                msqlconnection.add(locations);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException exception) {
            System.out.println("ERROR - sql connection fetching is fail!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - sql connection fetching is fail!" + exception,
                    null,
                    null,
                    null,
                    null
            );
        }
        return msqlconnection;
    }

    public ArrayList<Locations> msqlSCConnection () {
        Utilities utilities = new Utilities();
        ArrayList<Locations> msqlconnection = new ArrayList<>();
        try {
            Statement statement = oracleConnector.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT SBU_CODE, LOC_CODE, SITE_NAME, IP, DB, MOD_BY, PASSWD, USERID, DBTYPE" +
                            " FROM RMS_SITES" +
                            " WHERE MOD_BY = 'SC' AND  SBU_CODE='" + sbucode + "'"
            );

            while (resultSet.next()) {
                Locations locations = new Locations(
                        resultSet.getString("SBU_CODE"),
                        resultSet.getString("LOC_CODE"),
                        resultSet.getString("SITE_NAME"),
                        resultSet.getString("IP"),
                        resultSet.getString("DB"),
                        resultSet.getString("MOD_BY"),
                        resultSet.getString("PASSWD"),
                        resultSet.getString("USERID"),
                        resultSet.getString("DBTYPE")
                );
                msqlconnection.add(locations);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException exception) {
            System.out.println("ERROR - sql connection fetching is fail!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - sql connection fetching is fail!" + exception,
                    null,
                    null,
                    null,
                    null
            );
        }
        return msqlconnection;
    }

    public ArrayList<Locations> msqlADAConnection () {
        Utilities utilities = new Utilities();
        ArrayList<Locations> msqlconnection = new ArrayList<>();
        try {
            Statement statement = oracleConnector.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT SBU_CODE, LOC_CODE, SITE_NAME, IP, DB, MOD_BY, PASSWD, USERID, DBTYPE" +
                            " FROM RMS_SITES" +
                            " WHERE MOD_BY = 'ADA' AND  SBU_CODE='" + sbucode + "'"
            );

            while (resultSet.next()) {
                Locations locations = new Locations(
                        resultSet.getString("SBU_CODE"),
                        resultSet.getString("LOC_CODE"),
                        resultSet.getString("SITE_NAME"),
                        resultSet.getString("IP"),
                        resultSet.getString("DB"),
                        resultSet.getString("MOD_BY"),
                        resultSet.getString("PASSWD"),
                        resultSet.getString("USERID"),
                        resultSet.getString("DBTYPE")
                );
                msqlconnection.add(locations);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException exception) {
            System.out.println("ERROR - sql connection fetching is fail!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - sql connection fetching is fail!" + exception,
                    null,
                    null,
                    null,
                    null
            );
        }
        return msqlconnection;
    }

    public void saveQuery(String loc_code, String sbu_code, String sql_statement, String sql_query, String ip_address){
        Utilities utilities = new Utilities();
        try {
            // start transaction
            if (startTransactionTMarksys() == 0) {
                throw new Exception(_exception.getLocalizedMessage());
            }

            Statement statement = oracleConnector.getConn().createStatement();

            String insertQuery = "INSERT INTO VC_SQL_QUERY (LOC_CODE, SBU_CODE, SQL_STATEMENT, SQL_QUERY, IP_ADDRESS, EXECUTED_DATE) " +
                    "VALUES ('" + loc_code + "', '" + sbu_code + "', '" + sql_statement + "', '"+sql_query+"', '"+ip_address+"', SYSDATE)";

            if (statement.executeUpdate(insertQuery) <= 0) {
                System.out.println("ERROR - Something went wrong. Data cannot update log database");
                utilities.logDataFunction(
                        "mysql",
                        "ERROR - Something went wrong. Data cannot update log database",
                        null,
                        null,
                        null,
                        null
                );
            }
            statement.close();

            // end transaction
            if (endTransactionTMarksys() == 0) {
                throw new Exception(_exception.getLocalizedMessage());
            }

        } catch (SQLException exception) {

            System.out.println("ERROR - SQL Query uploading fail!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - SQL Query uploading fail!" + exception,
                    null,
                    null,
                    null,
                    null
            );
        } catch (Exception exception) {
            System.out.println("ERROR - Transaction failure!" + exception);
            utilities.logDataFunction(
                    "mysql",
                    "ERROR - Transaction failure!!" + exception,
                    null,
                    null,
                    null,
                    null
            );
            // abort transaction
            abortTransactionTMarksys();
        }
    }

    //    Start transaction method
    public int startTransactionTMarksys() {
        try {
            if (!oracleConnector.getConn().getAutoCommit()) {
                oracleConnector.rollback();
                oracleConnector.getConn().setAutoCommit(true);
            }
            oracleConnector.getConn().setAutoCommit(false);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //    End Transaction method
    public int endTransactionTMarksys() {
        try {
            oracleConnector.getConn().commit();
            oracleConnector.getConn().setAutoCommit(true);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //    Abort Transaction method
    public void abortTransactionTMarksys() {
        try {
            if (!oracleConnector.getConn().getAutoCommit()) {
                oracleConnector.getConn().rollback();
                oracleConnector.getConn().setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
