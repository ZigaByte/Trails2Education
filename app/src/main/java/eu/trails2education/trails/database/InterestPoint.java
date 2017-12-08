package eu.trails2education.trails.database;

import java.io.Serializable;

/**
 * Created by svetl on 6. 12. 2017.
 */

public class InterestPoint implements Serializable {

    public static final String TAG = "InterestPoint";
    private static final long serialVersionUID = -7406082437623008161L;

    private long cIdIP;
    private String ipnameEN;
    private String ipnameFR;
    private String ipnamePT;
    private String ipnameSL;
    private String ipnameEE;
    private String ipnameIT;
    private long pointtype;
    private Pathway mPathway;

    public InterestPoint() {

    }

    public InterestPoint(String nameEN, String nameFR, String namePT, String nameSL, String nameEE, String nameIT, long ctype) {
        this.ipnameEN = nameEN;
        this.ipnameFR = nameFR;
        this.ipnamePT = namePT;
        this.ipnameSL = nameSL;
        this.ipnameEE = nameEE;
        this.ipnameIT = nameIT;
        this.pointtype = ctype;
    }

    public long getcIdIP() {
        return cIdIP;
    }
    public void setIdIP(long mIdIP) {
        this.cIdIP = mIdIP;
    }

    public String getNameEN() {
        return ipnameEN;
    }
    public void setNameEN(String nameEN) {
        this.ipnameEN = nameEN;
    }

    public String getNameFR() {
        return ipnameFR;
    }
    public void setNameFR(String nameFR) {
        this.ipnameFR = nameFR;
    }

    public String getNamePT() {
        return ipnamePT;
    }
    public void setNamePT(String namePT) {
        this.ipnamePT = namePT;
    }

    public String getNameSL() {
        return ipnameSL;
    }
    public void setNameSL(String nameSL) {
        this.ipnameSL = nameSL;
    }

    public String getNameEE() {
        return ipnameEE;
    }
    public void setNameEE(String nameEE) {
        this.ipnameEE = nameEE;
    }

    public String getNameIT() {
        return ipnameIT;
    }
    public void setNameIT(String nameIT) {
        this.ipnameIT = nameIT;
    }

    public long getctype() {
        return pointtype;
    }
    public void setctype(long ctype) {
        this.pointtype = ctype;
    }

    public Pathway getPathway() {
        return mPathway;
    }
    public void setPathway(Pathway mPathway) {
        this.mPathway = mPathway;
    }


}
