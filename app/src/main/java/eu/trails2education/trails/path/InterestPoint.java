package eu.trails2education.trails.path;

/**
 * Created by Žiga on 25. 11. 2017.
 */

public class InterestPoint {

    public final Coordinate coordinate;
    private final int ID;
    private final int type;

    public InterestPoint(Coordinate coordinate, int ID, int type){
        this.coordinate = coordinate;
        this.ID = ID;
        this.type = type;
    }

}
