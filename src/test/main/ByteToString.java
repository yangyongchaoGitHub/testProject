package test.main;

public class ByteToString {

	public static void main(String[] args) {
		//2a47ad65d6f2a348b16d
		byte[] bytes = new byte[]{(byte) 0xbc, (byte) 0xb4, 0x6d, 0x61, 0x69, 0x6e};
		byte[] bytesget = new byte[]{(byte) 0x2a, (byte) 0x47, (byte) 0xad, 0x65, (byte) 0xd6, (byte) 0xf2, (byte) 0xa3, 0x48, (byte) 0xb1, 0x6d};
		System.out.println(new String(bytesget));

	}

}
