package test.main;

import java.nio.ByteBuffer;

public class ByteBuffCloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBuffer b1 = ByteBuffer.allocate(5);
		b1.put((byte) 0x31);
		b1.put((byte) 0x32);
		b1.put((byte) 0x33);
		b1.put((byte) 0x34);
		b1.put((byte) 0x35);
		System.out.println(b1.position());
		
		ByteBuffer b2 = null;
		System.out.println(b2);
		b2 = b1;
		System.out.println(b2);
		
		for (int i = 0; i < b1.array().length; i++) {
			System.out.println("b1 " + b1.array()[i]);
		}
		
		for (int i = 0; i < b2.array().length; i++) {
			System.out.println("b2 " + b2.array()[i]);
		}
		
		System.out.println(b1);
		b1 = null;
		System.out.println(b1);
		System.out.println(b2);
		for (int i = 0; i < b2.array().length; i++) {
			System.out.println("b2 " + b2.array()[i]);
		}
	}

}
