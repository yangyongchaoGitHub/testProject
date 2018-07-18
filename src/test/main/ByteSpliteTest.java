package test.main;

public class ByteSpliteTest {

	public static void main(String[] args) {
		int i=12;
		byte b;
		b = (byte) ((i >> 2) & 0x3);
		System.out.println(b);
		
		System.out.println((2<<1)-1);
		System.out.println("---------------");
		test(192, 0, 2);
		test(192, 2, 2);
		test(192, 4, 2);
		test(192, 6, 2);
		test(192, 8, 2);
		test(192, 10, 2);
		test(192, 12, 2);
	}
	
	public static byte test(int v, int offset, int len) {
		System.out.println((v>>offset) & ((byte)((2<<(len-1))-1)));
		return 0x00;
	}
}
