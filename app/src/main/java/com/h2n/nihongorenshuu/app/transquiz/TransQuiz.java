package com.h2n.nihongorenshuu.app.transquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.entity.GrammarExplain;
import com.h2n.nihongorenshuu.entity.GrammarStructure;
import com.h2n.nihongorenshuu.entity.History;
import com.h2n.nihongorenshuu.entity.Sentence;
import com.h2n.nihongorenshuu.repo.GrammarRepo;
import com.h2n.nihongorenshuu.repo.SentenceRepo;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninhh on 11/12/2016.
 */

public class TransQuiz extends Activity implements View.OnClickListener{


    private List<Sentence> listSen = new ArrayList<>();
    private List<History> listHis = new ArrayList<>();
    private List<Grammar> listGra = new ArrayList<>();
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
        String listGra = listSelectedGra.toString();
        isVnToJp = 1;

        listGra = listGra.replace("[", "(");
        listGra = listGra.replace("]", ")");

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

        String where = "WHERE " + Sentence.KEY_GrammarExplainId + " IN " + listGra + " ORDER BY RANDOM()";
        SentenceRepo sr = new SentenceRepo();
        listSen = sr.getSentenceBySelectQuery(where);
        index = 0;
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
            goToGrammarDetail(listGra.get(index).getId());
        }
    }

    private void loadNewSentence() {
        Sentence sen = listSen.get(index);
        String no = Integer.toString(index + 1) + "/" + Integer.toString(listSen.size());
        tvSenNo.setText(no);
        GrammarRepo gr = new GrammarRepo();
        List<Grammar> list = gr.getGrammarBySelectQuery(", " + GrammarStructure.TABLE + ", " + GrammarExplain.TABLE + " WHERE " +
            GrammarExplain.TABLE + "." + GrammarExplain.KEY_StructureId + " = " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_Id +
            " AND " + GrammarStructure.TABLE + "." + GrammarStructure.KEY_GrammarId + " = " + Grammar.TABLE + "." + Grammar.KEY_Id +
            " AND " + GrammarExplain.TABLE + "." + GrammarExplain.KEY_Id + " = " + sen.getGrammarExplainId());
        if(list.size() == 1) {
            listGra.add((list.get(0)));
            tvGra.setText(" " + listGra.get(index).getName());
        }
        if(isVnToJp == 1) {
            tvSen.setText(sen.getVnSentence());
        } else {
            tvSen.setText(sen.getJpSentence());
        }
        btnNext.setEnabled(false);
        if(index == 0) {
            btnPrev.setEnabled(false);
        }
        if(index == listSen.size()-1) {
            btnNext.setEnabled(false);
        }
        pbStatus.setMax(listSen.size());
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
        if(index == listSen.size()-1){
            btnNext.setEnabled(false);
        }

        etUserTrans.setEnabled(false);
        tvKeyTitle.setVisibility(View.VISIBLE);
        if(isVnToJp == 1) {
            tvKey.setText(listSen.get(index).getJpSentence());
        } else {
            tvKey.setText(listSen.get(index).getVnSentence());
        }
        History his = new History(listSen.get(index).getId(), etUserTrans.getText().toString().trim(), false);
        listHis.add(his);
    }

    private void loadReviewScreen() {
        String no = Integer.toString(index + 1) + "/" + Integer.toString(listSen.size());
        tvSenNo.setText(no);
        tvGra.setText(" " + listGra.get(index).getName());
        etUserTrans.setText(listHis.get(index).getUserTrans());
        if(isVnToJp == 1) {
            tvSen.setText(listSen.get(index).getVnSentence());
            tvKey.setText(listSen.get(index).getJpSentence());
        } else {
            tvSen.setText(listSen.get(index).getJpSentence());
            tvKey.setText(listSen.get(index).getVnSentence());
        }
        btnNext.setEnabled(false);
        if(index == 0) {
            btnPrev.setEnabled(false);
        }
        pbStatus.setMax(listSen.size());
        pbStatus.setProgress(index + 1);

        btnSubmit.setEnabled(false);
        btnNext.setEnabled(true);
        if(index == 0) {
            btnPrev.setEnabled(false);
        }
        if(index == listSen.size()-1){
            btnNext.setEnabled(false);
        }

        etUserTrans.setEnabled(false);
        tvKeyTitle.setVisibility(View.VISIBLE);
    }

    private void goToGrammarDetail(int graId) {
        /*Intent i = new Intent(this, TransQuiz.class);
        Bundle b = new Bundle();
        b.putInt("isJpToVn", 1);
        i.putExtras(b);
        startActivity(i);*/
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
