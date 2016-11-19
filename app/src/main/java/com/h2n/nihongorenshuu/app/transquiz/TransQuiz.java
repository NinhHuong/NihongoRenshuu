package com.h2n.nihongorenshuu.app.transquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.h2n.nihongorenshuu.repo.HistoryRepo;
import com.h2n.nihongorenshuu.repo.SelectedHistoryRepo;
import com.h2n.nihongorenshuu.repo.SentenceRepo;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/12/2016.
 */

public class TransQuiz extends AppCompatActivity implements View.OnClickListener{


//    private List<Sentence> listSen = new ArrayList<>();
    private List<History> listHis = new ArrayList<>();
//    private List<Grammar> listGra = new ArrayList<>();
    private List<JSONObject> listRetrieve = new ArrayList<>();
    Sentence sentence;
    Grammar grammar;
    private int isVnToJp, index;

    TextView tvSenNo, tvGra, tvSen, tvCorrection, tvKey, tvKeyTitle, tvDone;
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
        isVnToJp = b.getInt("isVn2Jp");
        ArrayList<Integer> listSelectedGra = b.getIntegerArrayList("listSelectedGra");
        String listGraId = listSelectedGra.toString();

        listGraId = listGraId.replace("[", "(");
        listGraId = listGraId.replace("]", ")");

