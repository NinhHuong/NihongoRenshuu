package com.h2n.nihongorenshuu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.h2n.nihongorenshuu.database.DatabaseHelper;
import com.h2n.nihongorenshuu.entity.SentencePart;
import com.h2n.nihongorenshuu.repo.SentencePartRepo;

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
        SentencePartRepo spr = new SentencePartRepo();
        spr.test();

    }
}
