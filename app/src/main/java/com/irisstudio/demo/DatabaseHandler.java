package com.irisstudio.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String CATEGORY_MASTER = "CATEGORY_MASTER";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String COST = "COST";
    private static final String CREATE_TABLE_CATEGORY_MASTER = "CREATE TABLE CATEGORY_MASTER(CATEGORY_ID INTEGER PRIMARY KEY,CATEGORY_NAME TEXT,SEQUENCE INTEGER,TOTAL_ITEMS TEXT)";
    private static final String CREATE_TABLE_STICKERS_INFO = "CREATE TABLE STICKERS_INFO(STICKER_ID INTEGER PRIMARY KEY,STICKER_NAME TEXT,MAIN_CATEGORY TEXT,SUB_CATEGORY TEXT,IS_HOT TEXT,COST TEXT,THUMB_PATH TEXT,IMAGE_PATH TEXT,THUMB_SERVER_PATH TEXT,IMAGE_SERVER_PATH TEXT,IS_DOWNLOADED TEXT,SEQUENCE INTEGER,IS_UPDATED TEXT)";
    private static final String DATABASE_NAME = "STICKERS_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String IMAGE_PATH = "IMAGE_PATH";
    private static final String IMAGE_SERVER_PATH = "IMAGE_SERVER_PATH";
    private static final String IS_DOWNLOADED = "IS_DOWNLOADED";
    private static final String IS_HOT = "IS_HOT";
    private static final String IS_UPDATED = "IS_UPDATED";
    private static final String MAIN_CATEGORY = "MAIN_CATEGORY";
    private static final String SEQUENCE = "SEQUENCE";
    private static final String STICKERS_INFO = "STICKERS_INFO";
    private static final String STICKER_ID = "STICKER_ID";
    private static final String STICKER_NAME = "STICKER_NAME";
    private static final String SUB_CATEGORY = "SUB_CATEGORY";
    private static final String THUMB_PATH = "THUMB_PATH";
    private static final String THUMB_SERVER_PATH = "THUMB_SERVER_PATH";
    private static final String TOTAL_ITEMS = "TOTAL_ITEMS";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHandler getDbHandler(Context context) {
        return new DatabaseHandler(context);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORY_MASTER);
        db.execSQL(CREATE_TABLE_STICKERS_INFO);
        Log.i("testing", "Database Created");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CATEGORY_MASTER");
        db.execSQL("DROP TABLE IF EXISTS STICKERS_INFO");
        onCreate(db);
    }

    public void insertCategoryMasterRow(CategoryRowInfo crInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, crInfo.getCATEGORY_NAME());
        values.put(SEQUENCE, Integer.valueOf(crInfo.getSEQUENCE()));
        values.put(TOTAL_ITEMS, Integer.valueOf(crInfo.getTOTAL_ITEMS()));
        db.insert(CATEGORY_MASTER, null, values);
        db.close();
    }

    public long insertStickerInfoRow(StickerInfo info) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STICKER_NAME, info.getSTICKER_NAME());
        values.put(MAIN_CATEGORY, info.getMAIN_CATEGORY());
        values.put(SUB_CATEGORY, info.getSUB_CATEGORY());
        values.put(IS_HOT, info.IS_HOT());
        values.put(COST, Integer.valueOf(info.getCOST()));
        values.put(THUMB_PATH, info.getTHUMB_PATH());
        values.put(IMAGE_PATH, info.getIMAGE_PATH());
        values.put(THUMB_SERVER_PATH, info.getTHUMB_SERVER_PATH());
        values.put(IMAGE_SERVER_PATH, info.getIMAGE_SERVER_PATH());
        values.put(IS_DOWNLOADED, info.IS_DOWNLOADED());
        values.put(SEQUENCE, Integer.valueOf(info.getSEQUENCE()));
        values.put(IS_UPDATED, info.getIS_UPDATED());
        long insertId = db.insert(STICKERS_INFO, null, values);
        db.close();
        return insertId;
    }

    public ArrayList<CategoryRowInfo> getCategoriesList() {
        ArrayList<CategoryRowInfo> categoriesList = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM CATEGORY_MASTER ORDER BY SEQUENCE ASC;", null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
            return categoriesList;
        }
        do {
            categoriesList.add(new CategoryRowInfo(cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
        } while (cursor.moveToNext());
        db.close();
        return categoriesList;
    }

    public ArrayList<StickerInfo> getStickerInfoList(String category) {
        ArrayList<StickerInfo> componentInfoList = new ArrayList();
        String query = "SELECT * FROM STICKERS_INFO WHERE MAIN_CATEGORY='" + category + "' AND " + IS_DOWNLOADED + " = '" + String.valueOf(true) + "' ORDER BY " + SEQUENCE + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
            return componentInfoList;
        }
        do {
            StickerInfo values = new StickerInfo();
            values.setSTICKER_ID(cursor.getLong(0));
            values.setSTICKER_NAME(cursor.getString(1));
            values.setMAIN_CATEGORY(cursor.getString(2));
            values.setSUB_CATEGORY(cursor.getString(3));
            values.setIS_HOT(cursor.getString(4));
            values.setCOST(cursor.getInt(5));
            values.setTHUMB_PATH(cursor.getString(6));
            values.setIMAGE_PATH(cursor.getString(7));
            values.setTHUMB_SERVER_PATH(cursor.getString(8));
            values.setIMAGE_SERVER_PATH(cursor.getString(9));
            values.setIS_DOWNLOADED(cursor.getString(10));
            values.setSEQUENCE(cursor.getInt(11));
            values.setIS_UPDATED(cursor.getString(12));
            componentInfoList.add(values);
        } while (cursor.moveToNext());
        db.close();
        return componentInfoList;
    }

    public ArrayList<StickerInfo> getStickerInfoList1(String category, int seq) {
        ArrayList<StickerInfo> componentInfoList = new ArrayList();
        String query = "SELECT * FROM STICKERS_INFO WHERE IS_UPDATED='" + String.valueOf(true) + "' AND " + MAIN_CATEGORY + "='" + category + "' AND " + SEQUENCE + " > '" + String.valueOf(seq) + "' ORDER BY " + SEQUENCE + " ASC;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
            return componentInfoList;
        }
        do {
            StickerInfo values = new StickerInfo();
            values.setSTICKER_ID(cursor.getLong(0));
            values.setSTICKER_NAME(cursor.getString(1));
            values.setMAIN_CATEGORY(cursor.getString(2));
            values.setSUB_CATEGORY(cursor.getString(3));
            values.setIS_HOT(cursor.getString(4));
            values.setCOST(cursor.getInt(5));
            values.setTHUMB_PATH(cursor.getString(6));
            values.setIMAGE_PATH(cursor.getString(7));
            values.setTHUMB_SERVER_PATH(cursor.getString(8));
            values.setIMAGE_SERVER_PATH(cursor.getString(9));
            values.setIS_DOWNLOADED(cursor.getString(10));
            values.setSEQUENCE(cursor.getInt(11));
            values.setIS_UPDATED(cursor.getString(12));
            componentInfoList.add(values);
        } while (cursor.moveToNext());
        db.close();
        return componentInfoList;
    }

    public ArrayList<StickerInfo> getStickerInfoByName(String stickerName) {
        ArrayList<StickerInfo> componentInfoList = new ArrayList();
        String query = "SELECT * FROM STICKERS_INFO WHERE STICKER_NAME='" + stickerName + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
            return componentInfoList;
        }
        do {
            StickerInfo values = new StickerInfo();
            values.setSTICKER_ID(cursor.getLong(0));
            values.setSTICKER_NAME(cursor.getString(1));
            values.setMAIN_CATEGORY(cursor.getString(2));
            values.setSUB_CATEGORY(cursor.getString(3));
            values.setIS_HOT(cursor.getString(4));
            values.setCOST(cursor.getInt(5));
            values.setTHUMB_PATH(cursor.getString(6));
            values.setIMAGE_PATH(cursor.getString(7));
            values.setTHUMB_SERVER_PATH(cursor.getString(8));
            values.setIMAGE_SERVER_PATH(cursor.getString(9));
            values.setIS_DOWNLOADED(cursor.getString(10));
            values.setSEQUENCE(cursor.getInt(11));
            values.setIS_UPDATED(cursor.getString(12));
            componentInfoList.add(values);
        } while (cursor.moveToNext());
        db.close();
        return componentInfoList;
    }

    public void updateCategoryRow(String categoryName, int sequence, int totalItems) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SEQUENCE, Integer.valueOf(sequence));
        cv.put(TOTAL_ITEMS, Integer.valueOf(totalItems));
        db.update(CATEGORY_MASTER, cv, "CATEGORY_NAME= ?", new String[]{categoryName});
        db.close();
    }

    public void updateStickerInfoRow(long rowId, int sequence) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SEQUENCE, Integer.valueOf(sequence));
        cv.put(IS_UPDATED, String.valueOf(true));
        db.update(STICKERS_INFO, cv, "STICKER_ID= ?", new String[]{String.valueOf(rowId)});
        db.close();
    }

    public void disableAllRow() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(IS_UPDATED, String.valueOf(false));
        db.update(STICKERS_INFO, cv, "SEQUENCE > ?", new String[]{"0"});
        db.close();
    }

    public void updateStickerImagePath(long rowId, String imagePath, boolean bool) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(IMAGE_PATH, imagePath);
        cv.put(IS_DOWNLOADED, String.valueOf(bool));
        db.update(STICKERS_INFO, cv, "STICKER_ID= ?", new String[]{String.valueOf(rowId)});
        db.close();
    }

    public void updateStickerThumbPath(long rowId, String thumbPath) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(THUMB_PATH, thumbPath);
        db.update(STICKERS_INFO, cv, "STICKER_ID= ?", new String[]{String.valueOf(rowId)});
        db.close();
    }

    public boolean deleteCategoryMasterRow(String categoryName) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM CATEGORY_MASTER WHERE CATEGORY_NAME='" + categoryName + "'");
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStickerInfoRow(int rowId) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM STICKERS_INFO WHERE STICKER_ID='" + rowId + "'");
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
