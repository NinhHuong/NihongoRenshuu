package com.h2n.nihongorenshuu.extendObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.app.grammar.GrammarDetail;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.entity.GrammarExplain;
import com.h2n.nihongorenshuu.entity.GrammarStructure;
import com.h2n.nihongorenshuu.entity.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ninhh on 11/18/2016.
 */

public class GrammarStructureAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private final List<GrammarStructure> listStrucHeader;
    private final Map<String, List<GrammarExplain>> explainData;
    private final Map<String, List<Sentence>> sentenceData;
    public GrammarStructureAdapter(Context mContext, List<GrammarStructure> listStrucHeader, Map<String, List<GrammarExplain>> explainData,
                                   Map<String, List<Sentence>> sentenceData) {
        this.mContext = mContext;
        this.listStrucHeader = listStrucHeader;
        this.explainData = explainData;
        this.sentenceData = sentenceData;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final CustomExpListView secondLevelExpListView = new CustomExpListView(this.mContext, convertView);
        final String parentNode = String.valueOf(((GrammarStructure) getGroup(groupPosition)).getId());
        secondLevelExpListView.setAdapter(new GrammarExplainAdapter(this.mContext, explainData.get(parentNode), sentenceData));
        secondLevelExpListView.setGroupIndicator(null);

        secondLevelExpListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String seletectText = ((TextView) v).getText().toString().toLowerCase();
                Sentence sen = sentenceData.get(String.valueOf(explainData.get(parentNode).get(groupPosition).getId())).get(childPosition);
                if(seletectText.trim().equals(sen.getJpSentence().trim())) {
                    ((TextView) v).setText(sen.getVnSentence());
                } else {
                    ((TextView) v).setText(sen.getJpSentence());
                }
                return false;
            }
        });
        return secondLevelExpListView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this.listStrucHeader.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return this.listStrucHeader.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = ((GrammarStructure) getGroup(groupPosition)).getStructure();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.gra_detail_structure, parent, false);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.strucHeader);
        ImageView struc_indicator = (ImageView) convertView.findViewById(R.id.struc_indicator);
        if(explainData.get(String.valueOf(listStrucHeader.get(groupPosition).getId())).size() == 0) {
            struc_indicator.setVisibility(View.INVISIBLE);
        }
        if(((GrammarStructure) getGroup(groupPosition)).getNote()) {
            lblListHeader.setText(mContext.getResources().getString(R.string.note) + ":\n" + headerTitle);
        } else if(listStrucHeader.size() == 1) {
            lblListHeader.setText(mContext.getResources().getString(R.string.structure) + ":\n" + headerTitle);
        } else {
            lblListHeader.setText(mContext.getResources().getString(R.string.structure) + " " +
                    String.valueOf(groupPosition + 1) + ":\n" + headerTitle);
        }

        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //

    public class GrammarExplainAdapter extends BaseExpandableListAdapter
    {
        private final Context mContext;
        private final List<GrammarExplain> listExplainHeader;
        private final Map<String, List<Sentence>> sentenceData;
        public GrammarExplainAdapter(Context mContext, List<GrammarExplain> listExplainHeader, Map<String, List<Sentence>> sentenceData) {
            this.mContext = mContext;
            this.listExplainHeader = listExplainHeader;
            this.sentenceData = sentenceData;
        }
        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return this.sentenceData.get(String.valueOf(this.listExplainHeader.get(groupPosition).getId()))
                    .get(childPosition);
        }
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent)
        {
            final String childText = ((Sentence) getChild(groupPosition, childPosition)).getJpSentence();
            TextView txtListChild = new TextView(mContext);
            txtListChild.setTextSize(14);
            txtListChild.setTextColor(mContext.getResources().getColor(R.color.sentence));
            txtListChild.setPadding(70, 0, 0, 0);
            txtListChild.setText(childText);
            return txtListChild;
        }
        @Override
        public int getChildrenCount(int groupPosition)
        {
            try {
                return this.sentenceData.get(String.valueOf(this.listExplainHeader.get(groupPosition).getId())).size();
            } catch (Exception e) {
                return 0;
            }
        }
        @Override
        public Object getGroup(int groupPosition)
        {
            return this.listExplainHeader.get(groupPosition);
        }
        @Override
        public int getGroupCount()
        {
            return this.listExplainHeader.size();
        }
        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent)
        {
            String headerTitle = ((GrammarExplain) getGroup(groupPosition)).getExplaination();
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.gra_detail_explain, parent, false);
            }
            TextView lblListHeader = (TextView) convertView.findViewById(R.id.explainHeader);
            ImageView explain_indicator = (ImageView) convertView.findViewById(R.id.explain_indicator);
            if(getChildrenCount(groupPosition) == 0) {
                explain_indicator.setVisibility(View.INVISIBLE);
            }
            if(((GrammarExplain) getGroup(groupPosition)).getNote()) {
                lblListHeader.setText(mContext.getResources().getString(R.string.note) + ":\n" + headerTitle);
            } else if(listExplainHeader.size() == 1) {
                lblListHeader.setText(mContext.getResources().getString(R.string.explain) + ":\n" + headerTitle);
            } else {
                lblListHeader.setText(mContext.getResources().getString(R.string.explain) + " " +
                        String.valueOf(groupPosition + 1) + ":\n" + headerTitle);
            }
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setTextSize(14);
            return convertView;
        }
        @Override
        public boolean hasStableIds() {
            return true;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    //
    public class CustomExpListView extends ExpandableListView
    {
        private View view;
        public CustomExpListView(Context context, View view)
        {
            super(context);
            this.view = view;
        }
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(470, MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(960, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}