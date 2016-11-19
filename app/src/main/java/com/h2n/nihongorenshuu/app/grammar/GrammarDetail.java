package com.h2n.nihongorenshuu.app.grammar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.database.DatabaseHelper;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.entity.GrammarExplain;
import com.h2n.nihongorenshuu.entity.GrammarStructure;
import com.h2n.nihongorenshuu.entity.Sentence;
import com.h2n.nihongorenshuu.extendObject.GrammarDetailContainer;
import com.h2n.nihongorenshuu.repo.GrammarExplainRepo;
import com.h2n.nihongorenshuu.repo.GrammarRepo;
import com.h2n.nihongorenshuu.repo.GrammarStructureRepo;
import com.h2n.nihongorenshuu.repo.SentenceRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ninhh on 11/12/2016.
 */

public class GrammarDetail extends AppCompatActivity {

    TextView tvGra;
    ExpandableListView explvlist;
//    GrammarDetailContainer gdContainer;
    HashMap<String, HashMap<String, List<Sentence>>> strucData = new HashMap<>();
    List<String> listStruc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grammar_detail);

        DatabaseHelper dbhelper = new DatabaseHelper();
        try {
            dbhelper.createDatabase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Bundle b = getIntent().getExtras();
//        int grammar_id = b.getInt("grammar_id");
        int grammar_id = 5;
        tvGra = (TextView) findViewById(R.id.tvGra);
        explvlist = (ExpandableListView)findViewById(R.id.parentLevel);

        GrammarRepo gr = new GrammarRepo();
        Grammar gra = gr.getGrammarById(grammar_id);
        tvGra.setText(gra.getName());

        prepairData(grammar_id);
        explvlist.setAdapter(new GrammarDetailContainer(this, strucData, listStruc));

        explvlist.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                /*Intent i = new Intent(GrammarListReview.this, GrammarDetail.class);
                Bundle b = new Bundle();
                b.putInt("grammar_id", ((Grammar) hashGrammar.get(listUnit.get(groupPosition)).get(childPosition)).getId());
                i.putExtras(b);
                startActivity(i);*/
                showMessage("test", parent.toString() + ", " + groupPosition + ", " + childPosition);
                return false;
            }
        });
    }

    private void prepairData(int graId){
        GrammarStructureRepo gsr = new GrammarStructureRepo();
        GrammarExplainRepo ger = new GrammarExplainRepo();
        SentenceRepo sr = new SentenceRepo();
        List<GrammarStructure> listStructure = gsr.getGrammarStructureBySelectQuery("WHERE " + GrammarStructure.KEY_GrammarId + " = " + graId);
        for(GrammarStructure gs : listStructure) {
            HashMap<String, List<Sentence>> mapExplain = new HashMap<>();
            List<GrammarExplain> listExplain = ger.getGrammarExplainBySelectQuery("WHERE " + GrammarExplain.KEY_StructureId + " = " + gs.getId());

            for(GrammarExplain ge : listExplain) {
                List<Sentence> listSentence = sr.getSentenceBySelectQuery("WHERE " + Sentence.KEY_GrammarExplainId + " = " + ge.getId());

                mapExplain.put(ge.getExplaination(), listSentence);
            }

            strucData.put(gs.getStructure(), mapExplain);
            listStruc.add(gs.getStructure());
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
