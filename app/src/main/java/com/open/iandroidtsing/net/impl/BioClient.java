package com.open.iandroidtsing.net.impl;


import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 
 * @author Administrator
 *
 */
public class BioClient {

	private final String TAG="BioClient";

	private final int STATE_CLOSE			= 1<<1;//socket关闭
	private final int STATE_CONNECT_START	= 1<<2;//开始连接server
	private final int STATE_CONNECT_SUCCESS	= 1<<3;//连接成功
	private final int STATE_CONNECT_FAILED	= 1<<4;//连接失败

	private final Message SIGNAL_RECONNECT = new Message();

	private Tcp[] tcpArray;
	private int index = -1;
	private IConnectionReceiveListener mConnectionReceiveListener;

	private ConcurrentLinkedQueue<Message> mMessageQueen = new ConcurrentLinkedQueue();
	private Thread mConnectionThread =null;
	private BioConnection mConnection;

	private final Object lock=new Object();

	private IBioConnectListener mBioConnectionListener = new IBioConnectListener() {
		@Override
		public void onConnectionSuccess() {

		}

		@Override
		public void onConnectionFailed() {
			sendMessage(SIGNAL_RECONNECT);//发送一个空的消息进行SocketConnect操作
		}
	};

	public BioClient(Tcp[] tcpArray , IConnectionReceiveListener mConnectionReceiveListener) {
		this.tcpArray = tcpArray;
		this.mConnectionReceiveListener = mConnectionReceiveListener;
	}

	public void sendMessage(Message msg)
	{
		//1.重连消息，进行重连
		//2.没有连接,需要进行重连
		//3.在连接不成功，并且也不在重连中时，需要进行重连;
		if(SIGNAL_RECONNECT == msg ){
			openConnection();
		}else if(null == mConnection){
			mMessageQueen.add(msg);
			openConnection();
		}else if(!mConnection.isConnected() && !mConnection.isConnecting()){
			mMessageQueen.add(msg);
			openConnection();
		}else{
			mMessageQueen.add(msg);
			if(mConnection.isConnected()){
				synchronized (lock)
				{
					lock.notifyAll();
				}
			}else{
				//说明正在重连中
			}
		}
	}

    public synchronized void connect()
    {
        sendMessage(SIGNAL_RECONNECT);
    }

    public synchronized void reconnect(){
        closeConnection(false);
        connect();
    }

	public synchronized void openConnection()
	{
		//已经在连接中就不再进行连接
		if(null != mConnection && !mConnection.isClosed()){
			return;
		}

		index++;
		if(index < tcpArray.length && index >= 0){
			closeConnection(false);
			mConnection = new BioConnection(mBioConnectionListener,mConnectionReceiveListener);
			mConnection.init(tcpArray[index].ip,tcpArray[index].port);
			mConnectionThread =new Thread(mConnection);
			mConnectionThread.start();
		}else{
			index = -1;

			//循环连接了一遍还没有连接上，说明网络连接不成功，此时清空消息队列，防止队列堆积
			mMessageQueen.clear();
		}
	}


	public synchronized void closeConnection()
	{
		closeConnection(true);
	}

