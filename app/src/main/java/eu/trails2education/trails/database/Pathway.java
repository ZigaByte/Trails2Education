package eu.trails2education.trails.database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by svetl on 4. 12. 2017.
 */

public class Pathway implements Serializable{

    public static final String TAG = "Pathway";
    private static final long serialVersionUID = -7406082437623008161L;

    public long idPathway;
    private String pathwayNameEN;
    private String pathwayNameFR;
    private String pathwayNamePT;
    private String pathwayNameSL;
    private String pathwayNameEE;
    private String pathwayNameIT;
    private String registerDate;
    private long totalMeters;
    private long estimatedCalories;
    private long estimatedTime;
    private long estimatedStepsRotat;
    private String averageHeartBeatRate;
    private String vehicleEN;
    private String vehicleFR;
    private String vehiclePT;
    private String vehicleSL;
    private String vehicleEE;
    private String vehicleIT;
    private String countryEN;
    private String countryFR;
    private String countryPT;
    private String countrySL;
    private String countryEE;
    private String countryIT;
    private String region;
    private String area;
    private String difficultyEN;
    private String difficultyFR;
    private String difficultyPT;
    private String difficultySL;
    private String difficultyEE;
    private String difficultyIT;

    private ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
    private ArrayList<InterestPoint> interestPoints = new ArrayList<InterestPoint>();


    public Pathway() {}


    public Pathway(String nameEN, String nameFR, String namePT, String nameSL, String nameEE, String nameIT, String regDate,
                   long totM, long estCal, long estTime, long estStep, String avgHB,
                   String vehEN, String vehFR, String vehPT, String vehSL, String vehEE, String vehIT,
                   String couEN, String couFR, String couPT, String couSL, String couEE, String couIT,
                   String reg, String ar,
                   String difEN, String difFR, String difPT, String difSL, String difEE, String difIT) {
        this.pathwayNameEN = nameEN;
        this.pathwayNameFR = nameFR;
        this.pathwayNamePT = namePT;
        this.pathwayNameSL = nameSL;
        this.pathwayNameEE = nameEE;
        this.pathwayNameIT = nameIT;
        this.registerDate = regDate;
        this.totalMeters = totM;
        this.estimatedCalories = estCal;
        this.estimatedTime = estTime;
        this.estimatedStepsRotat = estStep;
        this.averageHeartBeatRate = avgHB;
        this.vehicleEN = vehEN;
        this.vehicleFR = vehFR;
        this.vehiclePT = vehPT;
        this.vehicleSL = vehSL;
        this.vehicleEE = vehEE;
        this.vehicleIT = vehIT;
        this.countryEN = couEN;
        this.countryFR = couFR;
        this.countryPT = couPT;
        this.countrySL = couSL;
        this.countryEE = couEE;
        this.countryIT = couIT;
        this.region = reg;
        this.area = ar;
        this.difficultyEN = difEN;
        this.difficultyFR = difFR;
        this.difficultyPT = difPT;
        this.difficultySL = difSL;
        this.difficultyEE = difEE;
        this.difficultyIT = difIT;
    }

    public long getId() {
        return idPathway;
    }
    public void setId(long pId) {
        this.idPathway = pId;
    }

    public String getNameEN() {
        return pathwayNameEN;
    }
    public void setNameEN(String nameEN) {
        this.pathwayNameEN = nameEN;
    }

    public String getNameFR() {
        return pathwayNameFR;
    }
    public void setNameFR(String nameFR) {
        this.pathwayNameFR = nameFR;
    }

    public String getNamePT() {
        return pathwayNamePT;
    }
    public void setNamePT(String namePT) {
        this.pathwayNamePT = namePT;
    }

    public String getNameSL() {
        return pathwayNameSL;
    }
    public void setNameSL(String nameSL) {
        this.pathwayNameSL = nameSL;
    }

    public String getNameEE() {
        return pathwayNameEE;
    }
    public void setNameEE(String nameEE) {
        this.pathwayNameEE = nameEE;
    }

    public String getNameIT() {
        return pathwayNameIT;
    }
    public void setNameIT(String nameIT) {
        this.pathwayNameIT = nameIT;
    }

