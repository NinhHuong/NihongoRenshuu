package com.h2n.nihongorenshuu.app.transquiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.extendObject.CbGrammarItem;
import com.h2n.nihongorenshuu.extendObject.CbGrammarUnit;
import com.h2n.nihongorenshuu.extendObject.ExpandableListCbAdapter;
import com.h2n.nihongorenshuu.extendObject.Item;
import com.h2n.nihongorenshuu.repo.GrammarRepo;

/**
 * Created by ninhh on 11/13/2016.
 */

public class TransQuizHome extends Activity {

    private ExpandableListView expandableListView;
    private int level;
    private List<CbGrammarUnit> listUnit;
    private HashMap<CbGrammarUnit, List<CbGrammarItem>> hashGrammar;
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
        setContentView(R.layout.expandable_list_view);
        initViews();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void initViews() {

        prepareListData();
        expandableListView = (ExpandableListView) findViewById(R.id.lvExp);
        ExpandableListCbAdapter adapter = new ExpandableListCbAdapter(this, expandableListView, listUnit, hashGrammar);
        expandableListView.setAdapter(adapter);

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
        listUnit = new ArrayList<CbGrammarUnit>();
        hashGrammar = new HashMap<CbGrammarUnit, List<CbGrammarItem>>();
        for(Grammar gra : listGrammar) {
//            String unit = getResources().getString(R.string.unit) + " " + gra.getUnit();
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
