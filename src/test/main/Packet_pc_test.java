package test.main;

import java.nio.ByteBuffer;

public class Packet_pc_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Packet_pc packet_pc = new Packet_pc();
		byte[] data = {(byte) 0xFe, 0x05, 0x03, 0x00, 0x06, (byte) 0xFe, 0x04, 0x08, 0x0a};
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.position(data.length);
		while(packet_pc.decode(buffer, 0)) {
			System.out.println(Helper.getHex(packet_pc.data.array()));
		}
		
		data = new byte[]{(byte) 0xFe, (byte) 0xFe, 0x05, 0x04, 0x00, 0x07, (byte) 0xFe, 0x05, 0x04, 0x01, 0x08
				, (byte) 0xFe};
		
		byte[] data2 = {0x05, 0x04, 0x02, 0x09, (byte) 0xFe, 0x05, 0x04, 0x03, 0x0a, (byte) 0x14,
				0x04, 0x08, 0x0a};
		
		buffer = ByteBuffer.allocate(100);
		
		buffer.put(data);
		//buffer.put(data2);
		
		while(packet_pc.decode(buffer, 0)) {
			System.out.println("data " + Helper.getHex(packet_pc.data.array()) + " type: " +packet_pc.type);
		}
		System.out.println("lost1 " + Helper.getHex(buffer.array(), buffer.position()));
		
		buffer.put(data2);
		
		while(packet_pc.decode(buffer, 0)) {
			System.out.println(Helper.getHex(packet_pc.data.array()) + " type: " +packet_pc.type);
		}
		System.out.println("lost2 " + Helper.getHex(buffer.array(), buffer.position()));
		
		String sttt = "0000f8ff03fc00100000000000000000fff000fe00000000000000000000000000000000fc00000000000000000000fe0508ff0afe040204fe0508ff0afe040204";
		byte [] bsttt = Helper.stringHexToBytes(sttt);
		
		buffer = ByteBuffer.allocate(bsttt.length);
		buffer.put(bsttt);
		while(packet_pc.decode(buffer, 0)) {
			System.out.println(Helper.getHex(packet_pc.data.array()) + " type: " +packet_pc.type);
		}
		System.out.println("lost3 " + Helper.getHex(buffer.array(), buffer.position()));
	
	}

}
