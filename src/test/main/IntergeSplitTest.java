package test.main;

public class IntergeSplitTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Long ts = System.currentTimeMillis();
		int test = (ts.intValue() >> 10);
		System.out.println(ts.intValue() + " " + ts + " " + test);
		
		System.out.println(6%2);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
		System.out.println(Integer.MAX_VALUE);
		double d = 0.12136131313d;
		System.out.println((float)d);
	}

}
