package com.h2n.nihongorenshuu.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.h2n.nihongorenshuu.database.DatabaseManager;
import com.h2n.nihongorenshuu.entity.History;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/11/2016.
 */

public class HistoryRepo {
    public int insert(History his) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(History.KEY_SentenceId, his.getSentenceId());
        values.put(History.KEY_UserTrans, his.getUserTrans());
        if(his.isGame()) {
            values.put(History.KEY_IsGame, 1);
        } else {
            values.put(History.KEY_IsGame, 0);
        }
        if(his.isCorrect()) {
            values.put(History.KEY_IsCorrect, 1);
        } else {
            values.put(History.KEY_IsCorrect, 0);
        }
        if(his.isContinue()) {
            values.put(History.KEY_IsContinue, 1);
        } else {
            values.put(History.KEY_IsContinue, 0);
        }
        //insert
        int result = (int)db.insert(History.TABLE, null, values);
        System.out.println("insert success " + his.toString() + " into " + History.TABLE);

        //getId inserted
        int insertedId = -1;
        if(result != -1) {
            String selectQuery = "SELECT * from SQLITE_SEQUENCE WHERE name = \"" + History.TABLE + "\"";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    insertedId = cursor.getInt(cursor.getColumnIndex("seq"));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        DatabaseManager.getInstance().closeDatabase();
        return insertedId;
    }

    public int update(History his) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(History.KEY_SentenceId, his.getSentenceId());
        values.put(History.KEY_UserTrans, his.getUserTrans());
        values.put(History.KEY_Time, his.getTime());
        values.put(History.KEY_IsGame, his.isGame());
        values.put(History.KEY_IsCorrect, his.isCorrect());
        values.put(History.KEY_IsContinue, his.isContinue());
        //update
        int result = (int)db.update(History.TABLE, values, History.KEY_Id + " = ?", new String[]{String.valueOf(his.getId())});
        System.out.println("update success to table " + History.TABLE);
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public void updateContinueRecord() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        String query = "UPDATE " + History.TABLE + " SET " + History.KEY_IsContinue + " = 0 WHERE " + History.KEY_IsContinue + " = 1";
        db.execSQL(query);

        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(History his) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //delete
        db.delete(History.TABLE, History.KEY_Id + " = ?", new String[]{String.valueOf(his.getId())});
    }

    public void deleteTable( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(History.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<History> getAllHistory(){

        List<History> list = new ArrayList<History>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + History.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History his = new History();
                his.setId(cursor.getInt(cursor.getColumnIndex(History.KEY_Id)));
                his.setSentenceId(cursor.getInt(cursor.getColumnIndex(History.KEY_SentenceId)));
                his.setUserTrans(cursor.getString(cursor.getColumnIndex(History.KEY_UserTrans)));
                his.setTime(cursor.getString(cursor.getColumnIndex(History.KEY_Time)));
                his.setGame(cursor.getInt(cursor.getColumnIndex(History.KEY_Id)) == 1 ? true : false);
                his.setCorrect(cursor.getInt(cursor.getColumnIndex(History.KEY_IsCorrect)) == 1 ? true : false);
                his.setContinue(cursor.getInt(cursor.getColumnIndex(History.KEY_IsContinue)) == 1 ? true : false);

                list.add(his);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + History.TABLE);

        return list;
    }

    public History getHistoryById(int id){

        History his = new History();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + History.TABLE + " WHERE id = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                his.setId(cursor.getInt(cursor.getColumnIndex(History.KEY_Id)));
                his.setSentenceId(cursor.getInt(cursor.getColumnIndex(History.KEY_SentenceId)));
                his.setUserTrans(cursor.getString(cursor.getColumnIndex(History.KEY_UserTrans)));
                his.setTime(cursor.getString(cursor.getColumnIndex(History.KEY_Time)));
                his.setGame(cursor.getInt(cursor.getColumnIndex(History.KEY_Id)) == 1 ? true : false);
                his.setCorrect(cursor.getInt(cursor.getColumnIndex(History.KEY_IsCorrect)) == 1 ? true : false);
                his.setContinue(cursor.getInt(cursor.getColumnIndex(History.KEY_IsContinue)) == 1 ? true : false);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + his.toString() + " from " + History.TABLE);

        return his;
    }

    public List<History> getHistoryBySelectQuery(String condition){

        List<History> list = new ArrayList<History>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String selectQuery = "SELECT * FROM " + History.TABLE + " " + condition;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History his = new History();
                his.setId(cursor.getInt(cursor.getColumnIndex(History.KEY_Id)));
                his.setSentenceId(cursor.getInt(cursor.getColumnIndex(History.KEY_SentenceId)));
                his.setUserTrans(cursor.getString(cursor.getColumnIndex(History.KEY_UserTrans)));
                his.setTime(cursor.getString(cursor.getColumnIndex(History.KEY_Time)));
                his.setGame(cursor.getInt(cursor.getColumnIndex(History.KEY_Id)) == 1 ? true : false);
                his.setCorrect(cursor.getInt(cursor.getColumnIndex(History.KEY_IsCorrect)) == 1 ? true : false);
                his.setContinue(cursor.getInt(cursor.getColumnIndex(History.KEY_IsContinue)) == 1 ? true : false);

                list.add(his);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + History.TABLE);

        return list;
    }

    public void test(){
        // get by id
        getHistoryById(1);
        //insert
        History insertObj = new History(1, "a", "b", true, true, true);
        insert(insertObj);
        List<History> listInsert = getHistoryBySelectQuery("SELECT * FROM " + History.TABLE + " ORDER BY id DESC LIMIT 1;");
        if(listInsert.size() == 1) {
            if(insertObj.compareIgnoreId(listInsert.get(0))) {
                System.out.println("insert success " + insertObj.toString() + " into " + History.TABLE);
            } else {
                System.out.println("have problem when insert into " + History.TABLE);
            }
        } else{
            System.out.println(History.TABLE + "have no record");
        }
        //delete
        delete(listInsert.get(0));
        listInsert = getHistoryBySelectQuery("SELECT * FROM " + History.TABLE + " WHERE " + History.KEY_Id + " = " + listInsert.get(0).getId());
        if(listInsert.size() == 0) {
            System.out.println("delete success");
        }

        //update
        List<History> listUpdate = getHistoryBySelectQuery("SELECT * FROM " + History.TABLE + " ORDER BY RANDOM() LIMIT 1;");
        History updateObj = listUpdate.get(0);
        updateObj.setUserTrans("test_" + listUpdate.get(0).getUserTrans());
        update(updateObj);

        listUpdate.clear();
        listUpdate = getHistoryBySelectQuery("SELECT * FROM " + History.TABLE + " WHERE " + History.KEY_Id + " = " + updateObj.getId());
        if(updateObj.compare(listUpdate.get(0))) {
            System.out.println("update success " + insertObj.toString() + " into " + History.TABLE);
        } else {
            System.out.println("have problem when update " + History.TABLE);
        }

        //de-update
        updateObj.setUserTrans(updateObj.getUserTrans().substring(5, updateObj.getUserTrans().length()));
    }
}
