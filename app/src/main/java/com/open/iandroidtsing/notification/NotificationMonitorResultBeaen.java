package com.open.iandroidtsing.notification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/1/11.
 */

public class NotificationMonitorResultBeaen implements Parcelable {

    int notificationId  ;// 获取接收消息通知Id

    String notificationPkg ;// 获取接收消息APP的包名

    String notificationTitle ;// 获取接收消息的抬头

    String notificationText ;// 获取接收消息的内容

    String notificationSubText ;

    long notificationShowWhen ;


    @Override
    public String toString() {
        return "NotificationMonitorResultBeaen{" +
                "notificationId=" + notificationId +
                ", notificationPkg='" + notificationPkg + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", notificationText='" + notificationText + '\'' +
                ", notificationSubText='" + notificationSubText + '\'' +
                ", notificationShowWhen='" + notificationShowWhen + '\'' +
                '}';
    }

    public String toString2() {
        return
                "\nnotificationId=" + notificationId +
                "\nnotificationPkg='" + notificationPkg + '\'' +
                "\nnotificationTitle='" + notificationTitle + '\'' +
                "\nnotificationText='" + notificationText + '\'' +
                "\nnotificationText='" + notificationText + '\'' +
                "\nnotificationShowWhen='" + notificationShowWhen + '\'';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.notificationId);
        dest.writeString(this.notificationPkg);
        dest.writeString(this.notificationTitle);
        dest.writeString(this.notificationText);
        dest.writeString(this.notificationSubText);
        dest.writeLong(this.notificationShowWhen);
    }

    public NotificationMonitorResultBeaen() {
    }

    protected NotificationMonitorResultBeaen(Parcel in) {
        this.notificationId = in.readInt();
        this.notificationPkg = in.readString();
        this.notificationTitle = in.readString();
        this.notificationText = in.readString();
        this.notificationSubText = in.readString();
        this.notificationShowWhen = in.readLong();
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
