package test.main;

import java.nio.ByteBuffer;

public class ByteLengSplitTest {

	public static void main(String[] args) {
		int length = 11365;

		if (length > 500) {
			int c = (length / 500) + 1;
			for (int i = 0; i < c; i++) {
				if (i == 0) {
					System.out.println(0 + " " + 500);
				} else if (i == c - 1) {
					System.out.println((i * 500 + 1) + " " + length + "||" + (length - (i * 500 + 1)));
				} else {
					System.out.println((i * 500 + 1) + " " + (i + 1) * 500 + "||" + 500);
				}
			}
		} else {
			System.out.println(length);
		}
		ByteBuffer bb = ByteBuffer.allocate(0);

		System.out.println(bb);
	}

	
}
