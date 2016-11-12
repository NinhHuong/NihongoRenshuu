package com.h2n.nihongorenshuu.app.senzzleGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.h2n.nihongorenshuu.MainActivity;
import com.h2n.nihongorenshuu.R;

/**
 * Created by catbui on 11/11/2016.
 */

public class Submit extends AppCompatActivity implements View.OnClickListener{

    Button btViewGame, btBackHome, btTryAgain;
    TextView textKetQua;
    String dungSai = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senzzle_submit);

        thamChieu();
        Bundle layGiaTri = new Bundle();
        if(layGiaTri != null){
            dungSai = layGiaTri.getString("ketQua");
            textKetQua.setText(dungSai);
        }
        btBackHome.setOnClickListener(this);
        btTryAgain.setOnClickListener(this);
        btViewGame.setOnClickListener(this);

    }







    @Override
    public void onClick(View v) {
        int getId = v.getId();
        switch (getId){
            case R.id.btViewAll:
                Toast.makeText(this, "view all", Toast.LENGTH_SHORT).show();
                // len truyen gia tri nhu cu sang de co the view lai tu dau
                // gai tri chua truyen dang
                // co the goi lai game xep henh nhung lam han check truyen gia tri giang cho
                // neu gia tri tu ben nay truyen sang thi se cho view all
                // neu ko co gia tri truyen sang chi vd check = "" thi khong co gi thay doi.// va phai setEnable(false);


                // code here
                break;
            case R.id.btTryAgain: // done
                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
                Intent tryagain = new Intent(getApplicationContext(), SenzzleGame.class);
                startActivity(tryagain);
                finish();
                //code here
                break;
            case R.id.btHome: // done
                //code here // chi can quay ve page main
                Intent backhome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backhome);
                finish();
                //
                break;
            default:
        }
    }



    private void thamChieu() {
        textKetQua = (TextView) findViewById(R.id.textKeqQua);

        btViewGame = (Button) findViewById(R.id.btViewAll);
        btTryAgain = (Button) findViewById(R.id.btTryAgain);
        btBackHome = (Button) findViewById(R.id.btHome);
    }
}