package com.h2n.nihongorenshuu.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.h2n.nihongorenshuu.MainActivity;
import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.extendObject.ExpandableListAdapter;
import com.h2n.nihongorenshuu.repo.GrammarRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ninhh on 11/8/2016.
 */

public class GrammarListReview extends Activity {
//    DatabaseHelper dh = new DatabaseHelper(this);

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    private int lastExpandedPosition = -1;
    List<String> listUnit;
    HashMap<String, List<Grammar>> hashGrammar;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grammar_list_review);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listUnit, hashGrammar);
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
               return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listUnit.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }



    /*
     * Preparing the list data
     */
    private void prepareListData() {
        level = 3;
        GrammarRepo gr = new GrammarRepo();
        String query = "SELECT * FROM " + Grammar.TABLE + " WHERE " + Grammar.KEY_Level + " = " + level;
        List<Grammar> listGrammar = gr.getGrammarBySelectQuery(query);
        listUnit = new ArrayList<String>();
        hashGrammar = new HashMap<String, List<Grammar>>();
        for(Grammar gra : listGrammar) {
            String unit = getResources().getString(R.string.unit) + " " + gra.getUnit();
            List<Grammar> temp = new ArrayList<Grammar>();
            if(hashGrammar != null && hashGrammar.containsKey(unit)) {
                temp = hashGrammar.get(unit);
                hashGrammar.remove(unit);
            }else {
                listUnit.add(unit);
            }
            temp.add(gra);
            hashGrammar.put(unit, temp);
        }
    }
}
