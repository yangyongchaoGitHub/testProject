package test.main;

public class LongTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = Integer.MAX_VALUE;
		
		System.out.println(i);
		System.out.println(i % 1000000000);
		System.out.println(((i >> 2) & 0x000000ff) << 2);
		System.out.println(i & 0x8fffffff);
		
		System.out.println("----------------------");
		Long l = System.currentTimeMillis();
		System.out.println(l);
		int b = (int) (l.intValue() & 0x7fffffff);
		System.out.println(b);
		System.out.println(l.intValue());
		
		System.out.println(111111 | 0x0000);
		
		System.out.println(l % 1000000000);
		Long ll = 199l;
		System.out.println(ll.intValue());
		System.out.println("-------------------");
		float f = 1234.5467f;
		int fi = (int) (f%100);
		System.out.println(fi);
		System.out.println("-------------");
		float temperature = 2345.654f;
		byte[] temp = new byte[2];
		int ti = (int) ((temperature*100)%10000);
		System.out.println(ti);
		System.out.println(ti/1000);
		System.out.println((ti/100)%10);
		System.out.println((ti/10)%10);
		System.out.println(ti%10);
		temp[0] = (byte) ((((ti/1000) & 0x0f) << 4) | ((((ti/100)%10) & 0x0f)));
		temp[1] = (byte) (((((ti/10)%10) & 0x0f) << 4) | ((ti%10)));
		for (int j = 0; j < temp.length; j++) {
			System.out.println(temp[j]);
		}
	}

}
