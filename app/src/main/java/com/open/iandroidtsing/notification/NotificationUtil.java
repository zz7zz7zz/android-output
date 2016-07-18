package com.open.iandroidtsing.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.open.iandroidtsing.R;

public class NotificationUtil {
    
    public static void notify(Context context,int notifyID)
    {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       
        Intent intent = new Intent();        
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.push))
                .setSmallIcon(R.mipmap.push)
                .setContentIntent(pi)
                .setTicker(context.getResources().getString(R.string.app_name))// 设置状态栏的显示的信息
                .setWhen(System.currentTimeMillis())// 设置时间发生时间
                .setNumber(13)
                .setContentTitle("我是标题")
                .setContentText("我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容");
//                .setStyle(new NotificationCompat.InboxStyle()
//                        .addLine("M.Twain (Google+) Haiku is more than a cert...")
//                        .addLine("M.Twain Reminder")
//                        .addLine("M.Twain Lunch?")
//                        .addLine("M.Twain Revised Specs")
//                        .addLine("M.Twain ")
//                        .addLine("Google Play Celebrate 25 billion apps with Goo..")
//                        .addLine("Stack Exchange StackOverflow weekly Newsl...")
//                        .setBigContentTitle("6 new message")
//                        .setSummaryText("mtwain@android.com"))
//                        .build();
         
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        mNotificationManager.notify(notifyID, notification);
    }
    
    public static void notify2(Context context,int notifyID)
    {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       
        Intent intent = new Intent();        
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.push))
                .setSmallIcon(R.mipmap.push)
                .setContentIntent(pi)
                .setTicker(context.getResources().getString(R.string.app_name))// 设置状态栏的显示的信息
                .setWhen(System.currentTimeMillis())// 设置时间发生时间
                .setNumber(99)
                .setContentTitle("我是标题1")
                .setContentText("我是内容1 我是内容1 我是内容1 我是内容1 我是内容1 我是内容1 我是内容1 我是内容1 我是内容1")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("我是内容2 我是内容2 我是内容2 我是内容2 我是内容2 我是内容2 我是内容2 我是内容2 我是内容2" )
                        .addLine("AAAAAAAAAAAAAAAAAAAAAA")
                        .setBigContentTitle("我是标题2")
//                        .setSummaryText("----------------------------------------------------------------------------")
                        )
                        .build();
         
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        mNotificationManager.notify(notifyID, notification);
    }
    
    public static void notify3(Context context,int notifyID,String tag)
    {
        String title = "我是标题A---3------"+tag;
        String contentText = "我是内容A3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3";
        String BigContentTitle = "我是标题B---3"+tag;
        String bigText = "我是内容B3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3 我是内容3我是内容3我是内容3我是内容3我是内容3我是内容3我是内容3我是内容3";
        
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       
        Intent intent = new Intent();        
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
         builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.push))
                .setSmallIcon(R.mipmap.push)
                .setContentIntent(pi)
                .setTicker(context.getResources().getString(R.string.app_name))// 设置状态栏的显示的信息
                .setWhen(System.currentTimeMillis())// 设置时间发生时间
//                .setNumber(99)
                .setAutoCancel(true)// 设置可以清除
                .setContentTitle(title)
                .setContentText(contentText)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("我是内容33 我是内容33 我是内容33 我是内容33 我是内容33 我是内容33 我是内容33 我是内容33")
//                        .setBigContentTitle("我是标题33")
////                        .setSummaryText("我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介")
//                        )
                 .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText)
                        .setBigContentTitle(BigContentTitle)
                        .setSummaryText("我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介")
                        )
                        .build();

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        mNotificationManager.notify(notifyID, notification);
    }


    public static void notifyPriority(Context context,int notifyID,String tag , int priority)
    {
        String title            = tag+":title";
        String contentText      = tag+":contentText";
        String BigContentTitle  = tag+":BigContentTitle";
        String bigText          = tag+":bigText";

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent();
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.push))
                .setSmallIcon(R.mipmap.push)
                .setContentIntent(pi)
                .setTicker(context.getResources().getString(R.string.app_name))// 设置状态栏的显示的信息
                .setWhen(System.currentTimeMillis())// 设置时间发生时间
//                .setNumber(99)
                .setAutoCancel(true)// 设置可以清除
                .setContentTitle(title)
                .setContentText(contentText)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("我是内容33 我是内容33 我是内容33 我是内容33 我是内容33 我是内容33 我是内容33 我是内容33")
//                        .setBigContentTitle("我是标题33")
////                        .setSummaryText("我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介")
//                        )
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText)
                                .setBigContentTitle(BigContentTitle)
//                        .setSummaryText("我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介 我是内容简介")
                )
                .build();

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.priority = priority;
        mNotificationManager.notify(notifyID, notification);
    }
}
