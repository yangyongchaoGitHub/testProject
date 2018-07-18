package test.main;

public class SrtingTest2 {
	static String hexString = "0123456789ABCDEF";
	public static void main(String[] args) {
		Long[] l = new Long[]{3565656565656l,55452423424l,7545435345435l};
		String s = "";
		for (int i = 0; i < l.length; i++) {
			s+=l[i]+",";
		}
		System.out.println(s);
		System.out.println(s.subSequence(0, s.length()-1).toString());
		System.out.println(s.substring(0, s.length()-1));
		
		String string = "ff";
		char[] c = string.toUpperCase().toCharArray();
	    byte b = (byte) ((hexString.indexOf(c[0]) << 4) | hexString.indexOf(c[1]));
	    int bi = b & 0xff;
	    float fi = b & 0xff;
	    System.out.println("bi = " + bi);
	    System.out.println("fi = " + fi);
	    Float ff = 255.999f;
	    System.out.println(ff.toString());
	    System.out.println(ff.intValue());
	    System.out.println(bi != ff.intValue());
	    
	    
	    System.out.println(b & 0x01);
	    System.out.println((b>>1) & 0x01);
	    System.out.println(b & 0x04);
	    System.out.println(b & 0x08);
		
		//int i = Integer.parseInt(string);
		//System.out.println(i);
	}

}
