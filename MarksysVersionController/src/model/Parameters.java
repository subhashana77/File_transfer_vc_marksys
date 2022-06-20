package model;

/**
 * @author dilshan.r
 * @created 4/22/2022 - 11:30 AM
 * @project OracleToPOSJar
 * @ide IntelliJ IDEA
 */
public class Parameters {
    private String SBUCOD;
    private String LOCCOD;
    private String PARCOD;
    private String CVAL01;
    private String CVAL02;
    private String CVAL03;
    private String CCOMM;
    private String CREMA;

    public Parameters(String SBUCOD, String LOCCOD, String PARCOD, String CVAL01, String CVAL02, String CVAL03, String CCOMM, String CREMA) {
        this.SBUCOD = SBUCOD;
        this.LOCCOD = LOCCOD;
        this.PARCOD = PARCOD;
        this.CVAL01 = CVAL01;
        this.CVAL02 = CVAL02;
        this.CVAL03 = CVAL03;
        this.CCOMM = CCOMM;
        this.CREMA = CREMA;
    }

    public String getSBUCOD() {
        return SBUCOD;
    }

    public void setSBUCOD(String SBUCOD) {
        this.SBUCOD = SBUCOD;
    }

    public String getLOCCOD() {
        return LOCCOD;
    }

    public void setLOCCOD(String LOCCOD) {
        this.LOCCOD = LOCCOD;
    }

    public String getPARCOD() {
        return PARCOD;
    }

    public void setPARCOD(String PARCOD) {
        this.PARCOD = PARCOD;
    }

    public String getCVAL01() {
        return CVAL01;
    }

    public void setCVAL01(String CVAL01) {
        this.CVAL01 = CVAL01;
    }

    public String getCVAL02() {
        return CVAL02;
    }

    public void setCVAL02(String CVAL02) {
        this.CVAL02 = CVAL02;
    }

    public String getCVAL03() {
        return CVAL03;
    }

    public void setCVAL03(String CVAL03) {
        this.CVAL03 = CVAL03;
    }

    public String getCCOMM() {
        return CCOMM;
    }

    public void setCCOMM(String CCOMM) {
        this.CCOMM = CCOMM;
    }

    public String getCREMA() {
        return CREMA;
    }

    public void setCREMA(String CREMA) {
        this.CREMA = CREMA;
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "SBUCOD='" + SBUCOD + '\'' +
                ", LOCCOD='" + LOCCOD + '\'' +
                ", PARCOD='" + PARCOD + '\'' +
                ", CVAL01='" + CVAL01 + '\'' +
                ", CVAL02='" + CVAL02 + '\'' +
                ", CVAL03='" + CVAL03 + '\'' +
                ", CCOMM='" + CCOMM + '\'' +
                ", CREMA='" + CREMA + '\'' +
                '}';
    }
}
