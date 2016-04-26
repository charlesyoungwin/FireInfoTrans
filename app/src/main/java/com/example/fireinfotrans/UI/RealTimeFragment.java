package com.example.fireinfotrans.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fireinfotrans.PressureActivity;
import com.example.fireinfotrans.R;

/**
 * Created by charlesyoung on 2016/4/22.
 */
public class RealTimeFragment extends Fragment implements View.OnClickListener{

    private Button mButtonPressureNode;
    private Button mButtonPowerNode;
    private Button mButtonSwitchNode;
    private Button mButtonCabinetData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab01,container,false);

        mButtonPressureNode = (Button) v.findViewById(R.id.button_pressure_node);
        mButtonPowerNode = (Button) v.findViewById(R.id.button_power_node);
        mButtonSwitchNode = (Button) v.findViewById(R.id.button_switch_node);
        mButtonCabinetData = (Button) v.findViewById(R.id.button_cabinet_data);

        mButtonPressureNode.setOnClickListener(this);
        mButtonPowerNode.setOnClickListener(this);
        mButtonSwitchNode.setOnClickListener(this);
        mButtonCabinetData.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_pressure_node:
                Intent intent = new Intent(getActivity(), PressureActivity.class);
                startActivity(intent);
                break;
            case R.id.button_power_node:

                break;
            case R.id.button_switch_node:

                break;
            case R.id.button_cabinet_data:

                break;
            default:
                break;
        }
    }

}
