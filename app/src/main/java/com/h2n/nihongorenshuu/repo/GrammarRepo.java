package com.h2n.nihongorenshuu.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.h2n.nihongorenshuu.database.DatabaseManager;
import com.h2n.nihongorenshuu.entity.Grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/8/2016.
 */

public class GrammarRepo {
    public void insert(Grammar gr) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Grammar.KEY_Level, gr.getLevel());
        values.put(Grammar.KEY_Unit, gr.getUnit());
        values.put(Grammar.KEY_Name, gr.getName());

        //insert
        db.insert(Grammar.TABLE, null, values);
        System.out.println("insert success to table " + Grammar.TABLE);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(Grammar gr) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Grammar.KEY_Level, gr.getLevel());
        values.put(Grammar.KEY_Unit, gr.getUnit());
        values.put(Grammar.KEY_Name, gr.getName());

        //update
        db.update(Grammar.TABLE, values, Grammar.KEY_Id, new String[gr.getId()]);
        System.out.println("update success to table " + Grammar.TABLE);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(Grammar gr) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //delete
        db.delete(Grammar.TABLE, Grammar.KEY_Id, new String[gr.getId()]);
    }

    public void deleteTable( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Grammar.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<Grammar> getAllGrammar(){

        List<Grammar> list = new ArrayList<Grammar>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + Grammar.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Grammar gr = new Grammar();
                gr.setId(cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Id)));
                gr.setLevel(cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Level)));
                gr.setName(cursor.getString(cursor.getColumnIndex(Grammar.KEY_Name)));
                gr.setUnit(cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Unit)));

                list.add(gr);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + Grammar.TABLE);

        return list;
    }

    public List<Grammar> getGrammarBySelectQuery(String selectQuery){

        List<Grammar> list = new ArrayList<Grammar>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Grammar gr = new Grammar();
                gr.setId(cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Id)));
                gr.setLevel(cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Level)));
                gr.setName(cursor.getString(cursor.getColumnIndex(Grammar.KEY_Name)));
                gr.setUnit(cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Unit)));

                list.add(gr);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + Grammar.TABLE);

        return list;
    }

    public void test(){
        //insert
        Grammar insertObj = new Grammar(1, "g", 1);
        insert(insertObj);
        List<Grammar> listInsert = getGrammarBySelectQuery("SELECT * FROM " + Grammar.TABLE + " ORDER BY id DESC LIMIT 1;");
        if(listInsert.size() == 1) {
            if(insertObj.compareIgnoreId(listInsert.get(0))) {
                System.out.println("insert success " + insertObj.toString() + " into " + Grammar.TABLE);
            } else {
                System.out.println("have problem when insert into " + Grammar.TABLE);
            }
        } else{
            System.out.println(Grammar.TABLE + "have no record");
        }
        //delete
        delete(listInsert.get(0));
        listInsert = getGrammarBySelectQuery("SELECT * FROM " + Grammar.TABLE + " WHERE " + Grammar.KEY_Id + " = " + listInsert.get(0).getId());
        if(listInsert.size() == 0) {
            System.out.println("delete success");
        }

        //update
        List<Grammar> listUpdate = getGrammarBySelectQuery("SELECT * FROM " + Grammar.TABLE + " ORDER BY RANDOM() LIMIT 1;");
        Grammar updateObj = listUpdate.get(0);
        updateObj.setName("test_" + listUpdate.get(0).getName());
        update(updateObj);

        listUpdate.clear();
        listUpdate = getGrammarBySelectQuery("SELECT * FROM " + Grammar.TABLE + " WHERE " + Grammar.KEY_Id + " = " + updateObj.getId());
        if(updateObj.compare(listUpdate.get(0))) {
            System.out.println("update success " + insertObj.toString() + " into " + Grammar.TABLE);
        } else {
            System.out.println("have problem when update " + Grammar.TABLE);
        }

        //de-update
        updateObj.setName(updateObj.getName().substring(5, updateObj.getName().length()));
    }
}
