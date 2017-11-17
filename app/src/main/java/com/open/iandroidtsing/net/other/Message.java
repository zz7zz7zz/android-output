package com.open.iandroidtsing.net.other;


/**
 * 
 * @author Administrator
 *
 */
public class Message {
	
	private byte[] data;
	
	public void pack(String txt)
	{
		data=txt.getBytes();
	}
	
	public byte[] getPacket()
	{
		return data;
	}
}
