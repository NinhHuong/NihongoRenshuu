package com.h2n.nihongorenshuu.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.h2n.nihongorenshuu.database.DatabaseManager;
import com.h2n.nihongorenshuu.entity.GrammarStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/10/2016.
 */

public class GrammarStructureRepo {
    public int insert(GrammarStructure gs) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(GrammarStructure.KEY_GrammarId, gs.getGrammarId());
        values.put(GrammarStructure.KEY_Structure, gs.getStructure());
        values.put(GrammarStructure.KEY_Note, gs.getNote() ? 1 : 0 );

        //insert
        int result = (int)db.insert(GrammarStructure.TABLE, null, values);

//        //getId inserted
//        int insertedId = -1;
//        if(result != -1) {
//            String selectQuery = "SELECT * from SQLITE_SEQUENCE WHERE name = \"" + GrammarStructure.TABLE + "\"";
//            Cursor cursor = db.rawQuery(selectQuery, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    insertedId = cursor.getInt(cursor.getColumnIndex("seq"));
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//        }
//        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public int update(GrammarStructure gs) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(GrammarStructure.KEY_GrammarId, gs.getGrammarId());
        values.put(GrammarStructure.KEY_Structure, gs.getStructure());
        values.put(GrammarStructure.KEY_Note, gs.getNote() ? 1 : 0 );

        //update
        int result = (int)db.update(GrammarStructure.TABLE, values, GrammarStructure.KEY_Id + " = ?", new String[]{String.valueOf(gs.getId())});
        System.out.println("update success to table " + GrammarStructure.TABLE);
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public int delete(GrammarStructure gs) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //delete
        int result = (int)db.delete(GrammarStructure.TABLE, GrammarStructure.KEY_Id + " = ?", new String[]{String.valueOf(gs.getId())});
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public void deleteTable( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(GrammarStructure.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<GrammarStructure> getAllGrammarStructure(){

        List<GrammarStructure> list = new ArrayList<GrammarStructure>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + GrammarStructure.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GrammarStructure gs = new GrammarStructure();
                gs.setId(cursor.getInt(cursor.getColumnIndex(GrammarStructure.KEY_Id)));
                gs.setGrammarId(cursor.getInt(cursor.getColumnIndex(GrammarStructure.KEY_GrammarId)));
                gs.setStructure(cursor.getString(cursor.getColumnIndex(GrammarStructure.KEY_Structure)));
                gs.setNote(cursor.getInt(cursor.getColumnIndex(GrammarStructure.KEY_Note)) == 1 ? true : false);

                list.add(gs);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + GrammarStructure.TABLE);

        return list;
    }

    public GrammarStructure getGrammarStructureById(int id){

        GrammarStructure gs = new GrammarStructure();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + GrammarStructure.TABLE + " WHERE id = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                gs.setId(cursor.getInt(cursor.getColumnIndex(GrammarStructure.KEY_Id)));
                gs.setGrammarId(cursor.getInt(cursor.getColumnIndex(GrammarStructure.KEY_GrammarId)));
                gs.setStructure(cursor.getString(cursor.getColumnIndex(GrammarStructure.KEY_Structure)));
                gs.setNote(cursor.getInt(cursor.getColumnIndex(GrammarStructure.KEY_Note)) == 1 ? true : false);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + gs.toString() + " from " + GrammarStructure.TABLE);

        return gs;
    }

    public List<GrammarStructure> getGrammarStructureBySelectQuery(String condition){

        List<GrammarStructure> list = new ArrayList<GrammarStructure>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + GrammarStructure.TABLE + " " + condition;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GrammarStructure gs = new GrammarStructure();
                gs.setId(cursor.getInt(cursor.getColumnIndex(GrammarStructure.KEY_Id)));
                gs.setGrammarId(cursor.getInt(cursor.getColumnIndex(GrammarStructure.KEY_GrammarId)));
                gs.setStructure(cursor.getString(cursor.getColumnIndex(GrammarStructure.KEY_Structure)));
                gs.setNote(cursor.getInt(cursor.getColumnIndex(GrammarStructure.KEY_Note)) == 1 ? true : false);

                list.add(gs);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + GrammarStructure.TABLE);

        return list;
    }

    public void test(){
        // get by id
        getGrammarStructureById(1);
        //insert
        GrammarStructure insertObj = new GrammarStructure(1, "a", true);
        insert(insertObj);
        List<GrammarStructure> listInsert = getGrammarStructureBySelectQuery("SELECT * FROM " + GrammarStructure.TABLE + " ORDER BY id DESC LIMIT 1;");
        if(listInsert.size() == 1) {
            if(insertObj.compareIgnoreId(listInsert.get(0))) {
                System.out.println("insert success " + insertObj.toString() + " into " + GrammarStructure.TABLE);
            } else {
                System.out.println("have problem when insert into " + GrammarStructure.TABLE);
            }
        } else{
            System.out.println(GrammarStructure.TABLE + "have no record");
        }
        //delete
        delete(listInsert.get(0));
        listInsert = getGrammarStructureBySelectQuery("SELECT * FROM " + GrammarStructure.TABLE + " WHERE " + GrammarStructure.KEY_Id + " = " + listInsert.get(0).getId());
        if(listInsert.size() == 0) {
            System.out.println("delete success");
        }

        //update
        List<GrammarStructure> listUpdate = getGrammarStructureBySelectQuery("SELECT * FROM " + GrammarStructure.TABLE + " ORDER BY RANDOM() LIMIT 1;");
        GrammarStructure updateObj = listUpdate.get(0);
        updateObj.setStructure("test_" + listUpdate.get(0).getStructure());
        update(updateObj);

        listUpdate.clear();
        listUpdate = getGrammarStructureBySelectQuery("SELECT * FROM " + GrammarStructure.TABLE + " WHERE " + GrammarStructure.KEY_Id + " = " + updateObj.getId());
        if(updateObj.compare(listUpdate.get(0))) {
            System.out.println("update success " + insertObj.toString() + " into " + GrammarStructure.TABLE);
        } else {
            System.out.println("have problem when update " + GrammarStructure.TABLE);
        }

        //de-update
        updateObj.setStructure(updateObj.getStructure().substring(5, updateObj.getStructure().length()));
    }
}
