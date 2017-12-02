package eu.trails2education.trails.path;

import java.io.Serializable;

/**
 * Created by Žiga on 25. 11. 2017.
 */

public class Coordinate implements Serializable{
    public double lat, lon;
    public final int ID;

    public Coordinate(double lat, double lon, int id){
        this.lat = lat;
        this.lon = lon;
        this.ID = id;
    }
}