package com.h2n.nihongorenshuu.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.h2n.nihongorenshuu.database.DatabaseManager;
import com.h2n.nihongorenshuu.entity.SentencePart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/6/2016.
 */

public class SentencePartRepo {
    private SentencePart sentencePart;

    public SentencePartRepo() {
        sentencePart = new SentencePart();
    }

    public void insert(SentencePart sp) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(SentencePart.KEY_SentenceId, sp.getSentenceId());
        values.put(SentencePart.KEY_PartContent, sp.getPartContent());
        values.put(SentencePart.KEY_PartIndex, sp.getPartIndex());

        //insert
        db.insert(SentencePart.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(SentencePart sp) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(SentencePart.KEY_Id, sp.getId());
        values.put(SentencePart.KEY_SentenceId, sp.getSentenceId());
        values.put(SentencePart.KEY_PartContent, sp.getPartContent());
        values.put(SentencePart.KEY_PartIndex, sp.getPartIndex());

        //update
//        db.insert(SentencePart.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteTable( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(SentencePart.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<SentencePart> getAllSentencePart(){

        List<SentencePart> sentencePartList = new ArrayList<SentencePart>();

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

                sentencePartList.add(sp);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return sentencePartList;

    }
}
