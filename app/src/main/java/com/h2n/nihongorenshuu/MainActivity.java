package com.h2n.nihongorenshuu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.h2n.nihongorenshuu.app.grammar.GrammarListReview;
import com.h2n.nihongorenshuu.app.transquiz.TransQuizHome;
import com.h2n.nihongorenshuu.database.DatabaseHelper;
import com.h2n.nihongorenshuu.database.DatabaseManager;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.repo.GrammarRepo;
import com.h2n.nihongorenshuu.repo.SelectedHistoryRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        /*implements NavigationView.OnNavigationItemSelectedListener*/ {

    Button btnTrans, btnGraReview;
    RadioGroup rgLevel;
    RadioButton rbLevel5, rbLevel4, rbLevel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper();
        try{
            dbHelper.createDatabase();
        } catch(IOException e) {
            e.printStackTrace();
        }

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        btnTrans = (Button) findViewById(R.id.btnTransQuiz);
        btnGraReview = (Button) findViewById(R.id.btnGraReview);
        rgLevel = (RadioGroup) findViewById(R.id.rgLevel);
        rbLevel5 = (RadioButton) findViewById(R.id.rbLev5);
        rbLevel4 = (RadioButton) findViewById(R.id.rbLev4);
        rbLevel3 = (RadioButton) findViewById(R.id.rbLev3);


        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        List<Integer> listLevel = new ArrayList<>();
        String query = "SELECT DISTINCT " + Grammar.KEY_Level + " FROM " + Grammar.TABLE;
        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int l = cursor.getInt(cursor.getColumnIndex(Grammar.KEY_Level));

                listLevel.add(l);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        for(int l : listLevel) {
            switch (l) {
                case 5 :
                    rbLevel5.setEnabled(true);
                case 4:
                    rbLevel4.setEnabled(true);
                case 3:
                    rbLevel3.setEnabled(true);
            }
        }
    }

    public void btnTransOnClick(View view) {
        int level = 0;
        if(rgLevel.getCheckedRadioButtonId() == R.id.rbLev3) {
            level = 3;
        } else if(rgLevel.getCheckedRadioButtonId() == R.id.rbLev4) {
            level = 4;
        } else if(rgLevel.getCheckedRadioButtonId() == R.id.rbLev5) {
            level = 5;
        }

        Intent i = new Intent(this, TransQuizHome.class);
        Bundle b = new Bundle();
        b.putInt("level", level);
        i.putExtras(b);
        startActivity(i);
    }

    public void btnGraReviewOnClick(View view) {
        int level = 0;
        if(rgLevel.getCheckedRadioButtonId() == R.id.rbLev3) {
            level = 3;
        } else if(rgLevel.getCheckedRadioButtonId() == R.id.rbLev4) {
            level = 4;
        } else if(rgLevel.getCheckedRadioButtonId() == R.id.rbLev5) {
            level = 5;
        }

        Intent i = new Intent(this, GrammarListReview.class);
        Bundle b = new Bundle();
        b.putInt("level", level);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.graReview) {
            setTitle("Grammar Review");
            Intent i = new Intent(this, GrammarListReview.class);
            startActivity(i);
        } else if (id == R.id.transQuiz) {
            Intent i = new Intent(this, TransQuizHome.class);
            startActivity(i);
            setTitle("Translation Quiz");
        } else if (id == R.id.report) {
            setTitle("Report");
            NavUtils.navigateUpFromSameTask(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title.toString());
    }*/
}

