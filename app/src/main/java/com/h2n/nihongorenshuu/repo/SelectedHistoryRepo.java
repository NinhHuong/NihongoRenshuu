package com.h2n.nihongorenshuu.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.h2n.nihongorenshuu.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/8/2016.
 */

public class SelectedHistoryRepo {
    public static final String TABLE = "SelectedHistory";
    public static final String KEY_SelectedGrammarId = "selectedGrammarId";
    public static final String KEY_IsVn2Jp = "isVn2Jp";

    public int insertSeletedGrammar(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SelectedGrammarId, id);

        //insert
        int result = (int)db.insert(TABLE, null, values);

        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public int insertIsVn2Jp(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IsVn2Jp, id);

        //insert
        int result = (int)db.insert(TABLE, null, values);

        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    /*public int update(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        //update
        int result = (int)db.update(TABLE, values, KEY_Id + " = ?", new String[]{String.valueOf(id)});
        System.out.println("update success to table " + TABLE);
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }*/

    /*public int delete(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //delete
        int result = (int)db.delete(TABLE, KEY_Id + " = ?", new String[]{String.valueOf(id)});
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }*/

    public int deleteAllRecord() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //delete
        int result = (int)db.delete(TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public void deleteTable( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<Integer> getAllSelectedGrammar(){

        List<Integer> list = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + KEY_SelectedGrammarId + " FROM " + TABLE + " WHERE " + KEY_SelectedGrammarId + " IS NOT NULL";

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int gr = cursor.getInt(cursor.getColumnIndex(KEY_SelectedGrammarId));

                    list.add(gr);
                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        DatabaseManager.getInstance().closeDatabase();

        return list;
    }

    public int getIsVn2Jp(){

        int isVn2Jp = 0;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + KEY_IsVn2Jp + " FROM " + TABLE + " WHERE " + KEY_IsVn2Jp + " IS NOT NULL";

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    isVn2Jp = cursor.getInt(cursor.getColumnIndex(KEY_IsVn2Jp));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        DatabaseManager.getInstance().closeDatabase();

        return isVn2Jp;
    }

    /*public int getSelectedGrammarById(int id){

        int gr = 0;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + TABLE + " WHERE id = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                gr = (cursor.getInt(cursor.getColumnIndex(KEY_Id)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + String.valueOf(gr) + " from " + TABLE);

        return gr;
    }*/

    /*public List<Integer> getSelectedGrammarBySelectQuery(String condition){

        List<Integer> list = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + TABLE + " " + condition;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int gr = (cursor.getInt(cursor.getColumnIndex(KEY_Id)));

                list.add(gr);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + TABLE);

        return list;
    }*/

    /*public void test(){

    }*/
}
