package com.open.iandroidtsing.net.other;

/**
 * Created by Administrator on 2017/11/17.
 */

public class INetListeners {

    public interface IConnectListener {

        void onConnectionSuccess();

        void onConnectionFailed();

    }

    public interface IConnectReceiveListener
    {
        void onConnectionReceive(String txt);
    }

}
