package com.h2n.nihongorenshuu.extendObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.h2n.nihongorenshuu.R;
import com.h2n.nihongorenshuu.app.grammar.GrammarDetail;
import com.h2n.nihongorenshuu.app.grammar.Home;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.entity.Sentence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ninhh on 11/18/2016.
 */

public class GrammarDetailContainer extends BaseExpandableListAdapter
{
    private Context context;
    ExpandableListView explvlist;
    HashMap<String, HashMap<String, List<Sentence>>> strucData;
    List<String> listStruc;

    public GrammarDetailContainer(Context context, HashMap<String, HashMap<String, List<Sentence>>> strucData, List<String> listStruc) {
        this.context = context;
        this.strucData = strucData;
        this.listStruc = listStruc;
    }

    @Override
    public Object getChild(int arg0, int arg1)
    {
        return arg1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent)
    {
        ExplainList explainListExpandable = new ExplainList(context);
        HashMap<String, List<Sentence>> listExplain = strucData.get(listStruc.get(groupPosition));
        explainListExpandable.setAdapter(new ExplainListAdapter(listExplain, context));
        explainListExpandable.setGroupIndicator(null);
        return explainListExpandable;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public int getGroupCount()
    {
        return listStruc.size();
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
        TextView tv = new TextView(context);
        tv.setText(listStruc.get(groupPosition));
        tv.setBackgroundColor(Color.BLUE);
        tv.setPadding(10, 7, 7, 7);
        return tv;
    }

    public class ExplainListAdapter extends BaseExpandableListAdapter
    {
        Context context;
        HashMap<String, List<Sentence>> explainData = new HashMap<>();
        List<String> listExplain = new ArrayList<>();

        public ExplainListAdapter(HashMap<String, List<Sentence>> explainData, Context context){
            this.context = context;
            this.explainData = explainData;
            List<String> temp = new ArrayList<>(explainData.keySet());
            for(int i = temp.size()-1; i>=0; i--) {
                listExplain.add(temp.get(i));
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return childPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
        {
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.sentence, null);
            }
            TextView txtListChild = (TextView) convertView.findViewById(R.id.tvSen);
            txtListChild.setText(explainData.get(listExplain.get(groupPosition)).get(childPosition).getJpSentence());
//            TextView txtListChild2 = (TextView) convertView.findViewById(R.id.tvSen2);
//            txtListChild2.setText(explainData.get(listExplain.get(groupPosition)).get(childPosition).getVnSentence());
//            txtListChild.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    txtListChild.setText(explainData.get(listExplain.get(groupPosition)).get(childPosition).getVnSentence());
//                }
//            });
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            return explainData.get(listExplain.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            return groupPosition;
        }

        @Override
        public int getGroupCount()
        {
            return listExplain.size();
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
            TextView tv = new TextView(context);
            tv.setText(listExplain.get(groupPosition));
            tv.setPadding(15, 7, 7, 7);
            tv.setBackgroundColor(Color.RED);

            return tv;
        }

        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }

    }

    public class ExplainList extends ExpandableListView
    {

//        int intGroupPosition, intChildPosition, intGroupid;


        public ExplainList(Context context)
        {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(960, MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(600, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