        tvSenNo = (TextView) findViewById(R.id.tvSenNo);
        tvGra = (TextView) findViewById(R.id.tvGra);
        tvSen = (TextView) findViewById(R.id.tvSenContent);
        tvCorrection = (TextView) findViewById(R.id.tvCorrection);
        tvKey = (TextView) findViewById(R.id.tvKey);
        tvKeyTitle = (TextView) findViewById(R.id.tvKeyTitle);
        tvDone = (TextView) findViewById(R.id.tvDone);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        etUserTrans = (EditText) findViewById(R.id.etUserTrans);
        pbStatus = (ProgressBar) findViewById(R.id.pbStatus);

//        check is Continue
        HistoryRepo hr = new HistoryRepo();
        listHis = hr.getHistoryBySelectQuery("WHERE " + History.KEY_IsContinue + " = 1");
        if(listHis.size() != 0) {
            String listDoneHis = "(";
            for(int i=0; i<listHis.size(); i++) {
                if(i != listHis.size()-1) {
                    listDoneHis = listDoneHis + listHis.get(i).getSentenceId() + ", ";
                } else {
                    listDoneHis = listDoneHis + listHis.get(i).getSentenceId() + ")";
                }
            }

            String select = "SELECT " +  GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId + ", " + Grammar.TABLE + "." + Grammar.KEY_Level + ", " +
                    Grammar.TABLE + "." + Grammar.KEY_Name + ", " + Grammar.TABLE + "." + Grammar.KEY_Unit + ", " +
                    Sentence.TABLE + "." + Sentence.KEY_Id + ", " + Sentence.TABLE + "." + Sentence.KEY_GrammarExplainId + ", " +
                    Sentence.TABLE + "." + Sentence.KEY_JpSentence + ", " + Sentence.TABLE + "." + Sentence.KEY_VnSentence +
                    " FROM " + Sentence.TABLE + ", " + Grammar.TABLE + ", " + GrammarStructure.TABLE + ", " + GrammarExplain.TABLE + " WHERE " +
                    Grammar.TABLE + "." + Grammar.KEY_Id + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId +
                    " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_StructureId + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_Id +
                    " AND " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId + " = " + Grammar.TABLE + "." + Grammar.KEY_Id +
                    " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_Id + " = " + Sentence.TABLE + "." + Sentence.KEY_GrammarExplainId +
                    " AND " + Sentence.TABLE + "." + Sentence.KEY_Id + " IN " + listDoneHis;
            SentenceRepo sr = new SentenceRepo();
            listRetrieve = sr.getRetrieveSentenceAndGrammar(select);

            select = "SELECT " +  GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId + ", " + Grammar.TABLE + "." + Grammar.KEY_Level + ", " +
                    Grammar.TABLE + "." + Grammar.KEY_Name + ", " + Grammar.TABLE + "." + Grammar.KEY_Unit + ", " +
                    Sentence.TABLE + "." + Sentence.KEY_Id + ", " + Sentence.TABLE + "." + Sentence.KEY_GrammarExplainId + ", " +
                    Sentence.TABLE + "." + Sentence.KEY_JpSentence + ", " + Sentence.TABLE + "." + Sentence.KEY_VnSentence +
                    " FROM " + Sentence.TABLE + ", " + Grammar.TABLE + ", " + GrammarStructure.TABLE + ", " + GrammarExplain.TABLE + " WHERE " +
                    Grammar.TABLE + "." + Grammar.KEY_Id + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId +
                    " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_StructureId + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_Id +
                    " AND " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId + " = " + Grammar.TABLE + "." + Grammar.KEY_Id +
                    " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_Id + " = " + Sentence.TABLE + "." + Sentence.KEY_GrammarExplainId +
                    " AND " + Grammar.TABLE + "." + Grammar.KEY_Id + " IN " + listGraId +
                    " AND " + Sentence.TABLE + "." + Sentence.KEY_Id +  " NOT IN " + listDoneHis +
                    " ORDER BY RANDOM()";
            List<JSONObject> temp = sr.getRetrieveSentenceAndGrammar(select);
            listRetrieve.addAll(temp);

            index = listHis.size();
        } else {
            String select = "SELECT " +  GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId + ", " + Grammar.TABLE + "." + Grammar.KEY_Level + ", " +
                    Grammar.TABLE + "." + Grammar.KEY_Name + ", " + Grammar.TABLE + "." + Grammar.KEY_Unit + ", " +
                    Sentence.TABLE + "." + Sentence.KEY_Id + ", " + Sentence.TABLE + "." + Sentence.KEY_GrammarExplainId + ", " +
                    Sentence.TABLE + "." + Sentence.KEY_JpSentence + ", " + Sentence.TABLE + "." + Sentence.KEY_VnSentence +
                    " FROM " + Sentence.TABLE + ", " + Grammar.TABLE + ", " + GrammarStructure.TABLE + ", " + GrammarExplain.TABLE + " WHERE " +
                    Grammar.TABLE + "." + Grammar.KEY_Id + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId +
                    " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_StructureId + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_Id +
                    " AND " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId + " = " + Grammar.TABLE + "." + Grammar.KEY_Id +
                    " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_Id + " = " + Sentence.TABLE + "." + Sentence.KEY_GrammarExplainId +
                    " AND " + Grammar.TABLE + "." + Grammar.KEY_Id + " IN " + listGraId + " ORDER BY RANDOM()";
            SentenceRepo sr = new SentenceRepo();
            listRetrieve = sr.getRetrieveSentenceAndGrammar(select);
            index = 0;
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
            if(!btnSubmit.isEnabled()) {
                index ++;
                clearScreen();
                if(index < listHis.size()) {
                    loadReviewScreen();
                } else {
                    loadNewSentence();
                }
            } else {
                listRetrieve.remove(index);
                JSONObject json = new JSONObject();
                try {
                    json.put("Sentence", sentence);
                    json.put("Grammar", grammar);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listRetrieve.add(json);
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
        try{
            sentence = new Sentence(listRetrieve.get(index).getJSONObject("Sentence"));
            grammar = new Grammar(listRetrieve.get(index).getJSONObject("Grammar"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
        tvSenNo.setText(getResources().getString(R.string.sen_no) + " " + Integer.toString(index + 1));
        tvDone.setText(getResources().getString(R.string.done) + " " + listHis.size() + "/" + listRetrieve.size());
        tvGra.setText(" " + grammar.getName());
        if(isVnToJp == 1) {
            tvSen.setText(sentence.getVnSentence());
        } else {
            tvSen.setText(sentence.getJpSentence());
        }
//        btnNext.setEnabled(false);
        if(index == 0) {
            btnPrev.setEnabled(false);
        }
        if(index == listRetrieve.size()-1) {
            btnNext.setEnabled(false);
        }
        pbStatus.setMax(listRetrieve.size());
        pbStatus.setProgress(listHis.size());
    }

    private void loadScreenAfterSubmit() {
        try{
            sentence = new Sentence(listRetrieve.get(index).getJSONObject("Sentence"));
            grammar = new Grammar(listRetrieve.get(index).getJSONObject("Grammar"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
        if(etUserTrans.getText().toString().trim().length() == 0) {
            showMessage(getResources().getString(R.string.dialog_title_warning), getResources().getString(R.string.dialog_warning_empty));
            return;
        }
        btnSubmit.setEnabled(false);
//        btnNext.setEnabled(true);
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
        HistoryRepo hr = new HistoryRepo();
        hr.insert(his);

        tvDone.setText(getResources().getString(R.string.done) + " " + listHis.size() + "/" + listRetrieve.size());
    }

    private void loadReviewScreen() {
        tvSenNo.setText(getResources().getString(R.string.sen_no) + " " + Integer.toString(index + 1));
        tvDone.setText(getResources().getString(R.string.done) + " " + listHis.size() + "/" + listRetrieve.size());
        try{
            sentence = new Sentence(listRetrieve.get(index).getJSONObject("Sentence"));
            grammar = new Grammar(listRetrieve.get(index).getJSONObject("Grammar"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
        tvGra.setText(" " + grammar.getName());
        etUserTrans.setText(listHis.get(index).getUserTrans());
        if(isVnToJp == 1) {
            tvSen.setText(sentence.getVnSentence());
            tvKey.setText(sentence.getJpSentence());
        } else {
            tvSen.setText(sentence.getJpSentence());
            tvKey.setText(sentence.getVnSentence());
        }
//        btnNext.setEnabled(false);
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
        tvDone.setText("");
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
