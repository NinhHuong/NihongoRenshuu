package com.h2n.nihongorenshuu.app.senzzleGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.entity.Sentence;
import com.h2n.nihongorenshuu.entity.SentencePart;
import com.h2n.nihongorenshuu.repo.SentencePartRepo;
import com.h2n.nihongorenshuu.repo.SentenceRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class game01View extends AppCompatActivity implements View.OnClickListener {

    Button btA, btB, btC, btD;
    TextView back, next, submit, textShow, Refesh, textNumberDem;
    int number = 0;
    int numberDenCau = 1;

    List<Sentence> sentenceList;
    Map<Integer, String> nguoiLam = new HashMap();
    String id = "";
    String Cau_Nguoi = "";
    int status = 1;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senzzle_game);


        Bundle layGiaTri = getIntent().getExtras();
        if (layGiaTri != null) {
            status = layGiaTri.getInt("status");
            id = layGiaTri.getString("id");
            Cau_Nguoi = layGiaTri.getString("Cau_Nguoi");
        }


        // goi tung cau thoe id
        //=================================== tac lay gia tri =============================
        items = id.split("/");
//        SentenceRepo sentenceRepo = new SentenceRepo();
        sentenceList = sentenceRepo.getAllSentence();
        for (String item : items) {
//            sentenceList.add(sentenceRepo.getSentenceBySelectQuery("where "+ Sentence.KEY_Id));
            number++;
        }
        String[] itemsNguoiLams = Cau_Nguoi.split("/");
        int valuekey = 1;
        for (String nl : itemsNguoiLams) {
            nguoiLam.put(valuekey, nl);
            valuekey++;
        }
        //============================================================================





        Log.d("sentencelist_gameview01", sentenceList.toString());
//        Log.d("sentencelist_gameview01", id);
//        Log.d("sentencelist_gameview01", Cau_Nguoi);
//        Log.d("sentencelist_gameview01", items[2]);
//        Log.d("sentencelist_gameview01", String.valueOf(id.split("|")));
//        Log.d("nguoiLam_gameView01", nguoiLam.get(1));
//=========================== the same code here
        thamChieu();

        Refesh.setText("You Did");
        submit.setText("Back Home");
        textShow.setText("");
        btA.setOnClickListener(this);
        btB.setOnClickListener(this);
        btC.setOnClickListener(this);
        btD.setOnClickListener(this);
        next.setOnClickListener(this);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        Refesh.setOnClickListener(this);
        textNumberDem.setText(numberDenCau + "/" + number);
        btA.setEnabled(false);
        btB.setEnabled(false);
        btC.setEnabled(false);
        btD.setEnabled(false);

    }


    public void onClick(View v) {
        int getId = v.getId();
        switch (getId) {
            case R.id.button:
                btA.setEnabled(false);
                textShow.setText(textShow.getText() + "" + btA.getText());
                break;
            case R.id.button2:
                btB.setEnabled(false);
                textShow.setText(textShow.getText() + "" + btB.getText());
                break;
            case R.id.button3:
                btC.setEnabled(false);
                textShow.setText(textShow.getText() + "" + btC.getText());
                break;
            case R.id.button4:
                btD.setEnabled(false);
                textShow.setText(textShow.getText() + "" + btD.getText());
                break;
//=============================================DONE========================================================================
            case R.id.submit: // bay gio no la finish ke thuc thoat khoi review
                //======== chu y: code tai day nhe
                Intent backHome = new Intent(getApplicationContext(), game01Submit.class);
                startActivity(backHome);
                finish();
                break;


            case R.id.next:

//                nguoiLam.put(numberDenCau, textShow.getText().toString().trim());// dung de luu cau cu. khi chuyen sang cau khacc

                if (numberDenCau < number) {
                    numberDenCau++;
                    textShow.setText(nguoiLam.get(numberDenCau));
                    textNumberDem.setText(numberDenCau + "/" + number);
                    for (int ij = 0; ij < number; ij++) {
                        if (ij == numberDenCau) {
                            showAnswerABCD(Integer.parseInt(items[ij]));
                        }
                    }
                } else {
                    Toast.makeText(this, "you are filish! ", Toast.LENGTH_SHORT).show();
                }
                break;


            //======= co the xem khi luu lai===============
            case R.id.back:
                if (numberDenCau == 1) {
                    Toast.makeText(this, "khong the back dc nua", Toast.LENGTH_SHORT).show();
                } else {
                    numberDenCau--;
                    textNumberDem.setText(numberDenCau + "/" + number);
                    textShow.setText(nguoiLam.get(numberDenCau));
                }
                break;

            //xong oke
            case R.id.Refesh:
                // chem nut refesh thanh cau cau dap an
                if (Refesh.getText().equals("Answer")) {
                    Refesh.setText("You Did");
                    textShow.setText(nguoiLam.get(numberDenCau));

                } else if (Refesh.getText().equals("You Did")) {
                    Refesh.setText("Translate");
                    LayCauAnswerJP(numberDenCau);
                } else {
                    Refesh.setText("Answer");
                    LayCauAnswerTV(numberDenCau);
                }

                break;
            default:
        }
    }

    SentenceRepo sentenceRepo = new SentenceRepo();

    private void LayCauAnswerTV(int numberDenCau) {
        numberDenCau--;
//        sentenceList.clear();
//        sentenceList = sentenceRepo.getSentenceBySelectQuery("where " + Sentence.KEY_Id + "=" + numberDenCau);
        textShow.setText(sentenceList.get(Integer.parseInt(items[numberDenCau])).getVnSentence());
    }

    private void LayCauAnswerJP(int id) {
        id--;
//        sentenceList.clear();
//        sentenceList = sentenceRepo.getSentenceBySelectQuery("where " + Sentence.KEY_Id + "=" + id);
        textShow.setText(sentenceList.get(Integer.parseInt(items[id])).getJpSentence());
    }


    //=======================cai nay done=========================
    /// load  cac truong hop vao trong dap an
    private void showAnswerABCD(int idGoiCauLam_1) {
        SentencePartRepo sentencePartRepo = new SentencePartRepo();
        String sentencePartwhere = "where " + SentencePart.KEY_SentenceId + " = " + idGoiCauLam_1 + " ORDER BY RANDOM()";

        List<SentencePart> sentencePartList = sentencePartRepo.getSentencePartBySelectQuery(sentencePartwhere);
//         Log.d("sentencePartList", sentencePartList.toString());
        btA.setText(sentencePartList.get(0).getPartContent().trim());
        btB.setText(sentencePartList.get(1).getPartContent().trim());
        btC.setText(sentencePartList.get(2).getPartContent().trim());
        btD.setText(sentencePartList.get(3).getPartContent().trim());

    }

    private void thamChieu() {
        btA = (Button) findViewById(R.id.button);
        btB = (Button) findViewById(R.id.button2);
        btC = (Button) findViewById(R.id.button3);
        btD = (Button) findViewById(R.id.button4);

        next = (TextView) findViewById(R.id.next);
        back = (TextView) findViewById(R.id.back);
        submit = (TextView) findViewById(R.id.submit);
        textShow = (TextView) findViewById(R.id.textShow);
        Refesh = (TextView) findViewById(R.id.Refesh);
        textNumberDem = (TextView) findViewById(R.id.textNumberDem);
    }

}