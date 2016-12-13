package com.h2n.nihongorenshuu.app.grammar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.database.DatabaseHelper;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.entity.GrammarExplain;
import com.h2n.nihongorenshuu.entity.GrammarStructure;
import com.h2n.nihongorenshuu.entity.Sentence;
import com.h2n.nihongorenshuu.extendObject.GrammarStructureAdapter;
import com.h2n.nihongorenshuu.repo.GrammarExplainRepo;
import com.h2n.nihongorenshuu.repo.GrammarRepo;
import com.h2n.nihongorenshuu.repo.GrammarStructureRepo;
import com.h2n.nihongorenshuu.repo.SentenceRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ninhh on 11/12/2016.
 */

public class GrammarDetail extends AppCompatActivity {

    private TextView tvGra;
    private ExpandableListView graDetail;
    private List<GrammarStructure> listStrucHeader = new ArrayList<>();
    private Map<String, List<GrammarExplain>> explainData = new HashMap<>();
    private Map<String, List<Sentence>> sentenceData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gra_detail);

        DatabaseHelper dbhelper = new DatabaseHelper();
        try {
            dbhelper.createDatabase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        tvGra = (TextView) findViewById(R.id.tvGra);
        graDetail = (ExpandableListView)findViewById(R.id.graDetail);

        Bundle b = getIntent().getExtras();
        int grammar_id = b.getInt("grammar_id");
        int sentence_id = b.getInt("sentence_id");

        GrammarRepo gr = new GrammarRepo();
        Grammar gra = gr.getGrammarById(grammar_id);
        tvGra.setText(" " + gra.getName());

        prepairData(grammar_id, sentence_id);
        GrammarStructureAdapter gsa = new GrammarStructureAdapter(this, listStrucHeader, explainData, sentenceData);
        graDetail.setAdapter(gsa);
    }

    private void prepairData(int graId, int sentence_id){
        GrammarStructureRepo gsr = new GrammarStructureRepo();
        GrammarExplainRepo ger = new GrammarExplainRepo();
        SentenceRepo sr = new SentenceRepo();
        List<GrammarStructure> listStructure = gsr.getGrammarStructureBySelectQuery("WHERE " + GrammarStructure.KEY_GrammarId + " = " + graId);
        for(GrammarStructure gs : listStructure) {
            List<GrammarExplain> listExplain = ger.getGrammarExplainBySelectQuery("WHERE " + GrammarExplain.KEY_StructureId + " = " + gs.getId());
            List<GrammarExplain> listExplainHeader = new ArrayList<>();
            for(GrammarExplain ge : listExplain) {
                List<Sentence> listSentence = new ArrayList<>();
                if(sentence_id == 0) {
                    listSentence = sr.getSentenceBySelectQuery("WHERE " + Sentence.KEY_GrammarExplainId + " = " + ge.getId());
                } else {
                    listSentence = sr.getSentenceBySelectQuery("WHERE " + Sentence.KEY_GrammarExplainId + " = " + ge.getId() +
                            " AND " + Sentence.KEY_Id + " != " + sentence_id);
                }

                sentenceData.put(String.valueOf(ge.getId()), listSentence);
                listExplainHeader.add(ge);
            }

            explainData.put(String.valueOf(gs.getId()), listExplainHeader);
            listStrucHeader.add(gs);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
