package com.open.iandroidtsing.notification;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.open.iandroidtsing.R;
import com.open.iandroidtsing.com.open.frame.FileUtil;
import com.open.iandroidtsing.com.open.frame.SDCardUtil;
import com.open.iandroidtsing.com.open.frame.SharedPreConfig;
import com.open.iandroidtsing.com.open.frame.SharedPreUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/18.
 */

public class NotificationMonitorActivity extends Activity {

    public static final String NOTIFICATION_MONITOR_ACTION = "com.open.iandroidtsing.notification.monitor.response";
    public static final String NOTIFICATION_MONITOR_ACTION_KEY_CMD = "cmd";
    public static final String NOTIFICATION_MONITOR_ACTION_KEY_PKG = "package";
    public static final String NOTIFICATION_MONITOR_ACTION_KEY_DATA = "data";
    public static final String NOTIFICATION_MONITOR_ACTION_KEY_DATA_2 = "data2";
    public static final int NOTIFICATION_MONITOR_ACTION_CMD_ADD     = 1;
    public static final int NOTIFICATION_MONITOR_ACTION_CMD_ADD_2   = 2;
    public static final int NOTIFICATION_MONITOR_ACTION_CMD_REMOVE  = 3;
    public static final int NOTIFICATION_MONITOR_ACTION_CMD_ALLINFO = 4;
    public static final int NOTIFICATION_MONITOR_ACTION_CMD_ACCESSIBILITYSERVICE_BACK = 5;

    private static final String TAG = "NFMonitorActivity";

    private NotificationMonitorBroadcastReceiver mReceiver = new NotificationMonitorBroadcastReceiver();

    private EditText     notification_monitor_api_edittext;
    private IScrollView  notification_monitor_logcat_scrollView;
    private LinearLayout notification_monitor_logcat_set;
    private TextView notification_monitor_logcat_snapshoot;
    private LayoutInflater mInflater;

    private Handler mHandler = new Handler();

    private long scroll_bottom_duration = 10000;//获取快照的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_monitor);
        initView();

