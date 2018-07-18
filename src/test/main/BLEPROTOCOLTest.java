package test.main;

public class BLEPROTOCOLTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ts = System.currentTimeMillis() + "";
		System.out.println(ts);
		byte[] tss = getValuesByte(21, ts);
		System.out.println(tss.length);
		for (byte b : tss) {
			System.out.print(b + " ");
		}
	}
	
	public static byte[] getValuesByte(int resultSize, String value) {
        byte[] result = new byte[resultSize];
        if(value != null) {
            byte[] temp = value.getBytes();
            result[result.length - temp.length - 1] = 34;
            System.arraycopy(temp, 0, result, result.length - temp.length, temp.length);
        }

        return result;
    }
}
