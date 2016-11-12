package com.h2n.nihongorenshuu.app.senzzleGame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.entity.SentencePart;

import java.util.ArrayList;

/**
 * Created by catbui on 11/11/2016.
 */

public class SenzzleGame extends AppCompatActivity implements View.OnClickListener {
    Button btA, btB, btC, btD;
    TextView back, next, submit, textShow, Refesh, textNumberDem;
    int numberDem= 1;
    int number = 10;
    String dungSai = "5/10";

    ArrayList<SentencePart> listcurent = new ArrayList<>();
    ArrayList<SentencePart> listSentenceParts = new ArrayList<>();


//    DatabaseHelper database = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senzzle_game);
        thamChieu();
        btA.setOnClickListener(this);
        btB.setOnClickListener(this);
        btC.setOnClickListener(this);
        btD.setOnClickListener(this);
        next.setOnClickListener(this);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        Refesh.setOnClickListener(this);
        textNumberDem.setText(numberDem+"/"+number);


    }


    @Override
    public void onClick(View v) {
        int getId = v.getId();
        switch (getId) {
            case R.id.button:
                textShow.setText(btA.getText() + " " + textShow.getText());

                btA.setEnabled(false);
                break;
            case R.id.button2:
                textShow.setText(btB.getText() + " " + textShow.getText());

                btB.setEnabled(false);
                break;
            case R.id.button3:
                textShow.setText(btC.getText() + " " + textShow.getText());

                btC.setEnabled(false);
                break;
            case R.id.button4:
                textShow.setText(btD.getText() + " " + textShow.getText());

                btD.setEnabled(false);
                break;

            case R.id.submit:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Confirm submit");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Toast.makeText(SenzzleGame.this, "tong hop diem o day ", Toast.LENGTH_SHORT).show();
                        // goi den trang tong hop dap an dung sai
                        Intent submit = new Intent(getApplicationContext(), Submit.class);
                        submit.putExtra("ketQua",dungSai);
                        startActivity(submit);

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

                // tinh diem dung sai cho cau
                /// tinh diem so cau dung so cau sai luon tai day

                break;
            case R.id.next:

                if(numberDem <= number){
                    textNumberDem.setText((numberDem)+ "/" + number);
                    numberDem++;
                }
                else{
                    Toast.makeText(this, "you are filish! ", Toast.LENGTH_SHORT).show();
                }

                // check luon xem cao phai cau dung hay cau sai

                //
                // cau tiep theo o day
                btA.setText(numberDem+"adflakjdlajs asdfasdf  asdfadsf");
                btB.setText(numberDem+"adflakjdlajsdl;jasdfasdf  asdfadsf");
                btC.setText(numberDem+"adflakjdlajsdl;jasldj;alsdjf  adsf");
                btD.setText(numberDem+"adflakjdlajsdl;jasldj;aadsf");

                textShow.setText("");
                btA.setEnabled(true);
                btB.setEnabled(true);
                btC.setEnabled(true);
                btD.setEnabled(true);
                break;
            case R.id.back:
                if(numberDem == 1) {
                    Toast.makeText(this, "khong the back dc nua", Toast.LENGTH_SHORT).show();
                }
                else{
                    numberDem--;
                    textNumberDem.setText(numberDem+"/"+number);
                }

                //quay lai cau cu o day

                textShow.setText("");
                btA.setEnabled(true);
                btB.setEnabled(true);
                btC.setEnabled(true);
                btD.setEnabled(true);
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
