package com.open.iandroidtsing.net.data;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 
 * @author Administrator
 *
 */
public abstract class AbsMessage {
	
	public abstract byte[] getPacket();

	public void write(OutputStream outStream) throws IOException {
		outStream.write(getPacket());
	}

	public void write(SocketChannel socketChannel) throws IOException{
		ByteBuffer buf=ByteBuffer.wrap(getPacket());
		socketChannel.write(buf);
	}
}
