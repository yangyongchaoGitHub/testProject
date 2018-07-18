package test.main;

import java.text.DecimalFormat;

public class StringToByteLengthTest {
	public static DecimalFormat df2 = new DecimalFormat("#.00");
	
	public static void main(String[] args) {
		byte[] tt = {0x39, 0x39};
		System.out.println(new String(tt));
		String str = "0.00007855";
		byte[] str_b = str.getBytes();
		System.out.println(str_b.length);
		
		double dd = 3.999565;
		System.out.println(df2.format(dd));
		byte[] dd_s = (df2.format(dd) + "").getBytes();
		for (int i = 0; i < dd_s.length; i++) {
		    System.out.println(dd_s[i]);
		}
		
		byte[] tett = {0x2e, 0x30, 0x30};
		System.out.println(new String(tett));
	}
}
