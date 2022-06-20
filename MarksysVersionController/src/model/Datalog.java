package model;

import org.apache.commons.net.ntp.TimeStamp;

/**
 * @author dilshan.r
 * @created 5/4/2022 - 4:04 PM
 * @project MarksysVersionController
 * @ide IntelliJ IDEA
 */
public class Datalog {
    private int LOG_ID;
    private String LOG_DATE;
    private TimeStamp TIMESTAMP;
    private String CATEGORY;
    private String DESCRIPTION;
    private String SERVER_IP;
    private String LOC_CODE;
    private String SBU_CODE;
    private String LOC_CATE;

    public Datalog(String LOG_DATE, TimeStamp TIMESTAMP, String CATEGORY, String DESCRIPTION, String SERVER_IP, String LOC_CODE, String SBU_CODE, String LOC_CATE) {
        this.LOG_DATE = LOG_DATE;
        this.TIMESTAMP = TIMESTAMP;
        this.CATEGORY = CATEGORY;
        this.DESCRIPTION = DESCRIPTION;
        this.SERVER_IP = SERVER_IP;
        this.LOC_CODE = LOC_CODE;
        this.SBU_CODE = SBU_CODE;
        this.LOC_CATE = LOC_CATE;
    }

    public int getLOG_ID() {
        return LOG_ID;
    }

    public void setLOG_ID(int LOG_ID) {
        this.LOG_ID = LOG_ID;
    }

    public String getLOG_DATE() {
        return LOG_DATE;
    }

    public void setLOG_DATE(String LOG_DATE) {
        this.LOG_DATE = LOG_DATE;
    }

    public TimeStamp getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(TimeStamp TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getSERVER_IP() {
        return SERVER_IP;
    }

    public void setSERVER_IP(String SERVER_IP) {
        this.SERVER_IP = SERVER_IP;
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

    public String getLOC_CATE() {
        return LOC_CATE;
    }

    public void setLOC_CATE(String LOC_CATE) {
        this.LOC_CATE = LOC_CATE;
    }

    @Override
    public String toString() {
        return "Datalog{" +
                "LOG_ID=" + LOG_ID +
                ", LOG_DATE='" + LOG_DATE + '\'' +
                ", TIMESTAMP=" + TIMESTAMP +
                ", CATEGORY='" + CATEGORY + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", SERVER_IP='" + SERVER_IP + '\'' +
                ", LOC_CODE='" + LOC_CODE + '\'' +
                ", SBU_CODE='" + SBU_CODE + '\'' +
                ", LOC_CATE='" + LOC_CATE + '\'' +
                '}';
    }
}
