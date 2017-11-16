package com.open.iandroidtsing.net;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.open.iandroidtsing.R;
import com.open.iandroidtsing.net.impl.BioClient;
import com.open.iandroidtsing.net.impl.Message;

public class SocketConnectionActivity extends Activity {

	private BioClient mBioClient =null;
	private EditText ip,port,sendContent,recContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_socket_connection);
		
		initView();
	}
	 
	
	private void initView()
	{
		findViewById(R.id.open).setOnClickListener(listener);
		findViewById(R.id.close).setOnClickListener(listener);
		findViewById(R.id.reconn).setOnClickListener(listener);
		findViewById(R.id.send).setOnClickListener(listener);
		findViewById(R.id.clear).setOnClickListener(listener);
		
		ip=(EditText) findViewById(R.id.ip);
		port=(EditText) findViewById(R.id.port);
		sendContent=(EditText) findViewById(R.id.sendContent);
		recContent=(EditText) findViewById(R.id.recContent);
		
		ip.setText("192.168.123.1");
		port.setText("9999");
	}
	
	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
				case R.id.open:
					mBioClient =new BioClient(new BioClient.Tcp[]{new BioClient.Tcp(ip.getText().toString(), Integer.valueOf(port.getText().toString()))},socketListener);
					mBioClient.connect();
					break;
					
				case R.id.close:
					mBioClient.closeConnection();
					break;
					
				case R.id.reconn:
					mBioClient.reConnect();
					break;
					
				case R.id.send:
					Message packet=new Message();
					packet.pack(sendContent.getText().toString());
					mBioClient.sendMessage(packet);
					sendContent.setText("");
					break;
					
				case R.id.clear:
					recContent.setText("");
					break;
			}
		}
	};

	private BioClient.IConnectionReceiveListener socketListener=new BioClient.IConnectionReceiveListener() {

		@Override
		public void onConnectionReceive(final String txt) {
			runOnUiThread(new Runnable() {
				public void run() {
					recContent.getText().append(txt).append("\r\n");
				}
			});
		}
	};

}
