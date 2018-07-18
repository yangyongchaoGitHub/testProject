package test.main;

import java.nio.ByteBuffer;

public class StringToBytesTest2 {

	public static void main(String[] args) {
		String str = "bc6d61696e5265736f75726365";
		byte[] bytes = getValuesByte(40, str);
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i] + " ");
		}
		System.out.println("");
		System.out.println(new String(bytes));

		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		System.out.println(getValuesString(buffer, 0, buffer.capacity()));
	}
	
	
	private static String getStringValues(byte[] bytes) {
		int start = -1, length = bytes.length;
		
		for (int i = 0; i < length; i++) {
			if (bytes[i] == '"') {
				if (start == -1) {
					start = i;	
					break;
				}
			}
		}
		return new String(bytes, start, length - start);
	}
	
	private static byte[] getValuesByte(int resultSize, String value) {
		byte[] result = new byte[resultSize];
		byte[] temp = value.getBytes();
		if (temp != null) {
			result[result.length - temp.length - 1] = '"';
			System.arraycopy(temp, 0, result, result.length - temp.length, temp.length);	
		}
		return result;
	}
	
	private static String getValuesString(ByteBuffer raw, int offset, int length) {
		int start = -1;
		byte[] raws = raw.array();
		
		for (int i = offset; i < length; i++) {
			if (raws[i] == '"') {
				if (start == -1) {
					start = i + 1;	
					break;
				}
			}
		}
		if (start == -1) {
			return null;
		}
		
		return new String(raws, start, length - start);
	}
}