        mInflater = LayoutInflater.from(getApplicationContext());

        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction(NOTIFICATION_MONITOR_ACTION);
        registerReceiver(this.mReceiver, localIntentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isNotificationListenerServiceOn = isNotificationListenerServiceOn();
        ((Button)(findViewById(R.id.notification_monitor_authorization))).setTextColor(isNotificationListenerServiceOn ? Color.BLACK: Color.RED);

        boolean isNotificationAccessibilitySettingsOn = isNotificationAccessibilitySettingsOn(getApplicationContext());
        ((Button)(findViewById(R.id.notification_monitor_authorization_accessibility_service))).setTextColor(isNotificationAccessibilitySettingsOn ? Color.BLACK: Color.RED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(this.mReceiver);
    }

    private void initView(){

        notification_monitor_api_edittext = (EditText)findViewById(R.id.notification_monitor_api_edittext);
        notification_monitor_logcat_scrollView = (IScrollView) findViewById(R.id.notification_monitor_logcat_scrollView);
        notification_monitor_logcat_scrollView.setOnScrollStateChanged(new IScrollView.OnScrollStateChanged() {
            @Override
            public void ScrollTop() {
                Log.v(TAG,"ScrollTop");
            }

            @Override
            public void ScrollBottom() {
                Log.v(TAG,"ScrollBottom");
            }
        });
        notification_monitor_logcat_snapshoot = (TextView)findViewById(R.id.notification_monitor_logcat_snapshoot);
        notification_monitor_logcat_set = (LinearLayout) findViewById(R.id.notification_monitor_logcat_set);

        findViewById(R.id.notification_monitor_authorization).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_authorization_accessibility_service).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_allinfo).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_create).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_clear).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_api_setting).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_history).setOnClickListener(clickListener);


        String reportApi = SharedPreUtil.getString(getApplicationContext(), SharedPreConfig.FILENAME_NOTIFICATION_MONITOR_HISTORY_API, SharedPreConfig.API_KEY_REPORT);
        if(!TextUtils.isEmpty(reportApi)){
            notification_monitor_api_edittext.setText(reportApi);
        }else{
            SharedPreUtil.putString(getApplicationContext(), SharedPreConfig.FILENAME_NOTIFICATION_MONITOR_HISTORY_API,
                    SharedPreConfig.API_KEY_REPORT,notification_monitor_api_edittext.getText().toString());
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.notification_monitor_allinfo).performClick();
            }
        });

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){

                case R.id.notification_monitor_authorization:
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                    break;

                case R.id.notification_monitor_authorization_accessibility_service:
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                    break;

                case R.id.notification_monitor_allinfo:
                    {
                        Bundle mBundle = new Bundle();
                        mBundle.putInt(NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL_CMD, NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_GETALLNFS);
                        Intent intent = new Intent(NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL);
                        intent.putExtras(mBundle);
                        sendBroadcast(intent);
                    }
                    break;

                case R.id.notification_monitor_create:
                    startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                    break;

                case R.id.notification_monitor_clear:
                    {
                        Bundle mBundle = new Bundle();
                        mBundle.putInt(NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL_CMD,NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_ALL);
                        Intent intent = new Intent(NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL);
                        intent.putExtras(mBundle);
                        sendBroadcast(intent);
                    }
                    break;

                case R.id.notification_monitor_api_setting:
                    SharedPreUtil.putString(getApplicationContext(), SharedPreConfig.FILENAME_NOTIFICATION_MONITOR_HISTORY_API,
                            SharedPreConfig.API_KEY_REPORT,notification_monitor_api_edittext.getText().toString());
                    break;

                case R.id.notification_monitor_history:
                    startActivity(new Intent(getApplicationContext(),NotificationMonitorHistoryActivity.class));
                    break;

            }
        }
    };

    //判断是否打开了通知监听权限
    private boolean isNotificationListenerServiceOn() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean isNotificationAccessibilitySettingsOn(Context mContext) {

        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.v(TAG, e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(mContext.getPackageName().toLowerCase());
            }
        }
        return false;

        /*
            int accessibilityEnabled = 0;
            try {
                    accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            } catch (Settings.SettingNotFoundException e) {
                Log.e(TAG, e.getMessage());
            }
            TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

            if (accessibilityEnabled == 1) {
                String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                String service = getPackageName() + "/" + NotificationMonitorAccessibilityService.class.getCanonicalName();
                if (settingValue != null) {
                    mStringColonSplitter.setString(settingValue);
                    while (mStringColonSplitter.hasNext()) {
                        String accessibilityService = mStringColonSplitter.next();
                        if (accessibilityService.equalsIgnoreCase(service)) {
                            return true;
                        }
                    }
                }
            }
            return false;
            */
    }


    public static HashMap<String,int[]> titleContentIdMap = new HashMap<>();
    static{
        titleContentIdMap.put("com.open.iandroidtsing",new int[]{2131558534,2131558536});
        titleContentIdMap.put("com.uc.iflow",new int[]{2131361818,2131558536});
    }

    private void traversalRemoteView(View nfView , String pkg , Notification mNotification){

        ArrayList<String> txtArray = new ArrayList<>();
        String [] titleContent = new String[2];

        traversalRemoteView(nfView , titleContentIdMap.get(pkg) , titleContent , txtArray);

        if(TextUtils.isEmpty(titleContent[0]) && TextUtils.isEmpty(titleContent[1])){

            if(txtArray.size() > 0){
                titleContent[0] = txtArray.get(0);
            }

            if(txtArray.size() > 1){
                int maxLenth = txtArray.get(1).length();
                int maxLenthIndex = 1;
                for (int i = maxLenthIndex+1;i < txtArray.size();i++){
                    if(txtArray.get(i).length() > maxLenth){
                        maxLenthIndex = i;
                    }
                }
                titleContent[1] = txtArray.get(maxLenthIndex);
            }
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date(mNotification.when));

        Log.v(TAG,"when "+ date );
        Log.v(TAG,"Title "+ titleContent[0]);
        Log.v(TAG,"Content "+ titleContent[1]);

        int mChildrenCount = notification_monitor_logcat_set.getChildCount();
        for (int i = 0 ;i < mChildrenCount; i ++){
            LinearLayout dataItem = (notification_monitor_logcat_set.getChildAt(i) instanceof LinearLayout) ? (LinearLayout)(notification_monitor_logcat_set.getChildAt(i)) : null;

            if(null != dataItem && dataItem.getTag() instanceof NotificationMonitorResultBeaen){
                NotificationMonitorResultBeaen tag = (NotificationMonitorResultBeaen)dataItem.getTag();
                if(tag.showWhen > 0 && tag.showWhen == mNotification.when){
                    //更新Title
                    tag.title = titleContent[0];
                    tag.content = titleContent[1];
                    ((TextView) dataItem.findViewById(R.id.push_info)).setText(tag.toString2());

                    //更新通知预览
                    if(dataItem.getChildCount() == 1){
                        dataItem.addView(nfView,dataItem.getChildCount());
                    }
                    break;
                }
            }
        }

    }

    private void traversalRemoteView(final View nfView , final NotificationMonitorResultBeaen resultBeaen , Notification mNotification){

        ArrayList<String> txtArray = new ArrayList<>();
        String [] titleContent = new String[2];

        traversalRemoteView(nfView , titleContentIdMap.get(resultBeaen.pkg) , titleContent , txtArray);

        if(TextUtils.isEmpty(titleContent[0]) && TextUtils.isEmpty(titleContent[1])){

            if(txtArray.size() > 0){
                titleContent[0] = txtArray.get(0);
            }

            if(txtArray.size() > 1){
                int maxLenth = txtArray.get(1).length();
                int maxLenthIndex = 1;
                for (int i = maxLenthIndex+1;i < txtArray.size();i++){
                    if(txtArray.get(i).length() > maxLenth){
                        maxLenthIndex = i;
                    }
                }
                titleContent[1] = txtArray.get(maxLenthIndex);
            }
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date(mNotification.when));

        Log.v(TAG,"when "+ date );
        Log.v(TAG,"Title "+ titleContent[0]);
        Log.v(TAG,"Content "+ titleContent[1]);

        int mChildrenCount = notification_monitor_logcat_set.getChildCount();
        for (int i = 0 ;i < mChildrenCount; i ++){
            final LinearLayout dataItem = (notification_monitor_logcat_set.getChildAt(i) instanceof LinearLayout) ? (LinearLayout)(notification_monitor_logcat_set.getChildAt(i)) : null;

            if(null != dataItem && dataItem.getTag() instanceof NotificationMonitorResultBeaen){
                NotificationMonitorResultBeaen tag = (NotificationMonitorResultBeaen)dataItem.getTag();
                if(tag.id == resultBeaen.id || tag.indexId == resultBeaen.indexId ){
                    //更新Title
                    tag.title = titleContent[0];
                    tag.content = titleContent[1];

                    //记录日期对应的具体信息
                    String nfText = resultBeaen.bulld();
                    String fileName = String.format("%s_%s", SharedPreConfig.FILENAME_NOTIFICATION_MONITOR_HISTORY,resultBeaen.date);
                    SharedPreUtil.putString(getApplicationContext(), fileName,""+resultBeaen.indexId,nfText);

                    ((TextView) dataItem.findViewById(R.id.push_info)).setText(tag.toString2());

                    //更新通知预览
                    if(dataItem.getChildCount() == 1){
                        dataItem.addView(nfView,dataItem.getChildCount());
                        nfView.setDrawingCacheEnabled(true);

                        final String path = SDCardUtil.getDiskFilePath(getApplicationContext(),resultBeaen.snapshootPath);
                        if(!TextUtils.isEmpty(resultBeaen.snapshootPath) && !new File(path).exists()){
                            final MutableInt count = new MutableInt(0);
                            dataItem.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(null != nfView.getDrawingCache()){
                                        saveBitmap(nfView.getDrawingCache(), path);
                                        nfView.setDrawingCacheEnabled(false);
                                    }else{
                                        Log.v(TAG,"count.value "+count.value);
                                        if(count.value < 10){
                                            count.value++;
                                            dataItem.postDelayed(this,1000);
                                        }else{
                                            nfView.setDrawingCacheEnabled(false);
                                        }
                                    }
                                }
                            },1000);
                        }
                    }

                    break;
                }
            }
        }
    }

    private void traversalRemoteView(View nfView , int[] idMap , String [] titleContent, ArrayList<String> txtArray){
        if(nfView instanceof ViewGroup)
        {
            ViewGroup mViewGroup = (ViewGroup)nfView;
            int mChildrenCount = mViewGroup.getChildCount();
            for(int i = 0; i < mChildrenCount; i++) {
                View itemView = mViewGroup.getChildAt(i);
                if(itemView instanceof ViewGroup){
                    traversalRemoteView(itemView,idMap,titleContent,txtArray);
                }
                else if(itemView instanceof TextView) {
                    TextView mTextView = (TextView)itemView;
                    String text = mTextView.getText().toString().trim();
                    if(!TextUtils.isEmpty(text)){
                        setTitleCotent(mTextView.getId(), text , idMap , titleContent);
                        txtArray.add(text);
                    }
                    Log.v(TAG,"TextView 1 id:"+ mTextView.getId() + ".text:" + text);
                }
            }
        }else{
            if(nfView instanceof TextView) {
                TextView mTextView = (TextView)nfView;
                String text = mTextView.getText().toString().trim();
                if(!TextUtils.isEmpty(text)){
                    setTitleCotent(mTextView.getId(), text , idMap , titleContent);
                    txtArray.add(text);
                }
                Log.v(TAG,"TextView 2 id:"+ mTextView.getId() + ".text:" + text);
            }
        }
    }

    private void setTitleCotent(int viewId , String text , int[] idMap ,String [] titleContent){
        if(null != idMap){
            if(idMap[0] == viewId){
                titleContent[0] =  text;
            }else if(idMap[1] == viewId){
                titleContent[1] =  text;
            }
        }
    }

    private void saveBitmap(Bitmap bitmap, String path)
    {
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
        FileUtil.createFile(path);

        FileOutputStream out = null;
        try{
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Notification mCurrentNotification;
    public static ArrayList<Notification> mNotificationList;
    public class NotificationMonitorBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            if ((intent != null) && (intent.getAction() != null) && (intent.getAction().equals(NOTIFICATION_MONITOR_ACTION)))
            {
                Bundle extras = intent.getExtras();
                if(null != extras){

                    int cmd = extras.getInt(NOTIFICATION_MONITOR_ACTION_KEY_CMD);

                    Log.v(TAG,"cmd " + cmd);

                    if(cmd == NOTIFICATION_MONITOR_ACTION_CMD_ADD){

                        NotificationMonitorResultBeaen resultBeaen = extras.getParcelable(NOTIFICATION_MONITOR_ACTION_KEY_DATA);
                        String txt = "add \n"+ resultBeaen.toString2();

                        LinearLayout dataItem = (LinearLayout)mInflater.inflate(R.layout.notification_monitor_dateitem,notification_monitor_logcat_set,false);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.topMargin = 20;
                        notification_monitor_logcat_set.addView(dataItem,0,lp);

                        TextView addTextView = (TextView)dataItem.findViewById(R.id.push_info);
                        addTextView.setText(txt);
                        addTextView.setTextColor(Color.BLUE);

                        dataItem.setTag(resultBeaen);

                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CMD_ADD_2){

                        NotificationMonitorResultBeaen resultBeaen = extras.getParcelable(NOTIFICATION_MONITOR_ACTION_KEY_DATA);
                        String txt = "add \n"+ resultBeaen.toString2();

                        LinearLayout dataItem = (LinearLayout)mInflater.inflate(R.layout.notification_monitor_dateitem,notification_monitor_logcat_set,false);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.topMargin = 20;
                        notification_monitor_logcat_set.addView(dataItem,0,lp);

                        TextView addTextView = (TextView)dataItem.findViewById(R.id.push_info);
                        addTextView.setText(txt);
                        addTextView.setTextColor(Color.BLUE);

                        dataItem.setTag(resultBeaen);

//                        Notification mNotification = extras.getParcelable(NOTIFICATION_MONITOR_ACTION_KEY_DATA_2);
                        Notification mNotification = mCurrentNotification;
                        if(null != mNotification){
                            RemoteViews contentView = mNotification.contentView;
                            if (null != contentView) {
                                View nfView = contentView.apply(getApplicationContext(), notification_monitor_logcat_set);
                                traversalRemoteView(nfView, resultBeaen , mNotification);
                            }
                        }

                        Log.v(TAG,"add2 \n resultBeaen " + resultBeaen + "\n id "+ mNotification);
                        mCurrentNotification = null;

                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CMD_REMOVE){

                        NotificationMonitorResultBeaen resultBeaen = extras.getParcelable(NOTIFICATION_MONITOR_ACTION_KEY_DATA);
                        String txt = "remove \n"+ resultBeaen.toString2();

                        LinearLayout dataItem = (LinearLayout)mInflater.inflate(R.layout.notification_monitor_dateitem,notification_monitor_logcat_set,false);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.topMargin = 20;
                        notification_monitor_logcat_set.addView(dataItem,0,lp);

                        TextView removeTextView = (TextView)dataItem.findViewById(R.id.push_info);
                        removeTextView.setText(txt);
                        removeTextView.setTextColor(Color.RED);

                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CMD_ALLINFO){

                        notification_monitor_logcat_set.removeAllViews();

                        ArrayList<NotificationMonitorResultBeaen> resultBeaenList = extras.getParcelableArrayList(NOTIFICATION_MONITOR_ACTION_KEY_DATA);
//                        ArrayList<Notification> mNotificationList = extras.getParcelableArrayList(NOTIFICATION_MONITOR_ACTION_KEY_DATA_2);

                        int size = (null != resultBeaenList && resultBeaenList.size() > 0) ? resultBeaenList.size() : 0 ;
                        if(size > 0){
                            for (int i = size-1; i >=0 ; --i) {

                                NotificationMonitorResultBeaen itemNotificationMonitorResultBeaen = resultBeaenList.get(i);
                                String txt = itemNotificationMonitorResultBeaen.toString2();

                                LinearLayout dataItem = (LinearLayout)mInflater.inflate(R.layout.notification_monitor_dateitem,notification_monitor_logcat_set,false);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp.topMargin = 20;
                                notification_monitor_logcat_set.addView(dataItem,0,lp);

                                TextView addTextView = (TextView)dataItem.findViewById(R.id.push_info);
                                addTextView.setText(txt);
                                addTextView.setTextColor(Color.GRAY);

                                dataItem.setTag(itemNotificationMonitorResultBeaen);

                                Notification itemNotification = mNotificationList.get(i);
                                RemoteViews contentView = itemNotification.contentView;

                                if (null != contentView) {
                                    View nfView = contentView.apply(getApplicationContext(), notification_monitor_logcat_set);
                                    traversalRemoteView(nfView, itemNotificationMonitorResultBeaen , itemNotification);
                                }
                            }
                        }

                        TextView addTextView = new TextView(getApplicationContext());
                        addTextView.setText(String.format(size > 0 ? "There are %d activie notifications " : "There are %d activie notification", size));
                        addTextView.setTextColor(Color.GRAY);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.leftMargin = 20;
                        lp.topMargin = 20;
                        notification_monitor_logcat_set.addView(addTextView,0,lp);

                        mNotificationList = null;

                        scroll_bottom_duration = 10000;
                        notification_monitor_logcat_snapshoot.setVisibility(View.VISIBLE);
                        notification_monitor_logcat_scrollView.scrollTo(0,0);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Log.v(TAG," scroll_bottom_duration "+ scroll_bottom_duration +" getScrollY "+notification_monitor_logcat_scrollView.getScrollY()
                                        + " getHeight "+notification_monitor_logcat_scrollView.getHeight()
                                        +"  getHeight "+notification_monitor_logcat_scrollView.computeVerticalScrollRange());

                                boolean end = notification_monitor_logcat_scrollView.getScrollY() + notification_monitor_logcat_scrollView.getHeight() >=  notification_monitor_logcat_scrollView.computeVerticalScrollRange();
                                scroll_bottom_duration -=200;
                                if(end || scroll_bottom_duration < 0){
                                    notification_monitor_logcat_snapshoot.setVisibility(View.GONE);
                                }else{
                                    notification_monitor_logcat_scrollView.scrollBy(0,100);
                                    mHandler.postDelayed(this,200);
                                }
                            }
                        },200);

                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CMD_ACCESSIBILITYSERVICE_BACK){

                        String packageName = extras.getString(NOTIFICATION_MONITOR_ACTION_KEY_PKG);
                        Notification mNotification = extras.getParcelable(NOTIFICATION_MONITOR_ACTION_KEY_DATA_2);

                        Log.v(TAG,"packageName " + packageName + "\n " + "\n id "+ mNotification);

                        RemoteViews contentView = mNotification.contentView;

                        if (null != contentView) {
                            View nfView = contentView.apply(getApplicationContext(), notification_monitor_logcat_set);
                            traversalRemoteView(nfView, packageName , mNotification);
                        }
                    }
                }
            }
        }
    }

    public final class MutableInt {
        public int value;

        public MutableInt(int value) {
            this.value = value;
        }
    }

}
