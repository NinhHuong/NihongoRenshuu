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

public class game01Submit extends AppCompatActivity implements View.OnClickListener {

    Button btViewGame, btBackHome, btTryAgain, btContinue;
    TextView textKetQua;

    String id = "";
    String Cau_Nguoi = "";
    int status = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game01_submit);
        setTitle("SUBMIT");

        thamChieu();
        Bundle layGiaTri = getIntent().getExtras();
        if (layGiaTri != null) {
            textKetQua.setText(layGiaTri.getString("ketQua"));

            status = layGiaTri.getInt("status");
            id = layGiaTri.getString("id");
            Cau_Nguoi = layGiaTri.getString("Cau_Nguoi");

            //================du lieu giu sang========================
            //submit.putExtra("ketQua", cauDung+"/"+number);// gui ke qua di o day
//            submit.putExtra("CauRandom", String.valueOf(sentenceList));
//            submit.putExtra("ViewNguoiLam", (Parcelable) nguoiLam);
//            submit.putExtra("soCaulamDen", status);

//            Log.d("kiemTraDulieu",layGiaTri.getString("ketQua"));
//            Log.d("kiemTraDulieu",layGiaTri.getString("sentenceList"));
//            Log.d("kiemTraDulieu",layGiaTri.getString("nguoiLam"));
//            Log.d("kiemTraDulieu", id);
//            Log.d("kiemTraDulieu", String.valueOf(status));
//            Log.d("kiemTraDulieu", Cau_Nguoi);


        }




        btBackHome.setOnClickListener(this);
        btTryAgain.setOnClickListener(this);
        btViewGame.setOnClickListener(this);
        btContinue.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int getId = v.getId();
        switch (getId) {
            case R.id.btViewAll:
               Intent viewGame = new Intent(getApplicationContext(), game01View.class);

                viewGame.putExtra("status", status);
                viewGame.putExtra("id", id);
                viewGame.putExtra("Cau_Nguoi", Cau_Nguoi);
                startActivity(viewGame);
                finish();


                // code here
                break;
            case R.id.btTryAgain: // done
                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
                Intent tryagain = new Intent(getApplicationContext(), SenzzleGame.class);
                startActivity(tryagain);
                finish();
                //code here
                break;


            //======= done=====================================
            case R.id.btContinue: // done
                //code here // chi can quay ve page main
                Intent continueGame = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(continueGame);
                finish();

                break;
            case R.id.btBackHome:
//                Intent backhome = new Intent(getApplicationContext(),gethome);
//                startActivity(btBackHome);
//                finish();
                break;
            default:
        }
    }


    private void thamChieu() {
        textKetQua = (TextView) findViewById(R.id.textKeqQua);

        btViewGame = (Button) findViewById(R.id.btViewAll);
        btTryAgain = (Button) findViewById(R.id.btTryAgain);
        btBackHome = (Button) findViewById(R.id.btContinue);
        btContinue = (Button) findViewById(R.id.btContinue);
    }
}

