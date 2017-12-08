package eu.trails2education.trails.database;

import java.io.Serializable;

/**
 * Created by svetl on 5. 12. 2017.
 */

public class Coordinates implements Serializable {

    public static final String TAG = "Coordinates";
    private static final long serialVersionUID = -7406082437623008161L;

    private long cId;
    private double lat;
    private double lon;
    private double alt;
    private long sort;
    private Pathway mPathway;

    public Coordinates() {

    }

    public Coordinates(double clat, double clon, double calt, long csort) {
        this.lat = clat;
        this.lon = clon;
        this.alt = calt;
        this.sort = csort;
    }

    public long getIdC() {
        return cId;
    }

    public void setIdC(long mIdC) {
        this.cId = mIdC;
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

    public long getcsort() {
        return sort;
    }
    public void setcsort(long csort) {
        this.sort = csort;
    }

    public Pathway getPathway() {
        return mPathway;
    }
    public void setPathway(Pathway mPathway) {
        this.mPathway = mPathway;
    }

}
