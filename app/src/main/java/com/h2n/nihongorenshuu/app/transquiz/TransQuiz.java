package com.h2n.nihongorenshuu.app.transquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.app.grammar.GrammarDetail;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.entity.GrammarExplain;
import com.h2n.nihongorenshuu.entity.GrammarStructure;
import com.h2n.nihongorenshuu.entity.History;
import com.h2n.nihongorenshuu.entity.Sentence;
import com.h2n.nihongorenshuu.repo.GrammarRepo;
import com.h2n.nihongorenshuu.repo.SentenceRepo;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/12/2016.
 */

public class TransQuiz extends Activity implements View.OnClickListener{


//    private List<Sentence> listSen = new ArrayList<>();
    private List<History> listHis = new ArrayList<>();
//    private List<Grammar> listGra = new ArrayList<>();
    private List<JSONObject> listRetrieve = new ArrayList<>();
    Sentence sentence;
    Grammar grammar;
    private int isVnToJp, index;

    TextView tvSenNo, tvGra, tvSen, tvCorrection, tvKey, tvKeyTitle;
    Button btnSubmit;
    ImageButton btnPrev, btnNext;
    EditText etUserTrans;
    ProgressBar pbStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_quiz);

        //get selected grammarId from TransQuizHome
        Bundle b = getIntent().getExtras();
        isVnToJp = b.getInt("isJpToVn");
        ArrayList<Integer> listSelectedGra = b.getIntegerArrayList("listSelectedGra");
        String listGraId = listSelectedGra.toString();
        isVnToJp = 1;

        listGraId = listGraId.replace("[", "(");
        listGraId = listGraId.replace("]", ")");

        tvSenNo = (TextView) findViewById(R.id.tvSenNo);
        tvGra = (TextView) findViewById(R.id.tvGra);
        tvSen = (TextView) findViewById(R.id.tvSenContent);
        tvCorrection = (TextView) findViewById(R.id.tvCorrection);
        tvKey = (TextView) findViewById(R.id.tvKey);
        tvKeyTitle = (TextView) findViewById(R.id.tvKeyTitle);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        etUserTrans = (EditText) findViewById(R.id.etUserTrans);
        pbStatus = (ProgressBar) findViewById(R.id.pbStatus);

        String where = ", " + Grammar.TABLE + ", " + GrammarStructure.TABLE + ", " + GrammarExplain.TABLE + " WHERE " +
                Grammar.TABLE + "." + Grammar.KEY_Id + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId +
                " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_StructureId + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_Id +
                " AND " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId + " = " + Grammar.TABLE + "." + Grammar.KEY_Id +
                " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_Id + " = " + Sentence.TABLE + "." + Sentence.KEY_GrammarExplainId +
                " AND " + Grammar.TABLE + "." + Grammar.KEY_Id + " IN " + listGraId + " ORDER BY RANDOM()";
        String select = "SELECT * FROM " + Sentence.TABLE + ", " + Grammar.TABLE + ", " + GrammarStructure.TABLE + ", " + GrammarExplain.TABLE + " WHERE " +
                Grammar.TABLE + "." + Grammar.KEY_Id + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId +
                " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_StructureId + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_Id +
                " AND " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId + " = " + Grammar.TABLE + "." + Grammar.KEY_Id +
                " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_Id + " = " + Sentence.TABLE + "." + Sentence.KEY_GrammarExplainId +
                " AND " + Grammar.TABLE + "." + Grammar.KEY_Id + " IN " + listGraId + " ORDER BY RANDOM()";
        SentenceRepo sr = new SentenceRepo();
