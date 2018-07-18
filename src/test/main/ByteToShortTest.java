package test.main;

public class ByteToShortTest {

	public static void main(String[] args) {
		byte[] bytes = new byte[]{(byte) 0xff, 0x22};
		int s = ((bytes[0] << 8) | (bytes[1] & 0xff)) & 0xffff;
		System.out.println(s);
		short ss = (short) (((bytes[0] << 8) | (bytes[1] & 0xff)) & 0xffff);
		System.out.println(ss);
		int i = 65535;
		short is = (short)i;
		System.out.println(is);
		System.out.println(is & 0xffff);
	}

}
