package com.h2n.nihongorenshuu.app.grammar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

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

public class GrammarListReview extends AppCompatActivity {
//    DatabaseHelper dh = new DatabaseHelper(this);

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    TextView gr_detail_title;
    private int lastExpandedPosition = -1;
    private List<String> listUnit;
    private HashMap<String, List<Grammar>> hashGrammar;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_list_view);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        gr_detail_title = (TextView) findViewById(R.id.gr_list_title);

        prepareListData();
        gr_detail_title.setText(getResources().getString(R.string.gr_list_title) + " " + level);

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
                Intent i = new Intent(GrammarListReview.this, GrammarDetail.class);
                Bundle b = new Bundle();
                b.putInt("grammar_id", ((Grammar) hashGrammar.get(listUnit.get(groupPosition)).get(childPosition)).getId());
                i.putExtras(b);
                startActivity(i);
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
        String query = "WHERE " + Grammar.KEY_Level + " = " + level;
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