    public String getregDate() {
        return registerDate;
    }
    public void setregDate(String regDate) {
        this.registerDate = regDate;
    }

    public long gettotM() {
        return totalMeters;
    }
    public void settotM(long totM) {
        this.totalMeters = totM;
    }

    public long getestCal() {
        return estimatedCalories;
    }
    public void setestCal(long estCal) {
        this.estimatedCalories = estCal;
    }

    public long getestTime() {
        return estimatedTime;
    }
    public void setestTime(long estTime) {
        this.estimatedTime = estTime;
    }

    public long getestStep() {
        return estimatedStepsRotat;
    }
    public void setestStep(long estStep) {
        this.estimatedStepsRotat = estStep;
    }

    public String getavgHB() {
        return averageHeartBeatRate;
    }
    public void setavgHB(String avgHB) {
        this.averageHeartBeatRate = avgHB;
    }

    public String getvehEN() {
        return vehicleEN;
    }
    public void setvehEN(String vehEN) {
        this.vehicleEN = vehEN;
    }

    public String getvehFR() {
        return vehicleFR;
    }
    public void setvehFR(String vehFR) {
        this.vehicleFR = vehFR;
    }

    public String getvehPT() {
        return vehiclePT;
    }
    public void setvehPT(String vehPT) {
        this.vehiclePT = vehPT;
    }

    public String getvehSL() {
        return vehicleSL;
    }
    public void setvehSL(String vehSL) {
        this.vehicleSL = vehSL;
    }

    public String getvehEE() {
        return vehicleEE;
    }
    public void setvehEE(String vehEE) {
        this.vehicleEE = vehEE;
    }

    public String getvehIT() {
        return vehicleIT;
    }
    public void setvehIT(String vehIT) {
        this.vehicleIT = vehIT;
    }

    public String getcouEN() {
        return countryEN;
    }
    public void setcouEN(String couEN) {
        this.countryEN = couEN;
    }

    public String getcouFR() {
        return countryFR;
    }
    public void setcouFR(String couFR) {
        this.countryFR = couFR;
    }

    public String getcouPT() {
        return countryPT;
    }
    public void setcouPT(String couPT) {
        this.countryPT = couPT;
    }

    public String getcouSL() {
        return countrySL;
    }
    public void setcouSL(String couSL) {
        this.countrySL = couSL;
    }

    public String getcouEE() {
        return countryEE;
    }
    public void setcouEE(String couEE) {
        this.countryEE = couEE;
    }

    public String getcouIT() {
        return countryIT;
    }
    public void setcouIT(String couIT) {
        this.countryIT = couIT;
    }

    public String getreg() {
        return region;
    }
    public void setreg(String reg) {
        this.region = reg;
    }

    public String getar() {
        return area;
    }
    public void setar(String ar) {
        this.area = ar;
    }

    public String getdifEN() {
        return difficultyEN;
    }
    public void setdifEN(String difEN) {
        this.difficultyEN = difEN;
    }

    public String getdifFR() {
        return difficultyFR;
    }
    public void setdifFR(String difFR) {
        this.difficultyFR = difFR;
    }

    public String getdifPT() {
        return difficultyPT;
    }
    public void setdifPT(String difPT) {
        this.difficultyPT = difPT;
    }

    public String getdifSL() {
        return difficultySL;
    }
    public void setdifSL(String difSL) {
        this.difficultySL = difSL;
    }

    public String getdifEE() {
        return difficultyEE;
    }
    public void setdifEE(String difEE) {
        this.difficultyEE = difEE;
    }

    public String getdifIT() {
        return difficultyIT;
    }
    public void setdifIT(String difIT) {
        this.difficultyIT = difIT;
    }

    public void setCoorinates(ArrayList<Coordinates> coordinates){
        this.coordinates = coordinates;
    }
    public ArrayList<Coordinates> getCoordinates(){
       return coordinates;
    }

    public void setInterestPoints(ArrayList<InterestPoint> interestPoints){
        this.interestPoints = interestPoints;
    }
    public ArrayList<InterestPoint> getInterestPoints(){
        return interestPoints;
    }

}
