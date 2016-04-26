package com.example.fireinfotrans.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fireinfotrans.R;

/**
 * Created by charlesyoung on 2016/4/22.
 */
public class HistoricalDataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab02,container,false);

        return v;
    }
}
