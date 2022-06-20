package model;

/**
 * @author dilshan.r
 * @created 4/22/2022 - 11:30 AM
 * @project OracleToPOSJar
 * @ide IntelliJ IDEA
 */
public class Locations {
    private String SBU_CODE;
    private String LOC_CODE;
    private String SITE_ID;
    private String SITE_NAME;
    private String IP;
    private String DB;
    private String CRE_BY;
    private String MOD_BY;
    private String PASSWD;
    private String USERID;
    private String ALTSBU;
    private String ITMLOC;
    private String CVAL01;
    private String CCOMM;
    private String DBTYPE;

    public Locations(String SBU_CODE, String LOC_CODE, String IP, String CRE_BY, String PASSWD, String USERID, String CVAL01, String CCOMM) {
        this.SBU_CODE = SBU_CODE;
        this.LOC_CODE = LOC_CODE;
        this.IP = IP;
        this.CRE_BY = CRE_BY;
        this.PASSWD = PASSWD;
        this.USERID = USERID;
        this.CVAL01 = CVAL01;
        this.CCOMM = CCOMM;
    }

    public Locations(String SBU_CODE, String LOC_CODE, String SITE_NAME, String IP, String DB, String MOD_BY, String PASSWD, String USERID, String DBTYPE) {
        this.SBU_CODE = SBU_CODE;
        this.LOC_CODE = LOC_CODE;
        this.SITE_NAME = SITE_NAME;
        this.IP = IP;
        this.DB = DB;
        this.MOD_BY = MOD_BY;
        this.PASSWD = PASSWD;
        this.USERID = USERID;
        this.DBTYPE = DBTYPE;
    }

    public Locations(String SBU_CODE, String LOC_CODE, String IP, String CRE_BY, String PASSWD, String USERID) {
        this.SBU_CODE = SBU_CODE;
        this.LOC_CODE = LOC_CODE;
        this.IP = IP;
        this.CRE_BY = CRE_BY;
        this.PASSWD = PASSWD;
        this.USERID = USERID;
    }

    public Locations(String SBU_CODE, String LOC_CODE, String IP, String DB, String MOD_BY, String PASSWD, String USERID ) {
        this.SBU_CODE = SBU_CODE;
        this.LOC_CODE = LOC_CODE;
        this.IP = IP;
        this.DB = DB;
        this.MOD_BY = MOD_BY;
        this.PASSWD = PASSWD;
        this.USERID = USERID;
    }

    public String getSBU_CODE() {
        return SBU_CODE;
    }

    public void setSBU_CODE(String SBU_CODE) {
        this.SBU_CODE = SBU_CODE;
    }

    public String getLOC_CODE() {
        return LOC_CODE;
    }

    public void setLOC_CODE(String LOC_CODE) {
        this.LOC_CODE = LOC_CODE;
    }

    public String getSITE_ID() {
        return SITE_ID;
    }

    public void setSITE_ID(String SITE_ID) {
        this.SITE_ID = SITE_ID;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getDB() {
        return DB;
    }

    public void setDB(String DB) {
        this.DB = DB;
    }

    public String getCRE_BY() {
        return CRE_BY;
    }

    public void setCRE_BY(String CRE_BY) {
        this.CRE_BY = CRE_BY;
    }

    public String getMOD_BY() {
        return MOD_BY;
    }

    public void setMOD_BY(String MOD_BY) {
        this.MOD_BY = MOD_BY;
    }

    public String getPASSWD() {
        return PASSWD;
    }

    public void setPASSWD(String PASSWD) {
        this.PASSWD = PASSWD;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getALTSBU() {
        return ALTSBU;
    }

    public void setALTSBU(String ALTSBU) {
        this.ALTSBU = ALTSBU;
    }

    public String getITMLOC() {
        return ITMLOC;
    }

    public void setITMLOC(String ITMLOC) {
        this.ITMLOC = ITMLOC;
    }

    public String getCVAL01() {
        return CVAL01;
    }

    public void setCVAL01(String CVAL01) {
        this.CVAL01 = CVAL01;
    }

    public String getCCOMM() {
        return CCOMM;
    }

    public void setCCOMM(String CCOMM) {
        this.CCOMM = CCOMM;
    }

    public String getSITE_NAME() {
        return SITE_NAME;
    }

    public void setSITE_NAME(String SITE_NAME) {
        this.SITE_NAME = SITE_NAME;
    }

    public String getDBTYPE() {
        return DBTYPE;
    }

    public void setDBTYPE(String DBTYPE) {
        this.DBTYPE = DBTYPE;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "SBU_CODE='" + SBU_CODE + '\'' +
                ", LOC_CODE='" + LOC_CODE + '\'' +
                ", SITE_ID='" + SITE_ID + '\'' +
                ", SITE_NAME='" + SITE_NAME + '\'' +
                ", IP='" + IP + '\'' +
                ", DB='" + DB + '\'' +
                ", CRE_BY='" + CRE_BY + '\'' +
                ", MOD_BY='" + MOD_BY + '\'' +
                ", PASSWD='" + PASSWD + '\'' +
                ", USERID='" + USERID + '\'' +
                ", ALTSBU='" + ALTSBU + '\'' +
                ", ITMLOC='" + ITMLOC + '\'' +
                ", CVAL01='" + CVAL01 + '\'' +
                ", CCOMM='" + CCOMM + '\'' +
                ", DBTYPE='" + DBTYPE + '\'' +
                '}';
    }
}
