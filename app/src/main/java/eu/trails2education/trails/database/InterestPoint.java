package eu.trails2education.trails.database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by svetl on 6. 12. 2017.
 */

public class InterestPoint implements Serializable {

    public static final String TAG = "InterestPoint";
    private static final long serialVersionUID = -7406082437623008161L;

    private long cIdIP;

    private double lat;
    private double lon;
    private double alt;

    private String ipnameEN;
    private String ipnameFR;
    private String ipnamePT;
    private String ipnameSL;
    private String ipnameEE;
    private String ipnameIT;
    private long pointtype;

    private long pathwayID;

    private ArrayList<Content> contents = new ArrayList<Content>();
    public ArrayList<Integer> subjectIds = new ArrayList<Integer>();

    private Pathway mPathway;

    public InterestPoint() {

    }

    public InterestPoint(String nameEN, String nameFR, String namePT, String nameSL, String nameEE, String nameIT, long ctype,
                         double clat, double clon, double calt) {
        this.ipnameEN = nameEN;
        this.ipnameFR = nameFR;
        this.ipnamePT = namePT;
        this.ipnameSL = nameSL;
        this.ipnameEE = nameEE;
        this.ipnameIT = nameIT;
        this.pointtype = ctype;
        this.lat = clat;    // Svetlana: 13.1.2018 dodala koordinate
        this.lon = clon;
        this.alt = calt;
    }

    public long getPathwayID() {
        return pathwayID;
    }
    public void setPathwayID(long mIdC) {
        this.pathwayID = mIdC;
    }

    public long getcIdIP() {
        return cIdIP;
    }
    public void setIdIP(long mIdIP) {
        this.cIdIP = mIdIP;
    }

    public double getclat() {
        return lat;
    }
    public void setclat(double clat) {
        this.lat = clat;
    }

    public double getclon() {
        return lon;
    }
    public void setclon(double clon) {
        this.lon = clon;
    }

    public double getcalt() {
        return alt;
    }
    public void setcalt(double calt) {
        this.alt = calt;
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

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }
    public ArrayList<Content> getContents() {
        return contents;
    }

    public void setSubjectIds(ArrayList<Integer> subjectIds) {
        this.subjectIds = subjectIds;
    }
    public ArrayList<Integer> getSubjectIds() {
        return subjectIds;
    }

}
