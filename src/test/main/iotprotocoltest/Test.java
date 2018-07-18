package test.main.iotprotocoltest;

import java.nio.ByteBuffer;

public class Test {

	public static void main(String[] args) {
		// 打开控制箱门 v= 0
		HSTVCPacket p1 = new HSTVCPacket();
		p1.type = HSTVCPacket.TYPE_DOOR;
		p1.door = 0;
		System.out.println(getHex(p1.encode()));
		p1.door = 1;
		System.out.println(getHex(p1.encode()));
		
		//空气开关设置
		HSTVCPacket p3 = new HSTVCPacket();
		p3.type = HSTVCPacket.TYPE_POWER;
		p3.power = 0;
		System.out.println(getHex(p3.encode()));
		
		p3.power = 1;
		System.out.println(getHex(p3.encode()));
		
		//温度设置
		HSTVCPacket p2 = new HSTVCPacket();
		p2.type = HSTVCPacket.TYPE_FAN;
		p2.temperature = 25;
		System.out.println(getHex(p2.encode()));
		
		
		System.out.println("------------------- switch ↓ 全部正常");
		byte[] jxfstatus = new byte[]{(byte) 0xff, 0x02, 0x01, 0x00, 0x02};
		ByteBuffer raw = ByteBuffer.wrap(jxfstatus);
		raw.position(jxfstatus.length);
		
		HSTVCPacket pd1 = new HSTVCPacket();
		int recod = pd1.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println("bJXFstatus: " + pd1.bStatusJXF);
			System.out.println("JXF_status: " + pd1.status_JXF);
		}
		
