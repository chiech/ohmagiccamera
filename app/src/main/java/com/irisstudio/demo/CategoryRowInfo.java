package com.irisstudio.demo;

public class CategoryRowInfo {
    private int CATEGORY_ID = 0;
    private String CATEGORY_NAME = "";
    private int SEQUENCE = 0;
    private int TOTAL_ITEMS = 0;

    public CategoryRowInfo(String CATEGORY_NAME, int SEQUENCE, int TOTAL_ITEMS) {
        this.CATEGORY_NAME = CATEGORY_NAME;
        this.SEQUENCE = SEQUENCE;
        this.TOTAL_ITEMS = TOTAL_ITEMS;
    }

    public int getCATEGORY_ID() {
        return this.CATEGORY_ID;
    }

    public void setCATEGORY_ID(int CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    public String getCATEGORY_NAME() {
        return this.CATEGORY_NAME;
    }

    public void setCATEGORY_NAME(String CATEGORY_NAME) {
        this.CATEGORY_NAME = CATEGORY_NAME;
    }

    public int getSEQUENCE() {
        return this.SEQUENCE;
    }

    public void setSEQUENCE(int SEQUENCE) {
        this.SEQUENCE = SEQUENCE;
    }

    public int getTOTAL_ITEMS() {
        return this.TOTAL_ITEMS;
    }

    public void setTOTAL_ITEMS(int TOTAL_ITEMS) {
        this.TOTAL_ITEMS = TOTAL_ITEMS;
    }
}
