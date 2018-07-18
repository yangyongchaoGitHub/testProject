package test.main;

public class CsTest {

	public static void main(String[] args) {
		byte[] raw = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05};
		System.out.println(cs(raw, 0, 5));
	}

	public static byte cs(byte[] raw, int left, int length) {
		if (raw.length < length) {
			return 0;
		}
 		if (left == length) {
			return 0;
		}
		return (byte) (raw[left] + cs(raw, ++left, length));
	}
}
