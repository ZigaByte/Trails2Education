package eu.trails2education.trails.database;

import java.io.Serializable;

/**
 * Created by svetl on 6. 12. 2017.
 */

public class Multimedia implements Serializable {

    public static final String TAG = "Multimedia";
    private static final long serialVersionUID = -7406082437623008161L;

    private long cIdM;
    private long multitype;
    private String multimediaTypeEN;
    private String multimediaTypeFR;
    private String multimediaTypePT;
    private String multimediaTypeSL;
    private String multimediaTypeEE;
    private String multimediaTypeIT;
    private String elementURL;
    private Content mContent;

    public Multimedia() {

    }

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

    public long getcIdM() {
        return cIdM;
    }
    public void setIdM(long mIdM) {
        this.cIdM = mIdM;
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

    public String gemulFR() {
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

    public Content getContent() {
        return mContent;
    }
    public void setContent(Content mC) {
        this.mContent = mC;
    }

}
