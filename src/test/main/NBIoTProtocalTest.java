package test.main;

public class NBIoTProtocalTest {

	public static void main(String[] args) {
		//01f00035000050ffffffcd3836333730333033303539353838333436303131313137343830383635390000015FFB289A18010017010014010000000000000000000000000000000000000000c4

		String IMEI = "863703035109391";
		System.out.println("imei " + getHex(IMEI.getBytes()));
		String IMSI = "460111176397736";
		System.out.println("imsi " + getHex(IMSI.getBytes()));
	}
	
	public static String getHex(byte[] data) {
        String result = "";
        for (byte b : data) {
            String str = Integer.toHexString(b & 0xff);
            if (str.length() == 1) {
                str = "0" + str;
            }
            result += str;
        }
        return result;
    }
}
