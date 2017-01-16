package com.open.iandroidtsing.notification;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/11.
 */

public class NotificationMonitorResultBeaen implements Parcelable {

    int id;// 获取接收消息通知Id

    String pkg;// 获取接收消息APP的包名

    String title;// 获取接收消息的抬头

    String content;// 获取接收消息的内容

    String subText;

    long showWhen;

    //-----------------------------------
    String date;//日期
    int indexId;//一天中的第几条消息

    //-------------------------------------
    Bitmap snapshoot;


    public String bulld(){
        StringBuilder sb = new StringBuilder(128);
        sb.append("id").append("=").append(id);
        sb.append("&pkg").append("=").append(pkg);
        sb.append("&title").append("=").append(title);
        sb.append("&content").append("=").append(content);
        sb.append("&subText").append("=").append(subText);
        sb.append("&showWhen").append("=").append(showWhen);
        sb.append("&date").append("=").append(date);
        sb.append("&indexId").append("=").append(indexId);
        return sb.toString();
    }

    public void parse(String sb){
        String[] data = sb.split("&");
        for (String item:data) {
            String[] itemData = item.split("=");
            if("id".equals(itemData[0])){
                id = Integer.valueOf(itemData[1]);
            }else if("pkg".equals(itemData[0])){
                pkg = itemData[1];
            }else if("title".equals(itemData[0])){
                title = itemData[1];
            }else if("content".equals(itemData[0])){
                content = itemData[1];
            }else if("subText".equals(itemData[0])){
                subText = itemData[1];
            }else if("showWhen".equals(itemData[0])){
                showWhen = Long.valueOf(itemData[1]);
            }else if("date".equals(itemData[0])){
                date = itemData[1];
            }else if("indexId".equals(itemData[0])){
                indexId = Integer.valueOf(itemData[1]);
            }
        }
    }

    @Override
    public String toString() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date(showWhen));

        return "NotificationMonitorResultBeaen{" +
                "id=" + id +
                ", pkg='" + pkg + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", subText='" + subText + '\'' +
                ", showWhen=" + date +
                '}';
    }

    public String toString2() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date(showWhen));
        return
                "\ndate       = " + date +
                "\n\nid            = " + id +
                "\npkg         = " + pkg + "" +
                "\ntitle         = " + title + "" +
                "\ncontent  = " + content + "" +
                "\nsubText  = " + subText + "";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.pkg);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.subText);
        dest.writeLong(this.showWhen);
        dest.writeString(this.date);
        dest.writeInt(this.indexId);
    }

    public NotificationMonitorResultBeaen() {
    }

    protected NotificationMonitorResultBeaen(Parcel in) {
        this.id = in.readInt();
        this.pkg = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.subText = in.readString();
        this.showWhen = in.readLong();
        this.date = in.readString();
        this.indexId = in.readInt();
    }

    public static final Creator<NotificationMonitorResultBeaen> CREATOR = new Creator<NotificationMonitorResultBeaen>() {
        @Override
        public NotificationMonitorResultBeaen createFromParcel(Parcel source) {
            return new NotificationMonitorResultBeaen(source);
        }

        @Override
        public NotificationMonitorResultBeaen[] newArray(int size) {
            return new NotificationMonitorResultBeaen[size];
        }
    };
}
