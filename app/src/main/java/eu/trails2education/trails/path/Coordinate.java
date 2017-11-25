package eu.trails2education.trails.path;

/**
 * Created by Žiga on 25. 11. 2017.
 */

public class Coordinate{
    public double lat, lon;
    public final int ID;

    public Coordinate(double lat, double lon, int id){
        this.lat = lat;
        this.lon = lon;
        this.ID = id;
    }
}