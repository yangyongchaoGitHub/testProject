package test.main;

import java.nio.ByteBuffer;

public class ByteBufferPutTEST {
	public static void main(String[] args) {
		byte[] bytes = new byte[]{0x01, 0x01, 0x01};
		ByteBuffer mRecvBuf = ByteBuffer.allocate(5);
		mRecvBuf.put((byte) 0x31);
		mRecvBuf.put((byte) 0x32);
		mRecvBuf.put((byte) 0x33);
		
		int length = bytes.length, offset = mRecvBuf.position();
		
		for (byte b : mRecvBuf.array()) {
			System.out.println(b);
		}
		System.out.println("-----------");
		
		if (length + offset > mRecvBuf.capacity()) {
            ByteBuffer buf = ByteBuffer.allocate(mRecvBuf.capacity() * 2);
            buf.put(mRecvBuf.array());
            //buf.put(mRecvBuf);
            buf.position(offset);
            for (byte b : buf.array()) {
    			System.out.println(b);
    		}
            System.out.println("~~~~~~~~~~~~~~~~~~~");
            mRecvBuf = buf;
        }
		
		for (byte b : mRecvBuf.array()) {
			System.out.println(b);
		}
		System.out.println("#######################");
		
		mRecvBuf.put(bytes);
		for (byte b : mRecvBuf.array()) {
			System.out.println(b);
		}
	}

}
