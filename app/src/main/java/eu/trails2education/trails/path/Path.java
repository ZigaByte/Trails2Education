package eu.trails2education.trails.path;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Žiga on 14. 09. 2017.
 */

public class Path {

    public int ID; // ID of the path
    public String name;
    public String region;
    public String date;

    public int totalMeters; // Length of path
    public String estimatedCalories;
    public String estimatedTime; // In seconds
    public String estimatedSteps;
    public String averageHeartBeatRate; // BPM
    public String vehicle; // e.g. OnFoot
    public String area; // e.g. Urban

    // Path
    public ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

    // Interest Points
    public ArrayList<InterestPoint> interestPoints = new ArrayList<InterestPoint>();


}
