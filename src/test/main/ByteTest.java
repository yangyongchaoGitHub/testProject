package test.main;

public class ByteTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getDoubleByPositive((new byte[]{0x00, 0x00, (byte) 0x16, 0x68}), 0, 3, 1, 10)/60);
		
	}

	
	public static double getDoubleByPositive(byte[] raw, int star, int end, double base, double mult) {
        if (star > end) {
            return 0;
        }
        return (raw[end] & 0x0f) * base + (raw[end] >> 4 & 0x0f) * (base*mult) +
        		getDoubleByPositive(raw, star, --end, base*mult*mult, mult);
        /*0x04, 0x15, 0x56, 0x77 to 4155677.0 mult(+)  or (776551.4) mult(-)
                ((((raw[3] & 0xf0) >> 4) * 10 + (raw[3] & 0x0f)) * 1000000 +
                (((raw[4] & 0xf0) >> 4) * 10 + (raw[4] & 0x0f)) * 10000 +
                (((raw[5] & 0xf0) >> 4) * 10 + (raw[5] & 0x0f)) * 100 +
                (((raw[6] & 0xf0) >> 4) * 10 + (raw[6] & 0x0f)) * 1.00)*/
    }
	
	public static double getDouble(byte[] raw, int star, int end, double base, double mult) {
        if (star > end) {
            return 0;
        }
        return (raw[star] & 0x0f) * base + (raw[star] >> 4 & 0x0f) * (base*mult) +
                getDouble(raw, ++star, end, base*mult*mult, mult);
        /*0x04, 0x15, 0x56, 0x77 to 775615.04   or (405165.77)
                (raw[6] >> 4 & 0x0f) * 100000 + (raw[6] & 0x0f) * 10000 +
                (raw[5] >> 4 & 0x0f) * 1000 + (raw[5] & 0x0f) * 100 +
                (raw[4] >> 4 & 0x0f) * 10 + (raw[4] & 0x0f) +
                (raw[3] >> 4 & 0x0f) * 0.1 + (raw[3] & 0x0f) * 0.01*/
    }
}
