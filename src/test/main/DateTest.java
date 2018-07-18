package test.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTest {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) {
		TimeZone tZone =  TimeZone.getDefault();
		System.out.println(tZone.getDefault());
		System.out.println(tZone.getID());
		System.out.println(tZone.getRawOffset());
		long l = 1514254596798l;
		System.out.println(sdf.format(new Date(l)));
		System.out.println(System.currentTimeMillis());
		
		//Calendar calendar = Calendar.getInstance();
		//Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));
//		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
//		System.out.println(calendar.get(Calendar.YEAR));
//		System.out.println(calendar.get(Calendar.MONTH));
//		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
//		System.out.println(calendar.get(Calendar.DATE));
//		System.out.println("-----------");
//		//calendar.add(Calendar.DATE, 365*3);
//		
//		System.out.println(calendar.get(Calendar.YEAR));
//		System.out.println(calendar.get(Calendar.MONTH));
//		System.out.println(calendar.get(Calendar.DATE));
//		
//		System.out.println(calendar.getTimeZone());
//		System.out.println(TimeZone.getTimeZone("GMT+12").getID());
//		String id = calendar.getTimeZone().getID();
//		System.out.println(TimeZone.getTimeZone("Asia/Shanghai").getID());
//		System.out.println(calendar.getTime());
//		System.out.println(calendar.getTimeInMillis());
//		long l1 = 1513107717100l;
//		long l2 = 1513078918212l;
//		System.out.println(28800000/60/60);
//		System.out.println((l1 - l2)/60/60);
//		
//		System.out.println("---------------");
//		System.out.println(TimeZone.getDefault().getID());
//		
//		System.out.println(System.currentTimeMillis());
//		System.out.println(calendar.getTimeInMillis());
//		System.out.println(System.nanoTime());
//		System.out.println(sdf.format(System.currentTimeMillis()));
//		
//		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
//		
//		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(System.nanoTime());
//		System.out.println(calendar.getTimeInMillis());
//		System.out.println(System.currentTimeMillis());
//		System.out.println(TimeZone.getDefault().getID());
//		System.out.println(sdf.format(System.currentTimeMillis()));
		
		
	}
}