	public synchronized void closeConnection(boolean isCloseByUser)
	{

		try {

			if(null != mConnection) {
				mConnection.setCloseByUser(isCloseByUser);
			}

			if(null != mConnection && !mConnection.isClosed()) {
				mConnection.close();
			}
			mConnection= null;

			if( null!= mConnectionThread && mConnectionThread.isAlive() ) {
				mConnectionThread.interrupt();
			}
			mConnectionThread =null;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class BioConnection implements Runnable
	{
		private String ip ="192.168.1.1";
		private int port =9999;
		private int state = STATE_CLOSE;
		private IBioConnectListener mBioConnectionListener;
		private IConnectionReceiveListener mConnectionReceiveListener;
		private boolean isClosedByUser = false;

		private Socket socket=null;
		private OutputStream outStream=null;
		private InputStream inStream=null;
		private Thread writeThread =null;
		private Thread readThread =null;


		public BioConnection(IBioConnectListener mBioConnectionListener, IConnectionReceiveListener mConnectionReceiveListener) {
			this.mBioConnectionListener 	= mBioConnectionListener;
			this.mConnectionReceiveListener = mConnectionReceiveListener;
		}

		public void init(String ip, int port){
			this.ip = ip;
			this.port = port;
		}

		public boolean isClosed(){
			return state == STATE_CLOSE;
		}

		public boolean isConnected(){
			return state == STATE_CONNECT_SUCCESS;
		}

		public boolean isConnecting(){
			return state == STATE_CONNECT_START;
		}

		public boolean setCloseByUser(boolean isClosedbyUser){
			this.isClosedByUser = isClosedbyUser;
		}

		public void close(){
			try {
				if(state!=STATE_CLOSE)
				{
					try {
						if(null!=socket)
						{
							socket.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						socket=null;
					}

					try {
						if(null!=outStream)
						{
							outStream.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						outStream=null;
					}

					try {
						if(null!=inStream)
						{
							inStream.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						inStream=null;
					}

					try {
						if(null!= mConnectionThread && mConnectionThread.isAlive())
						{
							mConnectionThread.interrupt();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						mConnectionThread =null;
					}

					try {
						if(null!= writeThread && writeThread.isAlive())
						{
							writeThread.interrupt();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						writeThread =null;
					}

					try {
						if(null!= readThread && readThread.isAlive())
						{
							readThread.interrupt();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						readThread =null;
					}

					state=STATE_CLOSE;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			long start = System.currentTimeMillis();
Log.v(TAG,"BioConnection :Start");
			try {
                    isClosedByUser = false;
                    state=STATE_CONNECT_START;
                    socket=new Socket();
                    socket.connect(new InetSocketAddress(ip, port), 15*1000);

                    outStream=socket.getOutputStream();
                    inStream=socket.getInputStream();

                    writeThread =new Thread(new WriteRunnable());
                    readThread =new Thread(new ReadRunnable());
                    writeThread.start();
                    readThread.start();

                    state=STATE_CONNECT_SUCCESS;

			} catch (Exception e) {
				e.printStackTrace();
                state=STATE_CONNECT_FAILED;
			}finally {
				if(!(state == STATE_CONNECT_SUCCESS || isClosedByUser)) {
					if(null != mBioConnectionListener){
						mBioConnectionListener.onConnectionFailed();
					}
				}
			}

Log.v(TAG,"BioConnection :End cost " + (System.currentTimeMillis() -start));
		}


		private class WriteRunnable implements Runnable
		{
			public void run() {
				Log.v(TAG,"WriteRunnable :Start");
				try {
					while(state!=STATE_CLOSE&&state==STATE_CONNECT_SUCCESS&&null!=outStream)
					{

						while(!mMessageQueen.isEmpty())
						{
							Message item= mMessageQueen.poll();
							outStream.write(item.getPacket());
							outStream.flush();
						}

						Log.v(TAG,"WriteRunnable :woken up AAAAAAAAA");
						synchronized (lock)
						{
							lock.wait();
						}
						Log.v(TAG,"WriteRunnable :woken up BBBBBBBBBB");
					}
				}catch(SocketException e1)
				{
					e1.printStackTrace();//发送的时候出现异常，说明socket被关闭了(服务器关闭)java.net.SocketException: sendto failed: EPIPE (Broken pipe)
					openConnection();
				}
				catch (Exception e) {
					Log.v(TAG,"WriteRunnable ::Exception");
					e.printStackTrace();
				}

				Log.v(TAG,"WriteRunnable ::End");
			}
		}

		private class ReadRunnable implements Runnable
		{
			public void run() {
				Log.v(TAG,"ReadRunnable :Start");

				try {
					while(state!=STATE_CLOSE&&state==STATE_CONNECT_SUCCESS&&null!=inStream)
					{
						Log.v(TAG,"ReadRunnable :---------");
						byte[] bodyBytes=new byte[5];
						int offset=0;
						int length=5;
						int read=0;

						while((read=inStream.read(bodyBytes, offset, length))>0)
						{
							if(length-read==0)
							{
								if(null!= mConnectionReceiveListener)
								{
									mConnectionReceiveListener.onConnectionReceive(new String(bodyBytes));
								}

								offset=0;
								length=5;
								read=0;
								continue;
							}
							offset+=read;
							length=5-offset;
						}

						openConnection();//走到这一步，说明服务器socket断了
						break;
					}
				}
				catch(SocketException e1)
				{
					e1.printStackTrace();//客户端主动socket.closeConnection()会调用这里 java.net.SocketException: Socket closed
				}
				catch (Exception e2) {
					Log.v(TAG,"ReadRunnable :Exception");
					e2.printStackTrace();
				}

				Log.v(TAG,"ReadRunnable :End");
			}
		}

	}
	

	private interface IBioConnectListener {

		void onConnectionSuccess();

		void onConnectionFailed();

	}

	public interface IConnectionReceiveListener
	{
		void onConnectionReceive(String txt);
	}

	public static class Tcp{
		public String  ip;
		public int     port;

		public Tcp(String ip, int port) {
			this.ip = ip;
			this.port = port;
		}
	}
}
