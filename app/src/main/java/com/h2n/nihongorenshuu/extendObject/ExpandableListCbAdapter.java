package com.h2n.nihongorenshuu.extendObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.h2n.nihongorenshuu.R;

/**
 * Created by ninhh on 11/13/2016.
 */

public class ExpandableListCbAdapter extends BaseExpandableListAdapter {

    private LayoutInflater layoutInflater;
    private HashMap<CbGrammarUnit, List<CbGrammarItem>> listData;
    private List<CbGrammarUnit> listGroup;
    private int lastExpandedPosition = -1;
    private boolean editable = true;

    public ExpandableListCbAdapter(Context context, final ExpandableListView listView, List<CbGrammarUnit> listGroup,
                                   HashMap<CbGrammarUnit, List<CbGrammarItem>> listData, boolean editable) {
        layoutInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.listData = listData;
        this.listGroup = listGroup;
        this.editable = editable;

        listView.setOnGroupExpandListener(new OnGroupExpandListener() {

            public void onGroupExpand(int groupPosition) {
                if(lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    listView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        listView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            public void onGroupCollapse(int groupPosition) {
            }
        });
    }

    public CbGrammarItem getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return listData.get(listGroup.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getChildView(final int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_checkbox_item, null);
            childHolder = new ChildHolder();
            childHolder.cb = (CheckBox) convertView.findViewById(R.id.cb);
//            childHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        final CbGrammarItem child = getChild(groupPosition, childPosition);
        childHolder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CbGrammarUnit group = getGroup(groupPosition);
                child.setChecked(isChecked);
                if (isChecked) {
                    List<CbGrammarItem> childList = getChild(group);
                    int childIndex = childList.indexOf(child);
                    boolean isAllChildClicked = true;
                    for(CbGrammarItem item : childList) {
                        if(!item.isChecked()) {
                            isAllChildClicked = false;
                            break;
                        }
                    }

                    if (isAllChildClicked) {
                        group.setChecked(true);
                        checkAll = false;
                    }

                } else {
                    if (group.isChecked()) {
                        group.setChecked(false);
                        checkAll = false;
                    } else {
                        checkAll = true;
                    }
                }
                notifyDataSetChanged();

            }

        });

        childHolder.cb.setChecked(child.isChecked());
        childHolder.cb.setEnabled(editable);
        childHolder.cb.setText(child.getGrammar().getName());
//        childHolder.title = (TextView) convertView.findViewById(R.id.title);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return listData.get(listGroup.get(groupPosition)).size();
    }

    public CbGrammarUnit getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return listGroup.get(groupPosition);
    }

    public int getGroupCount() {
        // TODO Auto-generated method stub
        return listGroup.size();
    }

    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        final GroupHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_checkbox_group, null);
            holder = new GroupHolder();
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        final CbGrammarUnit groupItem = getGroup(groupPosition);

        holder.title.setText(groupItem.getUnit());

        holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (checkAll) {
                    List<CbGrammarItem> childItem = getChild(groupItem);
                    for (CbGrammarItem children : childItem)
                        children.setChecked(isChecked);
                }
                groupItem.setChecked(isChecked);
                notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        // TODO Auto-generated method stub
                        if (!checkAll)
                            checkAll = true;
                    }
                }, 50);

            }

        });
        holder.cb.setChecked(groupItem.isChecked());
        holder.cb.setEnabled(editable);
        return convertView;
    }

    private boolean checkAll = true;

    private List<CbGrammarItem> getChild(CbGrammarUnit group) {
        return listData.get(group);
    }

    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    private class GroupHolder {
        public CheckBox cb;
        public TextView title;

    }

    private class ChildHolder {
        public TextView title;
        public CheckBox cb;
    }

}

