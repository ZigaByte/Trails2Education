package eu.trails2education.trails.database;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by svetl on 6. 12. 2017.
 */

public class Content implements Serializable {

    public static final String TAG = "Content";
    private static final long serialVersionUID = -7406082437623008161L;

    private long interestpointid;
    private long cIdC;
    private long pointtype;
    private String subjectEN;
    private String subjectFR;
    private String subjectPT;
    private String subjectSL;
    private String subjectEE;
    private String subjectIT;
    private String titleEN;
    private String titleFR;
    private String titlePT;
    private String titleSL;
    private String titleEE;
    private String titleIT;
    private String descriptionEN;
    private String descriptionFR;
    private String descriptionPT;
    private String descriptionSL;
    private String descriptionEE;
    private String descriptionIT;
    private long subjecttype;

    private ArrayList<Multimedia> multimedia = new ArrayList<Multimedia>();

    public Content() {

    }

    public Content(long ipid, long ctype, long stype){
        this.pointtype = ctype;
        this.subjecttype = stype;
        this.interestpointid = ipid;
    }

    public Content(long ipid, long ctype, String subEN, String subFR, String subPT, String subSL, String subEE, String subIT,
                   String titEN, String titFR, String titPT, String titSL, String titEE, String titIT,
                   String desEN, String desFR, String desPT, String desSL, String desEE, String desIT,
                   long stype) {
        this.interestpointid = ipid;
        this.pointtype = ctype;
        this.subjectEN = subEN;
        this.subjectFR = subFR;
        this.subjectPT = subPT;
        this.subjectSL = subSL;
        this.subjectEE = subEE;
        this.subjectIT = subIT;
        this.titleEN = titEN;
        this.titleFR = titFR;
        this.titlePT = titPT;
        this.titleSL = titSL;
        this.titleEE = titEE;
        this.titleIT = titIT;
        this.descriptionEN = desEN;
        this.descriptionFR = desFR;
        this.descriptionPT = desPT;
        this.descriptionSL = desSL;
        this.descriptionEE = desEE;
        this.descriptionIT = desIT;
        this.subjecttype = stype;
    }

    public long getIpId() {
        return interestpointid;
    }
    public void setIpId(long interestpointid) {
        this.interestpointid = interestpointid;
    }

    public long getcIdC() {
        return cIdC;
    }
    public void setIdC(long mIdC) {
        this.cIdC = mIdC;
    }

    public String getsubEN() {
        return subjectEN;
    }
    public void setsubEN(String subEN) {
        this.subjectEN = subEN;
    }

    public String getsubFR() {
        return subjectFR;
    }
    public void setsubFR(String subFR) {
        this.subjectFR = subFR;
    }

    public String getsubPT() {
        return subjectPT;
    }
    public void setsubPT(String subPT) {
        this.subjectPT = subPT;
    }

    public String getsubSL() {
        return subjectSL;
    }
    public void setsubSL(String subSL) {
        this.subjectSL = subSL;
    }

    public String getsubEE() {
        return subjectEE;
    }
    public void setsubEE(String subEE) {
        this.subjectEE = subEE;
    }

    public String getsubIT() {
        return subjectIT;
    }
    public void setsubIT(String subIT) {
        this.subjectIT = subIT;
    }

    public String gettitEN() {
        return titleEN;
    }
    public void settitEN(String titEN) {
        this.titleEN = titEN;
    }

    public String gettitFR() {
        return titleFR;
    }
    public void settitFR(String titFR) {
        this.titleFR = titFR;
    }

    public String gettitPT() {
        return titlePT;
    }
    public void settitPT(String titPT) {
        this.titlePT = titPT;
    }

    public String gettitSL() {
        return titleSL;
    }
    public void settitSL(String titSL) {
        if(titSL.contains("Å¾")){
            titSL = titSL.replaceAll("Å¾", "ž");
        }
        if(titSL.contains("Ä\u008D")){
            titSL = titSL.replaceAll("Ä\u008D", "č");
        }
        this.titleSL = titSL;
    }

    public String gettitEE() {
        return titleEE;
    }
    public void settitEE(String titEE) {
        this.titleEE = titEE;
    }

    public String gettitIT() {
        return titleIT;
    }
    public void settitIT(String titIT) {
        this.titleIT = titIT;
    }

    public String getdesEN() {
        return descriptionEN;
    }
    public void setdesEN(String desEN) {
        this.descriptionEN = desEN;
    }

    public String getdesFR() {
        return descriptionFR;
    }
    public void setdesFR(String desFR) {
        this.descriptionFR = desFR;
    }

    public String getdesPT() {
        return descriptionPT;
    }
    public void setdesPT(String desPT) {
        this.descriptionPT = desPT;
    }

    public String getdesSL() {
        return descriptionSL;
    }
    public void setdesSL(String desSL) {
        if(desSL.contains("Å¾")){
            desSL = desSL.replaceAll("Å¾", "ž");
        }
        if(desSL.contains("Ä\u008D")){
            desSL = desSL.replaceAll("Ä\u008D", "č");
        }
        this.descriptionSL = desSL;
    }

    public String getdesEE() {
        return descriptionEE;
    }
    public void setdesEE(String desEE) {
        this.descriptionEE = desEE;
    }

    public String getdesIT() {
        return descriptionIT;
    }
    public void setdesIT(String desIT) {
        this.descriptionIT = desIT;
    }

    public long getctype() {
        return pointtype;
    }
    public void setctype(long ctype) {
        this.pointtype = ctype;
    }

    public long getstype() {
        return subjecttype;
    }
    public void setstype(long ctype) {
        this.subjecttype = ctype;
    }

    public ArrayList<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(ArrayList<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }
}
