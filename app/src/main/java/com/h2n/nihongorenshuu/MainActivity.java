package com.h2n.nihongorenshuu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.h2n.nihongorenshuu.database.DatabaseHelper;
import com.h2n.nihongorenshuu.entity.GrammarExplain;
import com.h2n.nihongorenshuu.entity.GrammarStructure;
import com.h2n.nihongorenshuu.entity.Sentence;
import com.h2n.nihongorenshuu.repo.GrammarExplainRepo;
import com.h2n.nihongorenshuu.repo.GrammarRepo;
import com.h2n.nihongorenshuu.repo.GrammarStructureRepo;
import com.h2n.nihongorenshuu.repo.HistoryRepo;
import com.h2n.nihongorenshuu.repo.SentencePartRepo;
import com.h2n.nihongorenshuu.repo.SentenceRepo;

import java.io.IOException;
import java.util.List;

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

//        //test get, insert, update to table SentenPart
//        SentencePartRepo spr = new SentencePartRepo();
//        spr.test();
//        //test connect db Grammar
//        GrammarRepo gr = new GrammarRepo();
//        gr.test();
//        //test connect db Grammar Explain
//        GrammarExplainRepo ger = new GrammarExplainRepo();
//        ger.test();
//        //test connect db Grammar Structure
//        GrammarStructureRepo gsr = new GrammarStructureRepo();
//        gsr.test();
//        // test connect to db Sentence
        SentenceRepo sr = new SentenceRepo();
//        sr.test();
        List<Sentence> l = sr.getSentenceBySelectQuery("where " + Sentence.KEY_Id + " = 20");
        for( Sentence s : l) {
            s.toString();
        }
//        // test connect to db History
//        HistoryRepo hr = new HistoryRepo();
//        hr.test();

    }
}
