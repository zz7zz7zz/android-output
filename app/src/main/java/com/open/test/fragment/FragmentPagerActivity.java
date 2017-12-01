package com.open.test.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.test.R;

/**
 * Created by Administrator on 2016/9/2.
 */

public class FragmentPagerActivity extends FragmentActivity {

    static final int [] colorArray = {Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE,Color.BLACK,Color.GRAY};
    MyAdapter mAdapter;
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_statepager);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
    }


    public static class MyAdapter extends FragmentPagerAdapter {
        public static final String TAG = "MyAdapter";
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            Log.v(TAG, "getCount: ");
            return colorArray.length;
        }

        @Override
        public Fragment getItem(int position) {
            Log.v(TAG, "getItem: " + position);
            return MyFragment.newInstance(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.v(TAG, "destroyItem: "+position);
            super.destroyItem(container, position, object);

        }

        @Override
        public void finishUpdate(ViewGroup container) {
            Log.v(TAG, "finishUpdate: ");
            super.finishUpdate(container);

        }

        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            Log.v(TAG, "instantiateItem position : " + arg1);
            return super.instantiateItem(arg0, arg1);

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            Log.v(TAG, "isViewFromObject: ");
            return super.isViewFromObject(view, object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            Log.v(TAG, "restoreState: ");
            super.restoreState(arg0, arg1);
        }

        @Override
        public Parcelable saveState() {
            Log.v(TAG, "saveState: ");
            return super.saveState();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            Log.v(TAG, "setPrimaryItem: "+position);
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            Log.v(TAG, "startUpdate: ");
            super.startUpdate(container);
        }
    }

    public static class MyFragment extends BaseFragment{

        int mNum;

        static MyFragment newInstance(int num) {
            MyFragment f = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            TextView v = new TextView(getContext());
            v.setText("Fragment #" + mNum);
            v.setTextColor(Color.WHITE);
            v.setBackgroundColor(colorArray[mNum]);
            return v;
        }
    }
}