		System.out.println("----------------------- reading ↓ ");
		byte[] jxfreading = new byte[]{(byte) 0xff, 0x06, 0x02, 0x20, 0x30, (byte) 0x99, 0x05, (byte) 0xca, (byte) 0xbf};
		raw = ByteBuffer.wrap(jxfreading);
		raw.position(jxfreading.length);
		HSTVCPacket pd2 = new HSTVCPacket();
		recod = pd2.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println("bJXFsensor: " + pd2.bSensor);
			System.out.println("JXF_sensor: " + getHex(pd2.sensors));
		}
		
		System.out.println("-------------------- net ↓ 网口3异常");
		
		byte[] jxfnet = new byte[]{(byte) 0xff, 0x02, 0x03, 0x04, 0x08};
		raw = ByteBuffer.wrap(jxfnet);
		raw.position(jxfnet.length);
		HSTVCPacket pd3 = new HSTVCPacket();
		recod = pd3.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println("bNETCOM: " + pd3.bNet);
			System.out.println("JXF_NETCOM: " + pd3.net);
		}
		
		System.out.println("--------------------- 12v ↓ 12v供电口2异常");
		
		byte[] jxf12v = new byte[]{(byte) 0xff, 0x02, 0x04, 0x01, 0x06};
		raw = ByteBuffer.wrap(jxf12v);
		raw.position(jxf12v.length);
		HSTVCPacket pd4 = new HSTVCPacket();
		recod = pd4.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println("b12V: " + pd4.b12V);
			System.out.println("v12_status: " + pd4.v12_status);
		}
		
		System.out.println("--------------------- 24v ↓ 24v供电口2异常");
		
		byte[] jxf24v = new byte[]{(byte) 0xff, 0x02, 0x05, 0x02, 0x08};
		raw = ByteBuffer.wrap(jxf24v);
		raw.position(jxf24v.length);
		HSTVCPacket pd5 = new HSTVCPacket();
		recod = pd5.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println("b24V: " + pd5.b24V);
			System.out.println("v24_status: " + pd5.v24_status);
		}
		
		System.out.println("--------------------- mult jxf状态+传感器读数");
		byte[] jxfmt1 = new byte[]{(byte) 0xff, 0x08, 0x01, 0x00, 0x02, 0x20, 0x30, 
				(byte) 0x99, 0x05, (byte) 0xc9, (byte) 0xc1};
		raw = ByteBuffer.wrap(jxfmt1);
		raw.position(jxfmt1.length);
		HSTVCPacket pm1 = new HSTVCPacket();
		recod = pm1.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println(pm1.bStatusJXF + " | " + 
		pm1.bSensor + " | " + pm1.bNet + " | " + pm1.b12V + " | " + pm1.b24V);
			System.out.println("JXF_status: " + pm1.status_JXF);
			System.out.println("JXF_sensor: " + getHex(pm1.sensors));
		}
		
		System.out.println("------------------------- final  jxf状态+传感器读数 和多余的0xff");
		
		jxfmt1 = new byte[]{(byte) 0xff, 0x08, 0x01, 0x00, 0x02, 0x20, 0x30, 
				(byte) 0x99, 0x05, (byte) 0xc9, (byte) 0xc1, 0x29, 0x03, (byte) 0xff};
		raw = ByteBuffer.allocate(200);
		raw.put(jxfmt1);
		
		HSTVCPacket pf1 = new HSTVCPacket();
		recod = pf1.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println(pf1.bStatusJXF + " | " + 
					pf1.bSensor + " | " + pf1.bNet + " | " + pf1.b12V + " | " + pf1.b24V);
			System.out.println("JXF_status: " + pf1.status_JXF);
			System.out.println("JXF_sensor: " + getHex(pf1.sensors));
		}
		
		System.out.println("---------------- final 2  24v供电异常");
		
		byte[] jxfft1 = new byte[]{0x02, 0x05, 0x02, 0x08};
		raw.put(jxfft1);
		HSTVCPacket pf2 = new HSTVCPacket();
		recod = pf2.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println(pf2.bStatusJXF + " | " + 
					pf2.bSensor + " | " + pf2.bNet + " | " + pf2.b12V + " | " + pf2.b24V);
			System.out.println("v24_status: " + pf2.v24_status);
		}
		
		System.out.println("----------------------final 3 add raw 12vstatus ");
		
		raw.put(jxf12v);
		HSTVCPacket pf3 = new HSTVCPacket();
		recod  = pf3.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println(pf3.bStatusJXF + " | " + 
					pf3.bSensor + " | " + pf3.bNet + " | " + pf3.b12V + " | " + pf3.b24V);
			System.out.println("v12_status: " + pf3.v12_status);
		}
		
		
		System.out.println("-------------------- really final ");
		
		byte[] jxfrf1 = new byte[]{(byte) 0xff, 0x08, 0x01, 0x00, 0x02, 0x20, 0x30, 
				(byte) 0x99, 0x05, (byte) 0xc9, (byte) 0xc1, (byte) 0xaa, 0x26, (byte) 0xff};
		raw.put(jxfrf1);
		HSTVCPacket prf1 = new HSTVCPacket();
		recod = prf1.decode(raw, 0);
		System.out.println("recod : " + recod);
		if (recod >= 0) {
			System.out.println(prf1.bStatusJXF + " | " + 
					prf1.bSensor + " | " + prf1.bNet + " | " + prf1.b12V + " | " + prf1.b24V);
			System.out.println("JXF_status: " + prf1.status_JXF);
			System.out.println("JXF_sensor: " + getHex(prf1.sensors));
		}
		
		System.out.println("---------------- final 2  24v供电异常 1");
		
		byte[] jxfrf2 = new byte[]{0x02, 0x05};
		raw.put(jxfrf2);
		HSTVCPacket prf2 = new HSTVCPacket();
		recod = prf2.decode(raw, 0);
		System.out.println("recod : " + recod);
		
		if (recod >= 0) {
			System.out.println(prf2.bStatusJXF + " | " + 
					prf2.bSensor + " | " + prf2.bNet + " | " + prf2.b12V + " | " + prf2.b24V);
			System.out.println("v24_status: " + prf2.v24_status);
		}
		
		System.out.println("---------------- final 2  24v供电异常 2");
		
		byte[] jxfrf3 = new byte[]{0x02, 0x08};
		raw.put(jxfrf3);
		
		HSTVCPacket prf3 = new HSTVCPacket();
		recod = prf3.decode(raw, 0);
		System.out.println("recod : " + recod);
		
		if (recod >= 0) {
			System.out.println(prf3.bStatusJXF + " | " + 
					prf3.bSensor + " | " + prf3.bNet + " | " + prf3.b12V + " | " + prf3.b24V);
			System.out.println("v24_status: " + prf3.v24_status);
		}
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