//        listSen = sr.getSentenceBySelectQuery(where);
        listRetrieve = sr.getRetrieveSentenceAndGrammar(select);
        index = 0;
        try{
            sentence = new Sentence(listRetrieve.get(index).getJSONObject("Sentence"));
            grammar = new Grammar(listRetrieve.get(index).getJSONObject("Grammar"));
        }catch (JSONException e) {
            e.printStackTrace();
        }

        clearScreen();
        loadNewSentence();

        btnSubmit.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvGra.setOnClickListener(this);
    }

    public void onClick(View view) {
        if(view == btnSubmit) {
            loadScreenAfterSubmit();
        } else if(view == btnNext) {
            index ++;
            clearScreen();
            if(index < listHis.size()) {
                loadReviewScreen();
            } else {
                loadNewSentence();
            }
        } else if (view == btnPrev) {
            index--;
            clearScreen();
            loadReviewScreen();
        } else if(view == tvGra) {
            goToGrammarDetail(grammar.getId());
        }
    }

    private void loadNewSentence() {
        String no = Integer.toString(index + 1) + "/" + Integer.toString(listRetrieve.size());
        tvSenNo.setText(no);
        tvGra.setText(" " + grammar.getName());
        if(isVnToJp == 1) {
            tvSen.setText(sentence.getVnSentence());
        } else {
            tvSen.setText(sentence.getJpSentence());
        }
        btnNext.setEnabled(false);
        if(index == 0) {
            btnPrev.setEnabled(false);
        }
        if(index == listRetrieve.size()-1) {
            btnNext.setEnabled(false);
        }
        pbStatus.setMax(listRetrieve.size());
        pbStatus.setProgress(index + 1);
    }

    private void loadScreenAfterSubmit() {
        if(etUserTrans.getText().toString().trim().length() == 0) {
            showMessage(getResources().getString(R.string.dialog_title_warning), getResources().getString(R.string.dialog_warning_empty));
            return;
        }
        btnSubmit.setEnabled(false);
        btnNext.setEnabled(true);
        if(index == 0) {
            btnPrev.setEnabled(false);
        }
        if(index == listRetrieve.size()-1){
            btnNext.setEnabled(false);
        }

        etUserTrans.setEnabled(false);
        tvKeyTitle.setVisibility(View.VISIBLE);
        if(isVnToJp == 1) {
            tvKey.setText(sentence.getJpSentence());
        } else {
            tvKey.setText(sentence.getVnSentence());
        }
        History his = new History(sentence.getId(), etUserTrans.getText().toString().trim(), false);
        listHis.add(his);
    }

    private void loadReviewScreen() {
        String no = Integer.toString(index + 1) + "/" + Integer.toString(listRetrieve.size());
        tvSenNo.setText(no);
        tvGra.setText(" " + grammar.getName());
        etUserTrans.setText(listHis.get(index).getUserTrans());
        if(isVnToJp == 1) {
            tvSen.setText(sentence.getVnSentence());
            tvKey.setText(sentence.getJpSentence());
        } else {
            tvSen.setText(sentence.getJpSentence());
            tvKey.setText(sentence.getVnSentence());
        }
        btnNext.setEnabled(false);
        if(index == 0) {
            btnPrev.setEnabled(false);
        }
        pbStatus.setMax(listRetrieve.size());
        pbStatus.setProgress(index + 1);

        btnSubmit.setEnabled(false);
        btnNext.setEnabled(true);
        if(index == 0) {
            btnPrev.setEnabled(false);
        }
        if(index == listRetrieve.size()-1){
            btnNext.setEnabled(false);
        }

        etUserTrans.setEnabled(false);
        tvKeyTitle.setVisibility(View.VISIBLE);
    }

    private void goToGrammarDetail(int graId) {
        Intent i = new Intent(this, GrammarDetail.class);
        Bundle b = new Bundle();
        b.putInt("grammar_id", graId);
        i.putExtras(b);
        startActivity(i);
    }

    private void clearScreen() {
        tvSenNo.setText("");
        tvGra.setText("");
        tvSen.setText("");
        tvCorrection.setText("");
        tvKey.setText("");

        tvKeyTitle.setVisibility(View.INVISIBLE);
        btnSubmit.setEnabled(true);
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        etUserTrans.setText("");
        etUserTrans.setEnabled(true);
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
