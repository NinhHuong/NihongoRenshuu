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
    /*private Button btnBack;*/
    private ExpandableListView graDetail;
    private List<String> listStrucHeader = new ArrayList<>();
    private Map<String, List<String>> explainData = new HashMap<>();
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

        tvGra = (TextView) findViewById(R.id.tvGra);
        /*btnBack = (Button) findViewById(R.id.btnBack);*/
        graDetail = (ExpandableListView)findViewById(R.id.graDetail);

        Bundle b = getIntent().getExtras();
        int grammar_id = b.getInt("grammar_id");
        int sentence_id = b.getInt("sentence_id");

        GrammarRepo gr = new GrammarRepo();
        Grammar gra = gr.getGrammarById(grammar_id);
        tvGra.setText(gra.getName());

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
            List<String> listExplainHeader = new ArrayList<>();
            for(GrammarExplain ge : listExplain) {
                List<Sentence> listSentence = new ArrayList<>();
                if(sentence_id == 0) {
                    listSentence = sr.getSentenceBySelectQuery("WHERE " + Sentence.KEY_GrammarExplainId + " = " + ge.getId());
                } else {
                    /*btnBack.setText(getResources().getString(R.string.btnClose));*/
                    listSentence = sr.getSentenceBySelectQuery("WHERE " + Sentence.KEY_GrammarExplainId + " = " + ge.getId() +
                            " AND " + Sentence.KEY_Id + " != " + sentence_id);
                }

                sentenceData.put(ge.getExplaination(), listSentence);
                listExplainHeader.add(ge.getExplaination());
            }

            explainData.put(gs.getStructure(), listExplainHeader);
            listStrucHeader.add(gs.getStructure());
        }
    }

    /*public void btnBackOnClick(View view) {
        this.finish();
    }*/

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
