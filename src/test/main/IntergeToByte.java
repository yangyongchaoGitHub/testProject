package test.main;

import java.nio.ByteBuffer;

public class IntergeToByte {
	static int hbsel = 0;
	static int fb = 23;

	public static void main(String[] args) {
		System.out.println(getConfigFrequency());
	}

	public static byte getConfigFrequency() {
        byte result = 0x00;
        result = (byte) (result | 0x40);
        result = hbsel == 1 ? (byte) (result | 0x20) : result;
        byte cFb = (byte) (fb & 0x1f);
        result = (byte) (result | cFb);
        return result;
    }
}
