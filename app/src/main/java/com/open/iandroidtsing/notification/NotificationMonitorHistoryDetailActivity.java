package com.open.iandroidtsing.notification;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.iandroidtsing.R;
import com.open.iandroidtsing.com.open.frame.SharedPreConfig;
import com.open.iandroidtsing.com.open.frame.SharedPreUtil;
import com.open.widgets.listview.IListView;
import com.open.widgets.listview.IPullCallBacks;
import com.open.widgets.recyclerview.BaseRecyclerAdapter;
import com.open.widgets.recyclerview.DividerLinearItemDecoration;
import com.open.widgets.recyclerview.IRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/18.
 */

public class NotificationMonitorHistoryDetailActivity extends Activity implements IPullCallBacks.IPullCallBackListener{

    private static final String TAG = "HistoryDetail";

    //-------------UI-------------
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration decor;
    private IRecyclerView mIRecyclerView;

    //-------------DATA-------------
    private Handler mHandler = new Handler();

    private String date;
    private ArrayList<NotificationMonitorResultBeaen> dateList;
    private IAdapter mIAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_monitor_history);

        date = getIntent().getStringExtra("date");

        mIRecyclerView  = (IRecyclerView)findViewById(R.id.listView);
        linearLayout();
    }

    @Override
    protected void onDestroy() {
        mIRecyclerView.release();
        super.onDestroy();
    }

    private void linearLayout(){

        if(null != decor){
            mIRecyclerView.removeItemDecoration(decor);
        }
        mIRecyclerView.setItemAnimator(null);

        mLayoutManager      = new LinearLayoutManager(getApplicationContext());
        decor = new DividerLinearItemDecoration(mLayoutManager.canScrollVertically() ? DividerLinearItemDecoration.ORIENTATION_VERTICAL : DividerLinearItemDecoration.ORIENTATION_HORIZONTAL,
                ContextCompat.getDrawable(getApplicationContext(),R.drawable.linear_itemdecoration),false,true);

        dateList = new ArrayList<>();
        mIAdapter       = new IAdapter(getApplicationContext(), dateList);

        mIRecyclerView.setLayoutManager(mLayoutManager);
        mIRecyclerView.addItemDecoration(decor);
        mIRecyclerView.setAdapter(mIAdapter);

        mIRecyclerView.setPullCallBackListener(this);
        mIRecyclerView.startPullDownLoading();
    }


    @Override
    public void onPullDown() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String fileName = String.format("%s_%s", SharedPreConfig.NOTIFICATION_MONITOR_HISTORY,date);
                Map<String,String> historyMap = (Map<String, String>) SharedPreUtil.getAll(getApplicationContext(), fileName);
                if(null != historyMap){
                    //这里将map.entrySet()转换成list
                    List<Map.Entry<String,String>> list = new ArrayList<>(historyMap.entrySet());
                    //然后通过比较器来实现排序
                    Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
                        //升序排序
                        public int compare(Map.Entry<String, String> o1,
                                           Map.Entry<String, String> o2) {
                            return o2.getKey().compareTo(o1.getKey());
                        }

                    });

                    for(Map.Entry<String,String> mapping:list){
                        Log.v(TAG,mapping.getKey()+":"+mapping.getValue());

                        NotificationMonitorResultBeaen mNotificationMonitorResultBeaen = new NotificationMonitorResultBeaen();
                        mNotificationMonitorResultBeaen.parse(mapping.getValue());
                        dateList.add(mNotificationMonitorResultBeaen);
                    }
                }

                //模拟网络回调
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIRecyclerView.stopPullLoading(IListView.STATUS_PULL_DOWN,mPullDownCallBack);
                    }
                },1);
            }
        }).start();

    }

    @Override
    public void onPullUp() {
        //模拟网络回调
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIRecyclerView.stopPullLoading(IListView.STATUS_PULL_UP,null);
            }
        },2000);
    }


    private Runnable mPullDownCallBack = new Runnable() {
        @Override
        public void run() {
            mIAdapter.notifyDataSetChanged();
        }
    };

    //------------------------------Adapter------------------------------
    public class IAdapter extends BaseRecyclerAdapter {

        private static final String  TAG = "IAdapter";

        private Context mContext;
        private ArrayList<NotificationMonitorResultBeaen> dateList;
        private LayoutInflater mLayoutInflater;

        public IAdapter(Context mContext, ArrayList<NotificationMonitorResultBeaen> bindDataList) {
            this.mContext = mContext;
            this.dateList = bindDataList;
            this.mLayoutInflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.v(TAG,"onBindViewHolder " + position + " text"+ dateList.get(position));
            TitleCountHolder realHolder = (TitleCountHolder)holder;
            realHolder.push_info.setText(dateList.get(position).toString2());
            realHolder.push_info.setTextColor(Color.BLUE);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.v(TAG,"onCreateViewHolder " + viewType);
            return new TitleCountHolder(mLayoutInflater.inflate(R.layout.notification_monitor_dateitem,parent,false));
        }

        @Override
        public int getItemCount() {
            return dateList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }


    public class TitleCountHolder extends RecyclerView.ViewHolder{
        private TextView push_info;

        public TitleCountHolder(View itemView) {
            super(itemView);
            push_info   = (TextView)itemView.findViewById(R.id.push_info);
        }
    }
}
