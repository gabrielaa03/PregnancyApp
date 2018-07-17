package com.gabrielaangebrandt.pregnancyapp.forumActivity.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.newQuestionActivity.view.NewAnswerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader = new ArrayList<>();
    private Map<String, List<Map<String, String>>> listDataChild = new HashMap<>();

    public ExpandableListAdapter(Context context, List<String> listDataHeader, Map<String, List<Map<String, String>>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosititon);
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
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Map<String, String> maps = (Map<String, String>) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_answer, null);
        }
        if (maps != null) {
            TextView username = (TextView) convertView.findViewById(R.id.tv_username);
            TextView text = (TextView) convertView.findViewById(R.id.tv_answer);
            TextView date = (TextView) convertView.findViewById(R.id.tv_date);
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                switch (entry.getKey()) {
                    case "date":
                        date.setText(entry.getValue());
                        break;
                    case "username":
                        username.setText(entry.getValue());
                        break;
                    case "answer":
                        text.setText(entry.getValue());
                        break;
                }
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(listDataChild.containsKey(listDataHeader.get(groupPosition)))
            return listDataChild.get(listDataHeader.get(groupPosition)).size();
        else
            return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View
            convertView, ViewGroup parent) {
        final String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_question, null);
        }

        TextView header = convertView.findViewById(R.id.tv_question_title);
        Button buttonForAnswering = convertView.findViewById(R.id.btn_answer);
        header.setText(headerTitle);
        buttonForAnswering.setHeight(header.getHeight());
        buttonForAnswering.setFocusable(false);
        buttonForAnswering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, NewAnswerActivity.class).putExtra("question", headerTitle));
            }
        });
        return convertView;
    }
}
