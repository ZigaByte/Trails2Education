package eu.trails2education.trails.database;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by svetl on 6. 12. 2017.
 */

public class Multimedia implements Serializable {

    public static final String TAG = "Multimedia";
    private static final long serialVersionUID = -7406082437623008161L;

    public class DisplayValues{
        public String multimediaType;
    }

    private long cId;
    private long id;
    private long multitype;
    private String multimediaTypeEN;
    private String multimediaTypeFR;
    private String multimediaTypePT;
    private String multimediaTypeSL;
    private String multimediaTypeEE;
    private String multimediaTypeIT;
    private String elementURL;

    public Multimedia() {}

    public Multimedia(long ctype, String mulEN, String mulFR, String mulPT, String mulSL, String mulEE, String mulIT,
                   String eURL) {
        this.multitype = ctype;
        this.multimediaTypeEN = mulEN;
        this.multimediaTypeFR = mulFR;
        this.multimediaTypePT = mulPT;
        this.multimediaTypeSL = mulSL;
        this.multimediaTypeEE = mulEE;
        this.multimediaTypeIT = mulIT;
        this.elementURL = eURL;

    }

    public long getCId() {
        return cId;
    }
    public void setCId(long mIdM) {
        this.cId = mIdM;
    }

    public long getId() {
        return id;
    }
    public void setId(long mId) {
        this.id = mId;
    }

    public long getctype() {
        return multitype;
    }
    public void setctype(long ctype) {
        this.multitype = ctype;
    }

    public String getmulEN() {
        return multimediaTypeEN;
    }
    public void setmulEN(String mulEN) {
        this.multimediaTypeEN = mulEN;
    }

    public String getmulFR() {
        return multimediaTypeFR;
    }
    public void setmulFR(String mulFR) {
        this.multimediaTypeFR = mulFR;
    }

    public String getmulPT() {
        return multimediaTypePT;
    }
    public void setmulPT(String mulPT) {
        this.multimediaTypePT = mulPT;
    }

    public String getmulSL() {
        return multimediaTypeSL;
    }
    public void setmulSL(String mulSL) {
        this.multimediaTypeSL = mulSL;
    }

    public String getmulEE() {
        return multimediaTypeEE;
    }
    public void setmulEE(String mulEE) {
        this.multimediaTypeEE = mulEE;
    }

    public String getmulIT() {
        return multimediaTypeIT;
    }
    public void setmulIT(String mulIT) {
        this.multimediaTypeIT = mulIT;
    }

    public String geteURL() {
        return elementURL;
    }
    public void seteURL(String eURL) {
        this.elementURL = eURL;
    }

    public Multimedia.DisplayValues getDisplayValues(Locale locale){
        Multimedia.DisplayValues values = new Multimedia.DisplayValues();

        switch (locale.getLanguage()){
            case "fr":
                values.multimediaType = getmulFR();
                break;
            case "pt":
                values.multimediaType = getmulPT();
                break;
            case "sl":
                values.multimediaType = getmulSL();
                break;
            case "et":
                values.multimediaType = getmulEE();
                break;
            case "it":
                values.multimediaType = getmulIT();
                break;

            default:
                values.multimediaType = getmulEN();
                break;
        }

        return values;
    }

}
