package com.open.iandroidtsing.notification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/1/11.
 */

public class NotificationMonitorResultBeaen implements Parcelable {

    int id;// 获取接收消息通知Id

    String pkg;// 获取接收消息APP的包名

    String title;// 获取接收消息的抬头

    String content;// 获取接收消息的内容

    String subText;

    String showWhen;


    @Override
    public String toString() {
        return "NotificationMonitorResultBeaen{" +
                "id=" + id +
                ", pkg='" + pkg + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", subText='" + subText + '\'' +
                ", showWhen='" + showWhen + '\'' +
                '}';
    }

    public String toString2() {
        return
                "\ndate       = " + showWhen +
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
        dest.writeString(this.showWhen);
    }

    public NotificationMonitorResultBeaen() {
    }

    protected NotificationMonitorResultBeaen(Parcel in) {
        this.id = in.readInt();
        this.pkg = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.subText = in.readString();
        this.showWhen = in.readString();
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
