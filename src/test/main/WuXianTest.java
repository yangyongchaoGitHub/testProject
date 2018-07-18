package test.main;

import java.text.DecimalFormat;

public class WuXianTest {
	public static DecimalFormat df2 = new DecimalFormat("#.00");

	public static void main(String[] args) {
		double d = 0.0;
		String btStr = Helper.df2.format(d);
		System.out.println("btStr = " + btStr);
		
		String[] hl = btStr.split("[.]");
		byte h = 0;
        byte l = 0;
        if (hl[0].length() > 0) {
            h = (byte) Integer.parseInt(hl[0]);
        }

        if (hl[1].length() > 0) {
            l = (byte) Integer.parseInt(hl[1]);
        }
        
        System.out.println(h);
        System.out.println(l);
        
        byte bat_H = 0x02;
        byte bat_L = (byte) 0x65;
        double bat_code = (((bat_H & 0xff) << 8) | (bat_L & 0xff));
        System.out.println(bat_code);
        System.out.println(bat_code/1024*3.3*2);
	}

}
