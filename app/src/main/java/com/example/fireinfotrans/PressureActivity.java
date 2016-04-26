package com.example.fireinfotrans;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fireinfotrans.model.PressureNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlesyoung on 2016/4/23.
 */
public class PressureActivity extends Activity {

    private List<PressureNode> mPressureNodeList = new ArrayList<PressureNode>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure);
        initPressureNodes();
        PressureAdapter adapter = new PressureAdapter(PressureActivity.this,
                R.layout.activity_pressure_item,mPressureNodeList);
        ListView listView = (ListView) findViewById(R.id.list_pressure);
        listView.setAdapter(adapter);
    }

    public void initPressureNodes(){
       /* PressureNode node1 = new PressureNode(10011002,25);
        mPressureNodeList.add(node1);
        PressureNode node2 = new PressureNode(10011003,24);
        mPressureNodeList.add(node2);
        PressureNode node3 = new PressureNode(10011004,23);
        mPressureNodeList.add(node3);
        PressureNode node4 = new PressureNode(10011005,22);
        mPressureNodeList.add(node4);
        PressureNode node5 = new PressureNode(10011006,21);
        mPressureNodeList.add(node5);
        PressureNode node6 = new PressureNode(10011007,20);
        mPressureNodeList.add(node6);
        PressureNode node7 = new PressureNode(10011007,20);
        mPressureNodeList.add(node7);
        PressureNode node8 = new PressureNode(10011007,20);
        mPressureNodeList.add(node8);
        PressureNode node9 = new PressureNode(10011007,20);
        mPressureNodeList.add(node9);
        PressureNode node10 = new PressureNode(10011007,20);
        mPressureNodeList.add(node10);
        */

    }
    public class PressureAdapter extends ArrayAdapter<PressureNode>{

        private int resourceId;

        public PressureAdapter(Context context, int textViewResourceId, List<PressureNode> objects){
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PressureNode pressureNode = getItem(position);
            View view;
            if(convertView == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            } else{
                view = convertView;
            }
            TextView textView = (TextView) view.findViewById(R.id.text_node_id);
            textView.setText(Integer.toString(pressureNode.getPressureId()));
            return view;
        }
    }
}
