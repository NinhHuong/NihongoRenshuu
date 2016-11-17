package com.h2n.nihongorenshuu.app.grammar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.database.DatabaseHelper;

import java.io.IOException;

/**
 * Created by ninhh on 11/12/2016.
 */

public class GrammarDetail extends AppCompatActivity {

    TextView gr_detail_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grammar_detail);

        Bundle b = getIntent().getExtras();
        int grammar_id = b.getInt("grammar_id");

        gr_detail_title = (TextView) findViewById(R.id.gr_detail_title);
        gr_detail_title.setText("I'm grammar Detail. My id = " + grammar_id);
    }
}
