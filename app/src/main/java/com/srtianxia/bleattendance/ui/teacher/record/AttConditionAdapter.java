package com.srtianxia.bleattendance.ui.teacher.record;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by srtianxia on 2017/1/20.
 */

public class AttConditionAdapter extends BaseExpandableListAdapter {
    private String[] mParentTitle;
    private List<List<String>> mChildContent;
//    private Context mContext;


    public AttConditionAdapter(/*Context context,*/ String[] parentTitle, List<List<String>> childContent) {
//        this.mContext = context;
        this.mParentTitle = parentTitle;
        this.mChildContent = childContent;
    }

    @Override
    public int getGroupCount() {
        return mParentTitle.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildContent.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParentTitle[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildContent.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_parent, null);
            holder = new ParentHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ParentHolder) convertView.getTag();
        }
        holder.setData(mParentTitle[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_child, null);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.setData(mChildContent.get(groupPosition).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

//    public void


    static class ParentHolder {
        @BindView(R.id.tv_parent_title)
        TextView tvParentTitle;

        public ParentHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void setData(String text) {
            tvParentTitle.setText("" + text);
        }
    }


    static class ChildHolder {
        @BindView(R.id.tv_child_content)
        TextView tvChildContent;

        public ChildHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void setData(String text) {
            tvChildContent.setText(" " + text);
        }
    }
}
