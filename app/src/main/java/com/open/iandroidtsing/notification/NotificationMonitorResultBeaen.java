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

    @Override
    public String toString() {
        return "NotificationMonitorResultBeaen{" +
                "notificationId=" + notificationId +
                ", notificationPkg='" + notificationPkg + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", notificationText='" + notificationText + '\'' +
                ", notificationSubText='" + notificationSubText + '\'' +
                '}';
    }

    public String toString2() {
        return
                " notificationId=" + notificationId +
                "\n notificationPkg='" + notificationPkg + '\'' +
                "\n notificationTitle='" + notificationTitle + '\'' +
                "\n notificationText='" + notificationText + '\'' +
                "\n notificationSubText='" + notificationSubText + '\'';
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
    }

    public NotificationMonitorResultBeaen() {
    }

    protected NotificationMonitorResultBeaen(Parcel in) {
        this.notificationId = in.readInt();
        this.notificationPkg = in.readString();
        this.notificationTitle = in.readString();
        this.notificationText = in.readString();
        this.notificationSubText = in.readString();
    }

    public static final Parcelable.Creator<NotificationMonitorResultBeaen> CREATOR = new Parcelable.Creator<NotificationMonitorResultBeaen>() {
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
