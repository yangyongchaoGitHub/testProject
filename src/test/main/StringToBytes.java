package test.main;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class StringToBytes {
	private static String hwaddr = "中文呢";

	public static void main(String[] args) {
		String string = "114.115.143.70:9872";
		try {
			System.out.println(string.length() + " bytes length " + string.getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		char[] chars = string.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			//System.out.println(chars[i]);
			//System.out.println(chars[i] & 0xffff);
			char c = chars[i];
			int a = c;
			//System.out.println(a);
		}
		
		byte[] bytes = string.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			System.out.println(bytes[i] & 0xff);
		}
		System.out.println("========");
		
		byte[] hws = getHwaddrByte();
		for (int i = 0; i < hws.length; i++) {
			System.out.print((hws[i] & 0xff) + " ");
		}
		
		System.out.println("");
		
		char cc = 0x5159;
		
		System.out.println(cc);
		
		System.out.println(new String(chars));
		
		ByteBuffer buffer = ByteBuffer.wrap(hws);
		System.out.println(getValuesString(buffer, 0, hws.length));
		
	}

	private static byte[] getHwaddrByte() {
		byte[] hwaddrs = new byte[65];
		if (hwaddr != null) {
			char[] hwaddr_c = hwaddr.toCharArray();
			byte[] temp = new byte[hwaddr_c.length * 2];
			for (int i = 0; i < hwaddr_c.length; i++) {
				int tem = i*2;
				
				temp[tem] = (byte)((hwaddr_c[i] >> 8) & 0xff);
				temp[tem+1] = (byte)(hwaddr_c[i] & 0xff);
			}
			hwaddrs[hwaddrs.length - temp.length - 1] = (byte) 0xff;
			System.arraycopy(temp, 0, hwaddrs, hwaddrs.length - temp.length, temp.length);
		}
		return hwaddrs;
	}
	
	private static String getValuesString(ByteBuffer raw, int offset, int length) {
		System.out.println(raw.position() + " " + raw.remaining());
		
		byte[] temp = new byte[length];
		raw.get(temp, offset, length);
		int start = temp.length;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == 0xff) {
				start = i;
			}
		}

		char[] chars = new char[(start - 1) / 2];
		for (int i = 0; i < chars.length; i++) {
			int tem = start + (2 * i);
			
			chars[i] = (char) (((temp[tem] << 8) | temp[tem + 1]) & 0xffff);
		}

		return new String(chars);
	}
}
