package com.example.fireinfotrans;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;


    private LinearLayout mRealTime;
    private LinearLayout mHistoricalData;
    private LinearLayout mAlarmStatistics;
    private LinearLayout mLinkDevice;

    private Button mButtonRealTime;
    private Button mButtonHistoricData;
    private Button mButtonAlarmStatistics;
    private Button mButtonLinkDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initEvent();

        setSelect(1);
    }

    private void initEvent(){
        mRealTime.setOnClickListener(this);
        mHistoricalData.setOnClickListener(this);
        mAlarmStatistics.setOnClickListener(this);
        mLinkDevice.setOnClickListener(this);

    }

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mRealTime = (LinearLayout)findViewById(R.id.real_time);
        mHistoricalData = (LinearLayout) findViewById(R.id.historic_data);
        mAlarmStatistics = (LinearLayout)findViewById(R.id.alarm_statistics);
        mLinkDevice = (LinearLayout) findViewById(R.id.link_device);

        mButtonRealTime = (Button) findViewById(R.id.button_real_time);
        mButtonHistoricData = (Button) findViewById(R.id.button_historic_data);
        mButtonAlarmStatistics = (Button) findViewById(R.id.button_alarm_statistics);
        mButtonLinkDevice = (Button) findViewById(R.id.button_link_device);

        mFragments = new ArrayList<Fragment>();
        Fragment mTab01 = new RealTimeFragment();
        Fragment mTab02 = new HistoricalDataFragment();
        Fragment mTab03 = new AlarmStatisticsFragment();
        Fragment mTab04 = new LinkDeviceFragment();
        mFragments.add(mTab01);
        mFragments.add(mTab02);
        mFragments.add(mTab03);
        mFragments.add(mTab04);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {

            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg0)
            {
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {

            }
        });

    }

    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.real_time:
                setSelect(0);
                break;
            case R.id.historic_data:
                setSelect(1);
                break;
            case R.id.alarm_statistics:
                setSelect(2);
                break;
            case R.id.link_device:
                setSelect(3);
                break;

            default:
                break;
        }

    }

    private void setSelect(int i)
    {
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    private void setTab(int i)
    {

        reset();
        //设置被选中的tab改变颜色
        switch (i)
        {
            case 0:
                mButtonRealTime.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case 1:
                mButtonHistoricData.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case 2:
                mButtonAlarmStatistics.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case 3:
                mButtonLinkDevice.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
        }
    }

    /**
     * 重置设置
     */
    private void reset(){
        mButtonRealTime.setBackgroundColor(Color.parseColor("#3F51B5"));
        mButtonHistoricData.setBackgroundColor(Color.parseColor("#3F51B5"));
        mButtonAlarmStatistics.setBackgroundColor(Color.parseColor("#3F51B5"));
        mButtonLinkDevice.setBackgroundColor(Color.parseColor("#3F51B5"));
    }
}
