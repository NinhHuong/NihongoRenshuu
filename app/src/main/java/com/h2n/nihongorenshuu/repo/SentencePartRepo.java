package com.h2n.nihongorenshuu.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.h2n.nihongorenshuu.database.DatabaseManager;
import com.h2n.nihongorenshuu.entity.SentencePart;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/6/2016.
 */

public class SentencePartRepo {

    public int insert(SentencePart sp) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(SentencePart.KEY_SentenceId, sp.getSentenceId());
        values.put(SentencePart.KEY_PartContent, sp.getPartContent());
        values.put(SentencePart.KEY_PartIndex, sp.getPartIndex());

        //insert
        int result = (int)db.insert(SentencePart.TABLE, null, values);

        //getId inserted
        int insertedId = -1;
        if(result != -1) {
            String selectQuery = "SELECT * from SQLITE_SEQUENCE WHERE name = \"" + SentencePart.TABLE + "\"";
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

    public int update(SentencePart sp) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(SentencePart.KEY_SentenceId, sp.getSentenceId());
        values.put(SentencePart.KEY_PartContent, sp.getPartContent());
        values.put(SentencePart.KEY_PartIndex, sp.getPartIndex());

        //update
        int result = (int)db.update(SentencePart.TABLE, values, SentencePart.KEY_Id + " = ?", new String[]{String.valueOf(sp.getId())});
        System.out.println("update success to table " + SentencePart.TABLE);
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public int delete(SentencePart sp) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //delete
        int result = (int)db.delete(SentencePart.TABLE, SentencePart.KEY_Id + " = ?", new String[]{String.valueOf(sp.getId())});
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public void deleteTable( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(SentencePart.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<SentencePart> getAllSentencePart(){

        List<SentencePart> list = new ArrayList<SentencePart>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + SentencePart.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SentencePart sp = new SentencePart();
                sp.setId(cursor.getInt(cursor.getColumnIndex(SentencePart.KEY_Id)));
                sp.setSentenceId(cursor.getInt(cursor.getColumnIndex(SentencePart.KEY_SentenceId)));
                sp.setPartContent(cursor.getString(cursor.getColumnIndex(SentencePart.KEY_PartContent)));
                sp.setPartIndex(cursor.getInt(cursor.getColumnIndex(SentencePart.KEY_PartIndex)));

                list.add(sp);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + SentencePart.TABLE);

        return list;
    }

    public SentencePart getSentencePartById(int id){


        SentencePart sp = new SentencePart();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + SentencePart.TABLE + " WHERE id = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sp.setId(cursor.getInt(cursor.getColumnIndex(SentencePart.KEY_Id)));
                sp.setSentenceId(cursor.getInt(cursor.getColumnIndex(SentencePart.KEY_SentenceId)));
                sp.setPartContent(cursor.getString(cursor.getColumnIndex(SentencePart.KEY_PartContent)));
                sp.setPartIndex(cursor.getInt(cursor.getColumnIndex(SentencePart.KEY_PartIndex)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return sp;
    }

    public List<SentencePart> getSentencePartBySelectQuery(String condition){

        List<SentencePart> list = new ArrayList<SentencePart>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + SentencePart.TABLE + " " + condition;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SentencePart sp = new SentencePart();
                sp.setId(cursor.getInt(cursor.getColumnIndex(SentencePart.KEY_Id)));
                sp.setSentenceId(cursor.getInt(cursor.getColumnIndex(SentencePart.KEY_SentenceId)));
                sp.setPartContent(cursor.getString(cursor.getColumnIndex(SentencePart.KEY_PartContent)));
                sp.setPartIndex(cursor.getInt(cursor.getColumnIndex(SentencePart.KEY_PartIndex)));

                list.add(sp);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + SentencePart.TABLE);

        return list;
    }

    public void test(){
        //get by id
        getSentencePartById(1);
        //insert
        SentencePart insertObj = new SentencePart(1, "a", 1);
        insert(insertObj);
        List<SentencePart> listInsert = getSentencePartBySelectQuery("SELECT * FROM " + SentencePart.TABLE + " ORDER BY id DESC LIMIT 1;");
        if(listInsert.size() == 1) {
            if(insertObj.compareIgnoreId(listInsert.get(0))) {
                System.out.println("insert success " + insertObj.toString() + " into " + SentencePart.TABLE);
            } else {
                System.out.println("have problem when insert into " + SentencePart.TABLE);
            }
        } else{
            System.out.println(SentencePart.TABLE + "have no record");
        }
        //delete
        delete(listInsert.get(0));
        listInsert = getSentencePartBySelectQuery("SELECT * FROM " + SentencePart.TABLE + " WHERE " + SentencePart.KEY_Id + " = " + listInsert.get(0).getId());
        if(listInsert.size() == 0) {
            System.out.println("delete success");
        }

        //update
        List<SentencePart> listUpdate = getSentencePartBySelectQuery("SELECT * FROM " + SentencePart.TABLE + " ORDER BY RANDOM() LIMIT 1;");
        SentencePart updateObj = listUpdate.get(0);
        updateObj.setPartContent("test_" + listUpdate.get(0).getPartContent());
        update(updateObj);

        listUpdate.clear();
        listUpdate = getSentencePartBySelectQuery("SELECT * FROM " + SentencePart.TABLE + " WHERE " + SentencePart.KEY_Id + " = " + updateObj.getId());
        if(updateObj.compare(listUpdate.get(0))) {
            System.out.println("update success " + insertObj.toString() + " into " + SentencePart.TABLE);
        } else {
            System.out.println("have problem when update " + SentencePart.TABLE);
        }

        //de-update
        updateObj.setPartContent(updateObj.getPartContent().substring(5, updateObj.getPartContent().length()));
    }
}
