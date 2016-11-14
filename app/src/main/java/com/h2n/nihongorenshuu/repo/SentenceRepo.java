package com.h2n.nihongorenshuu.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.h2n.nihongorenshuu.database.DatabaseManager;
import com.h2n.nihongorenshuu.entity.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/10/2016.
 */

public class SentenceRepo {
    public int insert(Sentence st) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Sentence.KEY_GrammarExplainId, st.getGrammarExplainId());
        values.put(Sentence.KEY_JpSentence, st.getJpSentence());
        values.put(Sentence.KEY_VnSentence, st.getVnSentence());

        //insert
        int result = (int)db.insert(Sentence.TABLE, null, values);

        //getId inserted
        int insertedId = -1;
        if(result != -1) {
            String selectQuery = "SELECT * from SQLITE_SEQUENCE WHERE name = \"" + Sentence.TABLE + "\"";
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

    public int update(Sentence st) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Sentence.KEY_GrammarExplainId, st.getGrammarExplainId());
        values.put(Sentence.KEY_JpSentence, st.getJpSentence());
        values.put(Sentence.KEY_VnSentence, st.getVnSentence());

        //update
        int result = (int)db.update(Sentence.TABLE, values, Sentence.KEY_Id + " = ?", new String[]{String.valueOf(st.getId())});
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public int delete(Sentence st) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //delete
        int result = (int)db.delete(Sentence.TABLE, Sentence.KEY_Id + " = ?", new String[]{String.valueOf(st.getId())});
        DatabaseManager.getInstance().closeDatabase();
        return result;
    }

    public void deleteTable( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Sentence.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<Sentence> getAllSentence(){

        List<Sentence> list = new ArrayList<Sentence>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + Sentence.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Sentence st = new Sentence();
                st.setId(cursor.getInt(cursor.getColumnIndex(Sentence.KEY_Id)));
                st.setGrammarExplainId(cursor.getInt(cursor.getColumnIndex(Sentence.KEY_GrammarExplainId)));
                st.setJpSentence(cursor.getString(cursor.getColumnIndex(Sentence.KEY_JpSentence)));
                st.setVnSentence(cursor.getString(cursor.getColumnIndex(Sentence.KEY_VnSentence)));

                list.add(st);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + Sentence.TABLE);

        return list;
    }

    public Sentence getSentenceById(int id){

        Sentence st = new Sentence();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + Sentence.TABLE + " WHERE id = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                st.setId(cursor.getInt(cursor.getColumnIndex(Sentence.KEY_Id)));
                st.setGrammarExplainId(cursor.getInt(cursor.getColumnIndex(Sentence.KEY_GrammarExplainId)));
                st.setJpSentence(cursor.getString(cursor.getColumnIndex(Sentence.KEY_JpSentence)));
                st.setVnSentence(cursor.getString(cursor.getColumnIndex(Sentence.KEY_VnSentence)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + st.toString() + " from " + Sentence.TABLE);

        return st;
    }

    public List<Sentence> getSentenceBySelectQuery(String condition){

        List<Sentence> list = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + Sentence.TABLE + " " + condition;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Sentence st = new Sentence();
                st.setId(cursor.getInt(cursor.getColumnIndex(Sentence.KEY_Id)));
                st.setGrammarExplainId(cursor.getInt(cursor.getColumnIndex(Sentence.KEY_GrammarExplainId)));
                st.setJpSentence(cursor.getString(cursor.getColumnIndex(Sentence.KEY_JpSentence)));
                st.setVnSentence(cursor.getString(cursor.getColumnIndex(Sentence.KEY_VnSentence)));

                list.add(st);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        System.out.println("get success " + list.size() + " from " + Sentence.TABLE);

        return list;
    }

    public void test(){
        // get by id
        getSentenceById(1);
        //insert
        Sentence insertObj = new Sentence(1, "a", "b");
        insert(insertObj);
        List<Sentence> listInsert = getSentenceBySelectQuery("SELECT * FROM " + Sentence.TABLE + " ORDER BY id DESC LIMIT 1;");
        if(listInsert.size() == 1) {
            if(insertObj.compareIgnoreId(listInsert.get(0))) {
                System.out.println("insert success " + insertObj.toString() + " into " + Sentence.TABLE);
            } else {
                System.out.println("have problem when insert into " + Sentence.TABLE);
            }
        } else{
            System.out.println(Sentence.TABLE + "have no record");
        }
        //delete
        delete(listInsert.get(0));
        listInsert = getSentenceBySelectQuery("SELECT * FROM " + Sentence.TABLE + " WHERE " + Sentence.KEY_Id + " = " + listInsert.get(0).getId());
        if(listInsert.size() == 0) {
            System.out.println("delete success");
        }

        //update
        List<Sentence> listUpdate = getSentenceBySelectQuery("SELECT * FROM " + Sentence.TABLE + " ORDER BY RANDOM() LIMIT 1;");
        Sentence updateObj = listUpdate.get(0);
        updateObj.setJpSentence("test_" + listUpdate.get(0).getJpSentence());
        update(updateObj);

        listUpdate.clear();
        listUpdate = getSentenceBySelectQuery("SELECT * FROM " + Sentence.TABLE + " WHERE " + Sentence.KEY_Id + " = " + updateObj.getId());
        if(updateObj.compare(listUpdate.get(0))) {
            System.out.println("update success " + insertObj.toString() + " into " + Sentence.TABLE);
        } else {
            System.out.println("have problem when update " + Sentence.TABLE);
        }

        //de-update
        updateObj.setJpSentence(updateObj.getJpSentence().substring(5, updateObj.getJpSentence().length()));
    }
}
