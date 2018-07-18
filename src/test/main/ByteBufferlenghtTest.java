package test.main;

import java.nio.ByteBuffer;

public class ByteBufferlenghtTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBuffer buffer = ByteBuffer.allocate(20);
		buffer.put(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 
				0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14});
		System.out.println(buffer.capacity() + " " + buffer.position() + " " + buffer.remaining() + " " + buffer.limit());
		System.out.println(buffer.get(0));
		System.out.println(buffer.get(1));
		System.out.println(buffer.get(2));
		System.out.println(buffer.get(3));
		System.out.println(buffer.capacity() + " " + buffer.position() + " " + buffer.remaining() + " " + buffer.limit());
		
		buffer.position(0);
		System.out.println(buffer.get());
		System.out.println(buffer.get());
		System.out.println(buffer.get());
		System.out.println(buffer.get());
		System.out.println(buffer.capacity() + " " + buffer.position() + " " + buffer.remaining() + " " + buffer.limit());
		System.out.println(buffer.getLong(0));
		System.out.println(buffer.capacity() + " " + buffer.position() + " " + buffer.remaining() + " " + buffer.limit());
		
		int nextSize = buffer.capacity() - 9;
		ByteBuffer next = ByteBuffer.allocate(nextSize);
		System.arraycopy(buffer.array(), buffer.capacity() - nextSize, next.array(), 0, nextSize);
		System.out.println(next.get(0));
		System.out.println(next.get(1));
		System.out.println(next.get(2));
	}

}
