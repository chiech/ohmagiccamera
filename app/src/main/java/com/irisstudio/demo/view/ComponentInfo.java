package com.irisstudio.demo.view;

import android.graphics.Bitmap;
import android.net.Uri;

public class ComponentInfo {
    private Bitmap BITMAP;
    private int COMP_ID;
    private String CURCONFIG = "";
    private float CURCONFIGPROG = 1.0f;
    private int HEIGHT;
    private int ORDER;
    private float POS_X;
    private float POS_Y;
    private int RES_ID;
    private Uri RES_URI;
    private float ROTATION;
    private int TEMPLATE_ID;
    private String TYPE = "";
    private int WIDTH;
    private float Y_ROTATION;

    public ComponentInfo(){}

    public ComponentInfo(int TEMPLATE_ID, float POS_X, float POS_Y, int WIDTH, int HEIGHT, float ROTATION, float Y_ROTATION, int RES_ID, Uri res_uri, String TYPE, int ORDER) {
        this.TEMPLATE_ID = TEMPLATE_ID;
        this.POS_X = POS_X;
        this.POS_Y = POS_Y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.ROTATION = ROTATION;
        this.Y_ROTATION = Y_ROTATION;
        this.RES_ID = RES_ID;
        this.RES_URI = res_uri;
        this.TYPE = TYPE;
        this.ORDER = ORDER;
    }

    public int getCOMP_ID() {
        return this.COMP_ID;
    }

    public void setCOMP_ID(int COMP_ID) {
        this.COMP_ID = COMP_ID;
    }

    public int getTEMPLATE_ID() {
        return this.TEMPLATE_ID;
    }

    public void setTEMPLATE_ID(int TEMPLATE_ID) {
        this.TEMPLATE_ID = TEMPLATE_ID;
    }

    public float getPOS_X() {
        return this.POS_X;
    }

    public void setPOS_X(float POS_X) {
        this.POS_X = POS_X;
    }

    public float getPOS_Y() {
        return this.POS_Y;
    }

    public void setPOS_Y(float POS_Y) {
        this.POS_Y = POS_Y;
    }

    public int getRES_ID() {
        return this.RES_ID;
    }

    public void setRES_ID(int RES_ID) {
        this.RES_ID = RES_ID;
    }

    public String getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public int getORDER() {
        return this.ORDER;
    }

    public void setORDER(int ORDER) {
        this.ORDER = ORDER;
    }

    public float getROTATION() {
        return this.ROTATION;
    }

    public void setROTATION(float ROTATION) {
        this.ROTATION = ROTATION;
    }

    public int getWIDTH() {
        return this.WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return this.HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public float getY_ROTATION() {
        return this.Y_ROTATION;
    }

    public void setY_ROTATION(float y_ROTATION) {
        this.Y_ROTATION = y_ROTATION;
    }

    public Uri getRES_URI() {
        return this.RES_URI;
    }

    public void setRES_URI(Uri RES_URI) {
        this.RES_URI = RES_URI;
    }

    public Bitmap getBITMAP() {
        return this.BITMAP;
    }

    public void setBITMAP(Bitmap BITMAP) {
        this.BITMAP = BITMAP;
    }

    public String getCURCONFIG() {
        return this.CURCONFIG;
    }

    public void setCURCONFIG(String CURCONFIG) {
        this.CURCONFIG = CURCONFIG;
    }

    public float getCURCONFIGPROG() {
        return this.CURCONFIGPROG;
    }

    public void setCURCONFIGPROG(float CURCONFIGPROG) {
        this.CURCONFIGPROG = CURCONFIGPROG;
    }
}
