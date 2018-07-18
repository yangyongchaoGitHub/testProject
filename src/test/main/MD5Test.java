package test.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Test {
	public final static char hex[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pswdString = "123456789";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(pswdString.getBytes());
			System.out.println(md5.digest());
			byte[] bytes = md5.digest();
			char[] password = new char[bytes.length<<1];
			int k = 0;
			
			for (int i = 0; i < bytes.length; i++) {
				password[k++] = hex[((bytes[i]>>4) & 0xf)];
				password[k++] = hex[(bytes[i] & 0xf)];
			}
		
			System.out.println(new String(password));
			System.out.println(MD5Encoder("123456789"));
			testss(bytes);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testss(byte[] pswd) {
		for (int i = 0; i < pswd.length; i++) {
			System.out.println(pswd[i] & 0xff);
			/*System.out.println(hex[((pswd[i]>>4) & 0xf)]);
			System.out.println(hex[((pswd[i]) & 0xf)]);*/
		}
	}
	
	public static String MD5Encoder(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			
			System.out.println(Integer.toHexString(val));
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}
}
