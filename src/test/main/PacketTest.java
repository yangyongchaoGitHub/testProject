package test.main;

import java.nio.ByteBuffer;

public class PacketTest {
	private int fb = 3;
    public int arbitrationFrequency = 271;
    public int workerFrequency = 272;
    public int hbsel = 0;
    public byte initPower = 0x03;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PacketTest test = new PacketTest();
		
		//test.dosx();
		
		test.doDecode();
	}
	
	public void doDecode() {
		byte[] dt = {(byte) 0xfa, 0x10, 0x0d, 0x39, 0x39, 0x39, 0x39, 0x39, 0x39, 0x39, 0x39, 0x39, 0x39, 0x02, 0x00, 0x68};
		System.out.println(Helper.getHex(dt));
		Packet packet = new Packet();
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.put(dt);
		buffer.position(buffer.capacity());
		if (packet.decode(buffer, 0)) {
			System.out.println(packet.toString());
			
			CollectorData data = packet.decodeDATA();
			System.out.println(data.battery);
			System.out.println(data.serial);
			System.out.println(data.data);
		}
	}
	
	public void dosx() {
		Packet packet = new Packet();
		byte serial = 0x12;
		int af = getFC(arbitrationFrequency);
		int wf = getFC(workerFrequency);
		
		ByteBuffer data = packet.encodeConfigData(
                initPower,
                getConfigFrequency(),
                af,
                wf);
		
		System.out.println(af);
		System.out.println(wf);

		System.out.println("111 " + Helper.getHex(data.array()));
        packet.serial = serial;
        packet.data = data;
        sendPacket(packet);
	}
	
	public int getFC(double frequency) {
        return (int) ((frequency/(10*(hbsel + 1)) - fb - 24) * 64000);
    }

    public byte getConfigFrequency() {
        byte result = 0x00;
        result = (byte) (result | 0x40);
        result = hbsel == 1 ? (byte) (result | 0x20) : result;
        byte cFb = (byte) (fb & 0x1f);
        result = (byte) (result | cFb);
        return result;
    }
    
    public void sendPacket(Packet packet) {
    	System.out.println(Helper.getHex(packet.encode().array()));
    }
}
