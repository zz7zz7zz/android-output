package com.open.iandroidtsing.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import com.open.iandroidtsing.com.open.frame.SharedPreConfig;
import com.open.iandroidtsing.com.open.frame.SharedPreUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/11.
 */
@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService {

    public static final String TAG = "NotificationMonitor";

    public static final String NOTIFICATION_MONITOR_ACTION_CANCEL = "com.open.iandroidtsing.notification.monitor.cancel";
    public static final String NOTIFICATION_MONITOR_ACTION_CANCEL_CMD = "cmd";
    public static final int NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_ALL          = 1;
    public static final int NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_LATEST       = 2;
    public static final int NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_GETALLNFS    = 3;

    //需要过滤的包名，完全匹配
    private final String [] exactStringMatchingPkgArray = new String[]{
            "android"
    };

    //需要过滤的包名，模糊匹配
    private final String [] likeStringMatchingPkgArray = new String[]{
            "com.android","com.google.android"
    };

    private CancelNotificationBroadcastReceiver mReceiver = new CancelNotificationBroadcastReceiver();

    public void onCreate()
    {
        super.onCreate();
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction(NOTIFICATION_MONITOR_ACTION_CANCEL);
        registerReceiver(this.mReceiver, localIntentFilter);
    }

    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(this.mReceiver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {


        Log.v(TAG, "onNotificationPosted A :  "+sbn.toString());

        Notification nf = sbn.getNotification();
        if(null != nf){

            NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
            resultBeaen.id = sbn.getId();
            resultBeaen.pkg = sbn.getPackageName();

            Bundle extras = nf.extras;
            if(null != extras){
                resultBeaen.title = extras.getString(Notification.EXTRA_TITLE);
                resultBeaen.content = extras.getString(Notification.EXTRA_TEXT);
                resultBeaen.subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                resultBeaen.showWhen = sbn.getPostTime() != 0 ? sbn.getPostTime() : nf.when;
            }

            Log.v(TAG, "onNotificationPosted B :  "+resultBeaen.toString());

            //存数据
            saveToHistory(resultBeaen);

            try{
                if(null == nf.contentView){
                    broadcastAdd(resultBeaen);
                }else{
                    broadcastAdd2(resultBeaen , nf);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        super.onNotificationPosted(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);

        Log.v(TAG, "onNotificationRemoved : "+sbn.toString());

        Notification nf = sbn.getNotification();
        if(null != nf){

            NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
            resultBeaen.id = sbn.getId();
            resultBeaen.pkg = sbn.getPackageName();

            Bundle extras = nf.extras;
            if(null != extras){
                resultBeaen.title = extras.getString(Notification.EXTRA_TITLE);
                resultBeaen.content = extras.getString(Notification.EXTRA_TEXT);
                resultBeaen.subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                resultBeaen.showWhen = sbn.getPostTime() != 0 ? sbn.getPostTime() : nf.when;
            }

            Log.v(TAG, "onNotificationRemoved : "+resultBeaen.toString());
            broadcastRemove(resultBeaen);
        }

    }

    private boolean exactStringMatching(String pkg){

        if(!TextUtils.isEmpty(pkg)){
            for (String item: exactStringMatchingPkgArray) {
                if(pkg.equals(item)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean likeStringMatching(String pkg){

        if(!TextUtils.isEmpty(pkg)){
            for (String item: likeStringMatchingPkgArray) {
                if(pkg.contains(item)){
                    return true;
                }
            }
        }

        return false;
    }

    public void broadcastAdd(NotificationMonitorResultBeaen resultBeaen){

        boolean filterPkg = exactStringMatching(resultBeaen.pkg) || likeStringMatching(resultBeaen.pkg);
        Log.v(TAG, "filterPkg : " + filterPkg);
        if(filterPkg){
            return;
        }

        if(null == resultBeaen){
            Log.v(TAG, "A resultBeaen null ");
            return;
        }

        Bundle mBundle = new Bundle();
        mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ADD);
        mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,resultBeaen);
        Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
        intent.putExtras(mBundle);
        sendBroadcast(intent);
    }

    public void broadcastAdd2(NotificationMonitorResultBeaen resultBeaen , Notification nf){

        boolean filterPkg = exactStringMatching(resultBeaen.pkg) || likeStringMatching(resultBeaen.pkg);
        Log.v(TAG, "filterPkg : " + filterPkg);
        if(filterPkg){
            return;
        }

        if(null == resultBeaen){
            Log.v(TAG, "B resultBeaen null ");
            return;
        }

        Bundle mBundle = new Bundle();
        mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ADD_2);
        mBundle.putString(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_PKG, resultBeaen.pkg);
        mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,resultBeaen);

        //下面这句将引发Caused by: android.os.TransactionTooLargeException:
//        mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA_2,nf);
        NotificationMonitorActivity.mCurrentNotification = nf;

        Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
        intent.putExtras(mBundle);
        sendBroadcast(intent);
    }

    public void broadcastRemove(NotificationMonitorResultBeaen resultBeaen){

        boolean filterPkg = exactStringMatching(resultBeaen.pkg) || likeStringMatching(resultBeaen.pkg);
        Log.v(TAG, "filterPkg : " + filterPkg);
        if(filterPkg){
            return;
        }

        if(null == resultBeaen){
            Log.v(TAG, "A resultBeaen null ");
            return;
        }

        Bundle mBundle = new Bundle();
        mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_REMOVE);
        mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,resultBeaen);
        Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
        intent.putExtras(mBundle);
        sendBroadcast(intent);
    }

    public void saveToHistory(NotificationMonitorResultBeaen resultBeaen){

        boolean filterPkg = exactStringMatching(resultBeaen.pkg) || likeStringMatching(resultBeaen.pkg);
        Log.v(TAG, "filterPkg : " + filterPkg);
        if(filterPkg){
            return;
        }

        if(null == resultBeaen){
            Log.v(TAG, "C resultBeaen null ");
            return;
        }

        //记录总表（日期+数目）
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());
        int count = SharedPreUtil.getInt(getApplicationContext(), SharedPreConfig.FILENAME_NOTIFICATION_MONITOR_HISTORY,date);
        ++count;
        SharedPreUtil.putInt(getApplicationContext(), SharedPreConfig.FILENAME_NOTIFICATION_MONITOR_HISTORY,date,count);

        resultBeaen.date    = date;
        resultBeaen.indexId = count;
        resultBeaen.snapshootPath = String.format("%s/%s/%d.png",SharedPreConfig.FILENAME_NOTIFICATION_MONITOR_HISTORY,date,count);
        String nfText = resultBeaen.bulld();

        //记录日期对应的具体信息
        String fileName = String.format("%s_%s",SharedPreConfig.FILENAME_NOTIFICATION_MONITOR_HISTORY,date);
        SharedPreUtil.putString(getApplicationContext(), fileName,""+count,nfText);
    }

    public void broadcastAll(ArrayList<NotificationMonitorResultBeaen> resultBeaenList, ArrayList<Notification> mNotificationList){

        Bundle mBundle = new Bundle();
        mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ALLINFO);
        mBundle.putParcelableArrayList(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,resultBeaenList);

        //下面这句将引发Caused by: android.os.TransactionTooLargeException:
//        mBundle.putParcelableArrayList(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA_2,mNotificationList);
        NotificationMonitorActivity.mNotificationList = mNotificationList;

        Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
        intent.putExtras(mBundle);
        sendBroadcast(intent);
    }


    class CancelNotificationBroadcastReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent)
        {
            if ((intent != null) && (intent.getAction() != null) && (intent.getAction().equals(NOTIFICATION_MONITOR_ACTION_CANCEL))) {
                Bundle extras = intent.getExtras();
                if (null != extras) {
                    int cmd = extras.getInt(NOTIFICATION_MONITOR_ACTION_CANCEL_CMD);
                    if(cmd == NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_ALL){
                        NotificationMonitorService.this.cancelAllNotifications();
                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_LATEST){

                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_GETALLNFS){

                        ArrayList<NotificationMonitorResultBeaen> resultBeaenList = null;
                        ArrayList<Notification> mNotificationList = null;

                        StatusBarNotification[] arrayOfStatusBarNotification = getActiveNotifications();
                        if(null != arrayOfStatusBarNotification && arrayOfStatusBarNotification.length > 0){
                            resultBeaenList = new ArrayList<>(arrayOfStatusBarNotification.length);
                            mNotificationList = new ArrayList<>(arrayOfStatusBarNotification.length);

                            HashMap<String ,ArrayList<NotificationMonitorResultBeaen>> cacheRecord = new HashMap<>();
                            for (int i = 0 ;i < arrayOfStatusBarNotification.length;i++){
                                StatusBarNotification sbn = arrayOfStatusBarNotification[i];
                                Notification nf = sbn.getNotification();
                                if(null != nf){

                                    NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
                                    resultBeaen.id = sbn.getId();
                                    resultBeaen.pkg = sbn.getPackageName();

                                    Bundle _extras = nf.extras;
                                    if(null != _extras){
                                        resultBeaen.title = extras.getString(Notification.EXTRA_TITLE);
                                        resultBeaen.content = extras.getString(Notification.EXTRA_TEXT);
                                        resultBeaen.subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                                        resultBeaen.showWhen = sbn.getPostTime() != 0 ? sbn.getPostTime() : nf.when;

                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                                        String date = df.format(resultBeaen.showWhen);

                                        //查询当天的数据进行匹配，根据Id 对date / indexId / snapshootPath 进行赋值
                                        ArrayList<NotificationMonitorResultBeaen> cacheItem = cacheRecord.get(date);
                                        if(null == cacheItem){
                                            cacheItem =  getCacheRecord(date);
                                            if(null != cacheItem){
                                                cacheRecord.put(date,cacheItem);
                                            }
                                        }

                                        if(null != cacheItem){
                                            boolean isFind = false;
                                            for (int j = 0; j < cacheItem.size(); j++) {
                                                if(cacheItem.get(j).id == resultBeaen.id && cacheItem.get(j).showWhen == resultBeaen.showWhen){
                                                    resultBeaen.date = cacheItem.get(j).date;
                                                    resultBeaen.indexId = cacheItem.get(j).indexId;
                                                    resultBeaen.snapshootPath = cacheItem.get(j).snapshootPath;
                                                    isFind = true;
                                                    break;
                                                }
                                            }

                                            if(!isFind){
                                                for (int j = 0; j < cacheItem.size(); j++) {
                                                    if(cacheItem.get(j).id == resultBeaen.id){
                                                        resultBeaen.date = cacheItem.get(j).date;
                                                        resultBeaen.indexId = cacheItem.get(j).indexId;
                                                        resultBeaen.snapshootPath = cacheItem.get(j).snapshootPath;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    resultBeaenList.add(resultBeaen);
                                    mNotificationList.add(nf);
                                }
                            }
                        }

                        broadcastAll(resultBeaenList,mNotificationList);
                    }
                }
            }
        }
    }


    private ArrayList<NotificationMonitorResultBeaen> getCacheRecord(String date){

        String fileName = String.format("%s_%s", SharedPreConfig.FILENAME_NOTIFICATION_MONITOR_HISTORY,date);
        Map<String,String> historyMap = (Map<String, String>) SharedPreUtil.getAll(getApplicationContext(), fileName);
        if(null != historyMap){
            //这里将map.entrySet()转换成list
            List<Map.Entry<String,String>> list = new ArrayList<>(historyMap.entrySet());
            //然后通过比较器来实现排序
            Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
                //升序排序
                public int compare(Map.Entry<String, String> o1,
                                   Map.Entry<String, String> o2) {
                    return Integer.valueOf(o2.getKey()).compareTo(Integer.valueOf(o1.getKey()));
                }

            });

            ArrayList<NotificationMonitorResultBeaen> dateList = new ArrayList<>();
            for(Map.Entry<String,String> mapping:list){
                Log.v(TAG,mapping.getKey()+":"+mapping.getValue());

                NotificationMonitorResultBeaen mNotificationMonitorResultBeaen = new NotificationMonitorResultBeaen();
                mNotificationMonitorResultBeaen.parse(mapping.getValue());

//                String imgPath = SDCardUtil.getDiskFilePath(getApplicationContext(),mNotificationMonitorResultBeaen.snapshootPath);
//                mNotificationMonitorResultBeaen.snapshoot = BitmapFactory.decodeFile(imgPath);

                dateList.add(mNotificationMonitorResultBeaen);
            }

            if(dateList.size() > 0){
                return dateList;
            }
        }

        return null;
    }

}
