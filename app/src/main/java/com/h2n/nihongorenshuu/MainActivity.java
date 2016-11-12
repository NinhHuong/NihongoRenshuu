package com.h2n.nihongorenshuu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.h2n.nihongorenshuu.database.DatabaseHelper;
import com.h2n.nihongorenshuu.repo.SentenceRepo;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbhelper = new DatabaseHelper();
        try {
            dbhelper.createDatabase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //test get, insert, update to table SentenPart
//        SentencePartRepo spr = new SentencePartRepo();
//        spr.test();
        //test connect db Grammar
//        GrammarRepo gr = new GrammarRepo();
//        gr.test();
        //test connect db Grammar Explain
//        GrammarExplainRepo ger = new GrammarExplainRepo();
//        ger.test();
        //test connect db Grammar Explain
//        GrammarStructureRepo ger = new GrammarStructureRepo();
//        ger.test();
        // test connect to db Sentence
//        SentenceRepo sr = new SentenceRepo();
//        sr.test();

    }
}
