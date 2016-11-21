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
    public int insert(Grammar gr) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Grammar.KEY_Level, gr.getLevel());
        values.put(Grammar.KEY_Unit, gr.getUnit());
        values.put(Grammar.KEY_Name, gr.getName());

        //insert
        int result = (int)db.insert(Grammar.TABLE, null, values);

        //getId inserted
        int insertedId = -1;
        if(result != -1) {
            String selectQuery = "SELECT * from SQLITE_SEQUENCE WHERE name = \"" + Grammar.TABLE + "\"";
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

    public int update(Grammar gr) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Grammar.KEY_Level, gr.getLevel());
        values.put(Grammar.KEY_Unit, gr.getUnit());
        values.put(Grammar.KEY_Name, gr.getName());

        //update
        int result = (int)db.update(Grammar.TABLE, values, Grammar.KEY_Id + " = ?", new String[]{String.valueOf(gr.getId())});
        System.out.println("update success to table " + Grammar.TABLE);
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public int delete(Grammar gr) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //delete
        int result = (int)db.delete(Grammar.TABLE, Grammar.KEY_Id + " = ?", new String[]{String.valueOf(gr.getId())});
        DatabaseManager.getInstance().closeDatabase();
        return result;
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

    public Grammar getGrammarById(int id){

        Grammar gr = new Grammar();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + Grammar.TABLE + " WHERE id = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                gr.setId(cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Id)));
                gr.setLevel(cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Level)));
                gr.setName(cursor.getString(cursor.getColumnIndex(Grammar.KEY_Name)));
                gr.setUnit(cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Unit)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + gr.toString() + " from " + Grammar.TABLE);

        return gr;
    }

    public List<Grammar> getGrammarBySelectQuery(String condition){

        List<Grammar> list = new ArrayList<Grammar>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + Grammar.TABLE + " " + condition;
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

    public List<Grammar> getGrammarByQuery(String query){

        List<Grammar> list = new ArrayList<Grammar>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(query, null);
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
        // get by id
        getGrammarById(1);
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
