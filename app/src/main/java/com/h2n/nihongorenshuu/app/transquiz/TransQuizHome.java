package com.h2n.nihongorenshuu.app.transquiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.database.DatabaseHelper;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.extendObject.CbGrammarItem;
import com.h2n.nihongorenshuu.extendObject.CbGrammarUnit;
import com.h2n.nihongorenshuu.extendObject.ExpandableListCbAdapter;
import com.h2n.nihongorenshuu.repo.GrammarRepo;
import com.h2n.nihongorenshuu.repo.HistoryRepo;
import com.h2n.nihongorenshuu.repo.SelectedHistoryRepo;

/**
 * Created by ninhh on 11/13/2016.
 */

public class TransQuizHome extends Activity {

    private ExpandableListView expandableListView;
    private int level;
    private List<CbGrammarUnit> listUnit;
    private HashMap<CbGrammarUnit, List<CbGrammarItem>> hashGrammar;
    private Button btnStart, btnContinue;
    private RadioGroup rgTransOption;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_quiz_home);

        DatabaseHelper dbhelper = new DatabaseHelper();
        try {
            dbhelper.createDatabase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        btnStart = (Button) findViewById(R.id.btnStart);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        rgTransOption = (RadioGroup) findViewById(R.id.rgTransOption);

        initViews();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }


    public void initViews() {
        SelectedHistoryRepo shr = new SelectedHistoryRepo();
        List<Integer> list =  shr.getAllSelectedGrammar();
        if(list != null && list.size() != 0) {
            if(shr.getIsVn2Jp() == 1) {
                ((RadioButton) findViewById(R.id.rbVn2Jp)).setChecked(true);
            } else {
                ((RadioButton) findViewById(R.id.rbJp2Vn)).setChecked(true);
            }
            btnContinue.setVisibility(View.VISIBLE);
            btnStart.setText(getResources().getString(R.string.btnStartOver));

            prepareListDataToContinue(list);
            ((RadioButton) findViewById(R.id.rbVn2Jp)).setEnabled(false);
            ((RadioButton) findViewById(R.id.rbJp2Vn)).setEnabled(false);
            expandableListView = (ExpandableListView) findViewById(R.id.expandableList);
            ExpandableListCbAdapter adapter = new ExpandableListCbAdapter(this, expandableListView, listUnit, hashGrammar, false);
            expandableListView.setAdapter(adapter);
        } else {
            btnContinue.setVisibility(View.INVISIBLE);
            btnStart.setText(getResources().getString(R.string.btnStart));
            ((RadioButton) findViewById(R.id.rbVn2Jp)).setChecked(true);

            prepareListData();
            expandableListView = (ExpandableListView) findViewById(R.id.expandableList);
            ExpandableListCbAdapter adapter = new ExpandableListCbAdapter(this, expandableListView, listUnit, hashGrammar, true);
            expandableListView.setAdapter(adapter);
        }
    }

    public void btnStartOnClick(View view) {
        if(btnStart.getText().equals(getResources().getString(R.string.btnStart))) {
            gotoTransQuiz();
        } else {
            showMessage(getResources().getString(R.string.dialog_title_guide), getResources().getString(R.string.dialog_guide_instruction));
            SelectedHistoryRepo sgr = new SelectedHistoryRepo();
            sgr.deleteAllRecord();
            prepareListData();
            HistoryRepo hr = new HistoryRepo();
            hr.updateContinueRecord();

            ((RadioButton) findViewById(R.id.rbVn2Jp)).setEnabled(true);
            ((RadioButton) findViewById(R.id.rbJp2Vn)).setEnabled(true);
            ((RadioButton) findViewById(R.id.rbVn2Jp)).setChecked(true);
            expandableListView = (ExpandableListView) findViewById(R.id.expandableList);
            ExpandableListCbAdapter adapter = new ExpandableListCbAdapter(this, expandableListView, listUnit, hashGrammar, true);
            expandableListView.setAdapter(adapter);
            btnStart.setText(getResources().getString(R.string.btnStart));
            btnContinue.setVisibility(View.INVISIBLE);
        }
    }

    public void btnContinueOnClick(View view) {
        gotoTranQuizContinue();
    }

    public void gotoTransQuiz() {
        ArrayList<Integer> listSelectedGra = new ArrayList<>();
        SelectedHistoryRepo shr = new SelectedHistoryRepo();
        for(CbGrammarUnit unit : hashGrammar.keySet()) {
            List<CbGrammarItem> listGra = hashGrammar.get(unit);
            for(CbGrammarItem gra : listGra) {
                if(gra.isChecked()) {
                    listSelectedGra.add(gra.getGrammar().getId());
                    shr.insertSeletedGrammar(gra.getGrammar().getId());
                }
            }
        }
        if(listSelectedGra.size() == 0) {
            showMessage(getResources().getString(R.string.dialog_title_warning), getResources().getString(R.string.dialog_warning_select));
        } else {
            Intent i = new Intent(this, TransQuiz.class);
            Bundle b = new Bundle();
            if(rgTransOption.getCheckedRadioButtonId() == R.id.rbVn2Jp){
                b.putInt("isVn2Jp", 1);
                shr.insertIsVn2Jp(1);
            } else {
                b.putInt("isVn2Jp", 0);
                shr.insertIsVn2Jp(0);
            }
//            b.putString("level", level);
            b.putIntegerArrayList("listSelectedGra", listSelectedGra);
            i.putExtras(b);
            startActivity(i);
        }
    }

    public void gotoTranQuizContinue() {
        ArrayList<Integer> listSelectedGra = new ArrayList<>();
        for(CbGrammarUnit unit : hashGrammar.keySet()) {
            List<CbGrammarItem> listGra = hashGrammar.get(unit);
            for(CbGrammarItem gra : listGra) {
                if(gra.isChecked()) {
                    listSelectedGra.add(gra.getGrammar().getId());
                }
            }
        }
        if(listSelectedGra.size() == 0) {
            showMessage(getResources().getString(R.string.dialog_title_warning), getResources().getString(R.string.dialog_warning_select));
        } else {
            Intent i = new Intent(this, TransQuiz.class);
            Bundle b = new Bundle();
            if(rgTransOption.getCheckedRadioButtonId() == R.id.rbVn2Jp){
                b.putInt("isVn2Jp", 1);
            } else {
                b.putInt("isVn2Jp", 0);
            }
//            b.putString("level", level);
            b.putIntegerArrayList("listSelectedGra", listSelectedGra);
            i.putExtras(b);
            startActivity(i);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("TransQuizHome Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        level = 3;
        GrammarRepo gr = new GrammarRepo();
        String query = "WHERE " + Grammar.KEY_Level + " = " + level;
        List<Grammar> listGrammar = gr.getGrammarBySelectQuery(query);
        listUnit = new ArrayList<>();
        hashGrammar = new HashMap<>();
        for(Grammar gra : listGrammar) {
            CbGrammarUnit unit = new CbGrammarUnit(getResources().getString(R.string.unit) + " " + gra.getUnit());
            List<CbGrammarItem> temp = new ArrayList<>();
            if(listUnit.contains(unit)) {
                temp = hashGrammar.get(unit);
                hashGrammar.remove(unit);
            }else {
                listUnit.add(unit);
            }
            CbGrammarItem item = new CbGrammarItem(gra);
            temp.add(item);
            hashGrammar.put(unit, temp);
        }
    }

    /*
     * Preparing the list data
     */
    private void prepareListDataToContinue(List<Integer> listGra) {
        level = 3;
        GrammarRepo gr = new GrammarRepo();
        String query = "WHERE " + Grammar.KEY_Level + " = " + level;
        List<Grammar> listGrammar = gr.getGrammarBySelectQuery(query);
        listUnit = new ArrayList<>();
        hashGrammar = new HashMap<>();
        for(Grammar gra : listGrammar) {
            CbGrammarUnit unit = new CbGrammarUnit(getResources().getString(R.string.unit) + " " + gra.getUnit());
            List<CbGrammarItem> temp = new ArrayList<>();
            if(listUnit.contains(unit)) {
                temp = hashGrammar.get(unit);
                hashGrammar.remove(unit);
            }else {
                listUnit.add(unit);
            }
            if(listGra.contains(gra.getId())) {
                CbGrammarItem item = new CbGrammarItem(gra, true);
                temp.add(item);
            } else {
                CbGrammarItem item = new CbGrammarItem(gra);
                temp.add(item);
            }
            hashGrammar.put(unit, temp);
        }
        for(CbGrammarUnit unit : listUnit) {
            boolean isCheck = true;
            for(CbGrammarItem item : hashGrammar.get(unit)) {
                if(!item.isChecked()) {
                    isCheck = false;
                    break;
                }
            }
            if(isCheck) {
                unit.setChecked(true);
            }
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
