package test.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) {
		Date date;
		Date date1;
		try {
			date = sdf.parse("1970-01-01 00:00:00");
			date1 = sdf.parse("199999998-01-30 00:00:00");
			System.out.println(date1.getTime());
			
			System.out.println(System.currentTimeMillis());
			System.out.println(date.getTime());
			System.out.println((System.currentTimeMillis() - date.getTime())/1000);
			System.out.println(Integer.MAX_VALUE);
			System.out.println(Long.MAX_VALUE);
			long tset = 1516775187311l;
			System.out.println(sdf.format(tset));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
