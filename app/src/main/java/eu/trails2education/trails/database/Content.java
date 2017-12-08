package eu.trails2education.trails.database;

import java.io.Serializable;

/**
 * Created by svetl on 6. 12. 2017.
 */

public class Content implements Serializable {

    public static final String TAG = "Content";
    private static final long serialVersionUID = -7406082437623008161L;

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
    private InterestPoint mInterestPoint;

    public Content() {

    }

    public Content(long ctype, String subEN, String subFR, String subPT, String subSL, String subEE, String subIT,
                   String titEN, String titFR, String titPT, String titSL, String titEE, String titIT,
                   String desEN, String desFR, String desPT, String desSL, String desEE, String desIT) {
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

    public InterestPoint getInterestPoint() {
        return mInterestPoint;
    }
    public void setInterestPoint(InterestPoint mIP) {
        this.mInterestPoint = mIP;
    }


}
