package com.h2n.nihongorenshuu.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.h2n.nihongorenshuu.database.DatabaseManager;
import com.h2n.nihongorenshuu.entity.GrammarExplain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/10/2016.
 */

public class GrammarExplainRepo {
    public int insert(GrammarExplain ge) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(GrammarExplain.KEY_StructureId, ge.getGrammarStructureId());
        values.put(GrammarExplain.KEY_Explaination, ge.getExplaination());
        values.put(GrammarExplain.KEY_Note, ge.getNote());

        //insert
        int result = (int)db.insert(GrammarExplain.TABLE, null, values);

        //getId inserted
        int insertedId = -1;
        if(result != -1) {
            String selectQuery = "SELECT * from SQLITE_SEQUENCE WHERE name = \"" + GrammarExplain.TABLE + "\"";
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

    public int update(GrammarExplain ge) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(GrammarExplain.KEY_StructureId, ge.getGrammarStructureId());
        values.put(GrammarExplain.KEY_Explaination, ge.getExplaination());
        values.put(GrammarExplain.KEY_Note, ge.getNote());

        //update
        int result = (int)db.update(GrammarExplain.TABLE, values, GrammarExplain.KEY_Id + " = ?", new String[]{String.valueOf(ge.getId())});
        System.out.println("update success to table " + GrammarExplain.TABLE);
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public int delete(GrammarExplain ge) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //delete
        int result = (int)db.delete(GrammarExplain.TABLE, GrammarExplain.KEY_Id + " = ?", new String[]{String.valueOf(ge.getId())});
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public void deleteTable( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(GrammarExplain.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<GrammarExplain> getAllGrammarExplain(){

        List<GrammarExplain> list = new ArrayList<GrammarExplain>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + GrammarExplain.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GrammarExplain ge = new GrammarExplain();
                ge.setId(cursor.getInt(cursor.getColumnIndex(GrammarExplain.KEY_Id)));
                ge.setGrammarStructureId(cursor.getInt(cursor.getColumnIndex(GrammarExplain.KEY_StructureId)));
                ge.setExplaination(cursor.getString(cursor.getColumnIndex(GrammarExplain.KEY_Explaination)));
                ge.setNote(cursor.getString(cursor.getColumnIndex(GrammarExplain.KEY_Note)));

                list.add(ge);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + GrammarExplain.TABLE);

        return list;
    }

    public GrammarExplain getGrammarExplainById(int id){

        GrammarExplain ge = new GrammarExplain();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + GrammarExplain.TABLE + " WHERE id = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ge.setId(cursor.getInt(cursor.getColumnIndex(GrammarExplain.KEY_Id)));
                ge.setGrammarStructureId(cursor.getInt(cursor.getColumnIndex(GrammarExplain.KEY_StructureId)));
                ge.setExplaination(cursor.getString(cursor.getColumnIndex(GrammarExplain.KEY_Explaination)));
                ge.setNote(cursor.getString(cursor.getColumnIndex(GrammarExplain.KEY_Note)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + ge.toString() + " from " + GrammarExplain.TABLE);

        return ge;
    }

    public List<GrammarExplain> getGrammarExplainBySelectQuery(String condition){

        List<GrammarExplain> list = new ArrayList<GrammarExplain>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + GrammarExplain.TABLE + " " + condition;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GrammarExplain ge = new GrammarExplain();
                ge.setId(cursor.getInt(cursor.getColumnIndex(GrammarExplain.KEY_Id)));
                ge.setGrammarStructureId(cursor.getInt(cursor.getColumnIndex(GrammarExplain.KEY_StructureId)));
                ge.setExplaination(cursor.getString(cursor.getColumnIndex(GrammarExplain.KEY_Explaination)));
                ge.setNote(cursor.getString(cursor.getColumnIndex(GrammarExplain.KEY_Note)));

                list.add(ge);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + GrammarExplain.TABLE);

        return list;
    }

    public void test(){
        //get by id
        getGrammarExplainById(1);
        //insert
        GrammarExplain insertObj = new GrammarExplain(1, "a", "b");
        insert(insertObj);
        List<GrammarExplain> listInsert = getGrammarExplainBySelectQuery("SELECT * FROM " + GrammarExplain.TABLE + " ORDER BY id DESC LIMIT 1;");
        if(listInsert.size() == 1) {
            if(insertObj.compareIgnoreId(listInsert.get(0))) {
                System.out.println("insert success " + insertObj.toString() + " into " + GrammarExplain.TABLE);
            } else {
                System.out.println("have problem when insert into " + GrammarExplain.TABLE);
            }
        } else{
            System.out.println(GrammarExplain.TABLE + "have no record");
        }
        //delete
        delete(listInsert.get(0));
        listInsert = getGrammarExplainBySelectQuery("SELECT * FROM " + GrammarExplain.TABLE + " WHERE " + GrammarExplain.KEY_Id + " = " + listInsert.get(0).getId());
        if(listInsert.size() == 0) {
            System.out.println("delete success");
        }

        //update
        List<GrammarExplain> listUpdate = getGrammarExplainBySelectQuery("SELECT * FROM " + GrammarExplain.TABLE + " ORDER BY RANDOM() LIMIT 1;");
        GrammarExplain updateObj = listUpdate.get(0);
        updateObj.setExplaination("test_" + listUpdate.get(0).getExplaination());
        update(updateObj);

        listUpdate.clear();
        listUpdate = getGrammarExplainBySelectQuery("SELECT * FROM " + GrammarExplain.TABLE + " WHERE " + GrammarExplain.KEY_Id + " = " + updateObj.getId());
        if(updateObj.compare(listUpdate.get(0))) {
            System.out.println("update success " + insertObj.toString() + " into " + GrammarExplain.TABLE);
        } else {
            System.out.println("have problem when update " + GrammarExplain.TABLE);
        }

        //de-update
        updateObj.setExplaination(updateObj.getExplaination().substring(5, updateObj.getExplaination().length()));
    }
}
