package com.h2n.nihongorenshuu.app.transquiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.extendObject.CbGrammarItem;
import com.h2n.nihongorenshuu.extendObject.CbGrammarUnit;
import com.h2n.nihongorenshuu.extendObject.ExpandableListCbAdapter;
import com.h2n.nihongorenshuu.repo.GrammarRepo;

/**
 * Created by ninhh on 11/13/2016.
 */

public class TransQuizHome extends Activity {

    private ExpandableListView expandableListView;
    private int level;
    private List<CbGrammarUnit> listUnit;
    private HashMap<CbGrammarUnit, List<CbGrammarItem>> hashGrammar;
    private Button btnStart;
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
        initViews();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        btnStart = (Button) findViewById(R.id.btnStart);
    }


    private void initViews() {

        prepareListData();
        expandableListView = (ExpandableListView) findViewById(R.id.expandableList);
        ExpandableListCbAdapter adapter = new ExpandableListCbAdapter(this, expandableListView, listUnit, hashGrammar);
        expandableListView.setAdapter(adapter);

    }

    public void btnStartOnClick(View view) {
        if(btnStart.getText().equals(getResources().getString(R.string.btnStart))) {
            ArrayList<Integer> listSelectedGra = new ArrayList<>();
            for(CbGrammarUnit unit : hashGrammar.keySet()) {
                List<CbGrammarItem> listGra = hashGrammar.get(unit);
                for(CbGrammarItem gra : listGra) {
                    if(gra.isChecked()) {
                        listSelectedGra.add(gra.getGrammar().getId());
                    }
                }
            }



            Intent i = new Intent(this, TransQuiz.class);
            Bundle b = new Bundle();
//            b.putString("level", level);
            b.putInt("isJpToVn", 1);
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
            List<CbGrammarItem> temp = new ArrayList<CbGrammarItem>();
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
}
