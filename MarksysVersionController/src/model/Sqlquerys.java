package model;

/**
 * @author dilshan.r
 * @created 4/27/2022 - 5:11 PM
 * @project OracleToPOSJar
 * @ide IntelliJ IDEA
 */
public class Sqlquerys {
    private int SCRIPT_ID;
    private String LOC_CODE;
    private String SBU_CODE;
    private String SQL_STATEMENT;
    private String SQL_QUERY;
    private String IP_ADDRESS;
    private String EXECUTED_DATE;

    public Sqlquerys(String LOC_CODE, String SBU_CODE, String SQL_STATEMENT, String SQL_QUERY, String IP_ADDRESS, String EXECUTED_DATE) {
        this.LOC_CODE = LOC_CODE;
        this.SBU_CODE = SBU_CODE;
        this.SQL_STATEMENT = SQL_STATEMENT;
        this.SQL_QUERY = SQL_QUERY;
        this.IP_ADDRESS = IP_ADDRESS;
        this.EXECUTED_DATE = EXECUTED_DATE;
    }

    public int getSCRIPT_ID() {
        return SCRIPT_ID;
    }

    public void setSCRIPT_ID(int SCRIPT_ID) {
        this.SCRIPT_ID = SCRIPT_ID;
    }

    public String getLOC_CODE() {
        return LOC_CODE;
    }

    public void setLOC_CODE(String LOC_CODE) {
        this.LOC_CODE = LOC_CODE;
    }

    public String getSBU_CODE() {
        return SBU_CODE;
    }

    public void setSBU_CODE(String SBU_CODE) {
        this.SBU_CODE = SBU_CODE;
    }

    public String getSQL_STATEMENT() {
        return SQL_STATEMENT;
    }

    public void setSQL_STATEMENT(String SQL_STATEMENT) {
        this.SQL_STATEMENT = SQL_STATEMENT;
    }

    public String getSQL_QUERY() {
        return SQL_QUERY;
    }

    public void setSQL_QUERY(String SQL_QUERY) {
        this.SQL_QUERY = SQL_QUERY;
    }

    public String getIP_ADDRESS() {
        return IP_ADDRESS;
    }

    public void setIP_ADDRESS(String IP_ADDRESS) {
        this.IP_ADDRESS = IP_ADDRESS;
    }

    public String getEXECUTED_DATE() {
        return EXECUTED_DATE;
    }

    public void setEXECUTED_DATE(String EXECUTED_DATE) {
        this.EXECUTED_DATE = EXECUTED_DATE;
    }
}
