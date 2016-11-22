package com.h2n.nihongorenshuu.extendObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.app.grammar.GrammarDetail;
import com.h2n.nihongorenshuu.entity.Grammar;
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
    private final List<String> listStrucHeader;
    private final Map<String, List<String>> explainData;
    private final Map<String, List<Sentence>> sentenceData;
    public GrammarStructureAdapter(Context mContext, List<String> listStrucHeader, Map<String, List<String>> explainData,
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
        final String parentNode = (String) getGroup(groupPosition);
        secondLevelExpListView.setAdapter(new GrammarExplainAdapter(this.mContext, explainData.get(parentNode), sentenceData));
        secondLevelExpListView.setGroupIndicator(null);

        secondLevelExpListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String seletectText = ((TextView) v).getText().toString().toLowerCase();
                List<String> listExplainHeader = new ArrayList<String>(explainData.keySet());
                Sentence sen = sentenceData.get(explainData.get(parentNode).get(groupPosition)).get(childPosition);
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
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.gra_detail_structure, parent, false);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.strucHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        if(listStrucHeader.size() == 1) {
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
        private final List<String> listExplainHeader;
        private final Map<String, List<Sentence>> sentenceData;
        public GrammarExplainAdapter(Context mContext, List<String> listExplainHeader, Map<String, List<Sentence>> sentenceData) {
            this.mContext = mContext;
            this.listExplainHeader = listExplainHeader;
            this.sentenceData = sentenceData;
        }
        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return this.sentenceData.get(this.listExplainHeader.get(groupPosition))
                    .get(childPosition).getJpSentence();
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
            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.gra_detail_sentence, parent, false);
            }
            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.sentence);
            txtListChild.setTextSize(14);
            txtListChild.setText(childText);
            return convertView;
        }
        @Override
        public int getChildrenCount(int groupPosition)
        {
            try {
                return this.sentenceData.get(this.listExplainHeader.get(groupPosition)).size();
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
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.gra_detail_explain, parent, false);
            }
            TextView lblListHeader = (TextView) convertView.findViewById(R.id.explainHeader);
            if(listExplainHeader.size() == 1) {
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
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
