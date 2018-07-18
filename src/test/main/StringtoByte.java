package test.main;

public class StringtoByte {

	public static void main(String[] args) {
		String net = "-126";
		byte b = Byte.parseByte(net);
		System.out.println(b);
		System.out.print(((b>>7) & 0x01) + " ");
		System.out.print(((b>>6) & 0x01) + " ");
		System.out.print(((b>>5) & 0x01) + " ");
		System.out.print(((b>>4) & 0x01) + " ");
		System.out.print(((b>>3) & 0x01) + " ");
		System.out.print(((b>>2) & 0x01) + " ");
		System.out.print(((b>>1) & 0x01) + " ");
		System.out.print(((b>>0) & 0x01) + " ");
		System.out.println();
		
		net = "-110";
		int i = Integer.parseInt(net);
		System.out.println(i);
		byte b1 = (byte) (i & 0xff);
		System.out.println("b1 = " + b1);
		System.out.print(((i>>7) & 0x01) + " ");
		System.out.print(((i>>6) & 0x01) + " ");
		System.out.print(((i>>5) & 0x01) + " ");
		System.out.print(((i>>4) & 0x01) + " ");
		System.out.print(((i>>3) & 0x01) + " ");
		System.out.print(((i>>2) & 0x01) + " ");
		System.out.print(((i>>1) & 0x01) + " ");
		System.out.print(((i>>0) & 0x01) + " ");
		
		System.out.println("");
		byte bb1 = (byte) 0xff;
		System.out.println((float)(bb1 & 0xff));
	}

}
