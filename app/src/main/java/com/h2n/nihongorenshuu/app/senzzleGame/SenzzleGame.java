package com.h2n.nihongorenshuu.app.senzzleGame;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

/**
 * Created by catbui on 11/11/2016.
 */

public class SenzzleGame extends AppCompatActivity implements View.OnClickListener {
    Button btA, btB, btC, btD;
    TextView back, next, submit, textShow, Refesh, textNumberDem;
    int number = 1;
    int numberDenCau = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senzzle_game);

//        DatabaseHelper dbhelper = new DatabaseHelper();
//        try {
//            dbhelper.createDatabase();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        thamChieu();
        loadIdSentencesQuestion();

        number = sentenceList.size();
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


    }

    //==================LAY DANH SACH CAU RANDOM=================================
    private void loadIdSentencesQuestion() {
        SentenceRepo sentenceRepo = new SentenceRepo();
        sentenceList = sentenceRepo.getSentenceBySelectQuery("where " + Sentence.KEY_GrammarExplainId + " in (1,2,3,4) ORDER BY RANDOM()");
    }

    List<Sentence> sentenceList;
    Map<Integer, String> nguoiLam = new HashMap();
    int status = 1;

    @Override
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
            case R.id.submit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm submit");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SenzzleGame.this, "tong hop diem o day ", Toast.LENGTH_SHORT).show();
                        //=============== check ket qua bai lam=================
                        int cauDung = 0;
                        //=== lay id=== day sang
                        String id = "";
                        for(int j = 0; j <sentenceList.size(); j++){
                            id+=  sentenceList.get(j).getId() +"/";
                        }
                        String Cau_Nguoi = "";
                        for(int ji = 1 ; ji <= nguoiLam.size(); ji++){
                            Cau_Nguoi+=  nguoiLam.get(ji)+"/";
                        }


                        for (int index = 1; index < status; index++) {
//                            Log.d("checkketqua",nguoiLam.get(index));
//                            Log.d("checkketqua",sentenceList.get(index).getJpSentence());
                            if (nguoiLam.get(index).equals(sentenceList.get(index).getJpSentence())) {
                                cauDung++;
                            }
                        }



                        Intent submit = new Intent(SenzzleGame.this, game01Submit.class);
                        submit.putExtra("ketQua", cauDung+"/"+number);// gui ke qua di o day
                        //================= gui data di choi activity======================
//                        submit.putExtra("sentenceList", String.valueOf(sentenceList));
//                        submit.putExtra("nguoiLam", String.valueOf(nguoiLam));
                        submit.putExtra("status", status);
                        submit.putExtra("id", id);
                        submit.putExtra("Cau_Nguoi", Cau_Nguoi);
                        //=================================================================
//                        Log.d("vaoday","oke");

                        startActivity(submit);
                        finish();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;


            ///===============LEN XUONG BUC MINH=================
            ///===============LEN XUONG BUC MINH=================


            case R.id.next:

                nguoiLam.put(numberDenCau, textShow.getText().toString().trim());// dung de luu cau cu. khi chuyen sang cau khacc
                btA.setEnabled(true);
                btB.setEnabled(true);
                btC.setEnabled(true);
                btD.setEnabled(true);


                if (numberDenCau < number) {
                    numberDenCau++;

                    if (numberDenCau > status) {
                        status = numberDenCau;
                        textShow.setText("");
                    }
                    if (status >= numberDenCau) {
                        textShow.setText(nguoiLam.get(numberDenCau));
                        Log.d("Testnext", textShow.getText().toString());
                    }

                    textNumberDem.setText(numberDenCau + "/" + number);
                    for (int ij = 0; ij < number; ij++) {
                        if (ij == numberDenCau) {
                            showAnswerABCD(sentenceList.get(ij).getId());
                        }
                    }
                } else {
                    Toast.makeText(this, "you are filish! ", Toast.LENGTH_SHORT).show();
                }
                break;


            //======= co the xem khi luu lai===============
            case R.id.back:
                nguoiLam.put(numberDenCau, textShow.getText().toString().trim());// kho biet dung hay sai hehehe
                btA.setEnabled(true);
                btB.setEnabled(true);
                btC.setEnabled(true);
                btD.setEnabled(true);
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
                textShow.setText("");
                btA.setEnabled(true);
                btB.setEnabled(true);
                btC.setEnabled(true);
                btD.setEnabled(true);
                break;
            default:
        }
    }


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
