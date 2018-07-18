package test.main;

import java.nio.ByteBuffer;

public class IntValueTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(1<<1);
		System.out.println(1<<2);
		System.out.println(1<<3);
		System.out.println(1<<4);
		System.out.println(1<<5);
		
		ByteBuffer buffer = ByteBuffer.allocate(4);
		System.out.println(buffer.position() + " " + buffer.limit() + " " + buffer.remaining());
		int a = 15;
		buffer.putInt(a);
		System.out.println(buffer.position() + " " + buffer.limit() + " " + buffer.remaining());
		buffer.flip();
		System.out.println(buffer.position() + " " + buffer.limit() + " " + buffer.remaining());
		System.out.println(buffer.getInt());
		System.out.println(buffer.position() + " " + buffer.limit() + " " + buffer.remaining());
		buffer.flip();
		
		byte b = buffer.get(0);
		b = (byte) (0x80|b);
		buffer.put(0,b);
		System.out.println(buffer.position() + " " + buffer.limit() + " " + buffer.remaining());
		int end = buffer.getInt();
		System.out.println(end);
		boolean bb = (end & 0x80000000) != 0;
		int realEnd = (end & 0x7fffffff);
		System.out.println(bb + " " + realEnd);
		
		Short short1 = 1234;
		Long l1 = System.currentTimeMillis();
		Long l2 = 123451l;
		
		double d = l1.doubleValue()/System.currentTimeMillis();
		System.out.println(d);
		System.out.println(short1.doubleValue()/l2);
	}

}
