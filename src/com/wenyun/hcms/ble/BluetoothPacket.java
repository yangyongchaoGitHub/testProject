package com.wenyun.hcms.ble;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/11/24 0024.
 *
 * {       start        |      sn      |bS&action|bR&target|   length    |   cs  |   dtu    }
 * {       1byte        |    2byte     | 1byte   | 1byte  |     2byte    | 1byte |  ···dtu  }
 * V1.0.0
 */

public class BluetoothPacket {
    private static final String TAG = "BluetoothPacket_1.1.2";
    final static public byte[] START = new byte[]{0x67};
    final static public int HEAD_SIZE = 8;
    final static public int MAX_LENGTH = 500;
    final static private int OFF_SN = 1;
    final static private int OFF_ACTION = 3;
    final static private int OFF_TARGET = 4;
    final static private int OFF_LH = 5;
    final static private int OFF_LL = 6;
    final static private int OFF_CS = 7;
    final static private int OFF_DTU = 8;

    public int sn;
    public byte action;
    public byte target;
    public int length = 0;
    public byte cs = 0;
    public boolean bSerial = false;
    public boolean bResponse = false;
    public ByteBuffer dtu;
    private volatile int packet_length;
    private volatile int packet_listen_length;
    
    public BluetoothPacket() {}

    public BluetoothPacket (BluetoothPacket packet) {
    	sn = packet.sn;
        bResponse = packet.bResponse;
        length = packet.length;
        target = packet.target;
        action = packet.action;
        cs = packet.cs;
        dtu = packet.dtu;
    }

    public BluetoothPacket(int sn, byte action, byte target, ByteBuffer dtu) {
        this.sn = sn;
        this.action = action;
        this.target = target;
        this.dtu = dtu;
    }
    
    public BluetoothPacket(int sn, byte action, byte target) {
    	this.sn = sn;
        this.action = action;
        this.target = target;
    }

    private byte target(boolean bResponse, byte target) {
        return bResponse? (byte)(0x80|target) : target;
    }
    
    private byte action(boolean bSerial, byte action) {
    	return bSerial? (byte)(0x80|action) : action;
    }

    public byte[] encode() {
        if (dtu != null && dtu.capacity() > 0) {
            length = (short) dtu.capacity();
        }
        ByteBuffer buf = ByteBuffer.allocate(length + HEAD_SIZE);
        buf.put(START[0]);
        buf.putShort((short)sn);
        buf.put(action(bSerial, action));
        buf.put(target(bResponse, target));
        buf.putShort((short)length);
        if (length > 0) {
        	buf.put(CRCCheck.calcCrc8(dtu.array(), 0, length));
        	buf.put(dtu.array());
        }
        return  buf.array();
    }

    static public void print(String info, ByteBuffer raw) {
    	print(info, raw.array(), raw.capacity());
    }
    
    static public void print(String info, ByteBuffer raw, int size) {
    	print(info, raw.array(), size);
    }
    
    static public void print(String info, byte[] buf, int size) {
    	System.out.println(info);
    	for (int i = 0; i < size; i++) {
			String str = Integer.toHexString(buf[i] & 0xff);
            if (str.length() == 1) {
                str = "0" + str;
            }
            System.out.print(str);
		}
		System.out.println("\n");
    }
    
    public int decode(ByteBuffer raw, int skip) {
    	int size = align(raw, skip);
    	
    	if (size >= HEAD_SIZE) {
        	byte[] buf = raw.array();
        	int total;
        	
        	length = ((buf[OFF_LH] << 8) | (buf[OFF_LL] & 0xff)) & 0xffff;        
        	total = length + HEAD_SIZE;
        	
            if (size >= total) {
				int left = size - total;
				cs = buf[OFF_CS];
				
				if (!validate(raw)) {
					System.out.println("decode false !!!");
					//print("decode false:", raw, size);
					return decode(raw, 1);
				}
	
				sn = (((buf[OFF_SN] << 8)) | buf[OFF_SN + 1] & 0xff) & 0xffff;
				byte bsa = buf[OFF_ACTION];
				action = (byte) (bsa & 0x7F);
				bSerial = ((bsa & 0x80) != 0);
				byte brt = buf[OFF_TARGET];
				bResponse = ((brt & 0x80) != 0);
				target = (byte) (brt & 0x7F);
				dtu = ByteBuffer.allocate(length);				
				System.arraycopy(buf, OFF_DTU, dtu.array(), 0, length);	
				
				System.arraycopy(buf, total, buf, 0, left);
				raw.position(left);
	
				return 0;
            } else if (length > MAX_LENGTH) {
				decode(raw, 1);
			} 
        }
        
        return -1;
    }
    
    /**
     * return int array length == 2, first args is package total length, second args is listen length 
     * @return int array of decode message 
     */
    public int[] getDecodeMsg() {
    	return new int[]{packet_length, packet_listen_length};
    }

    private int align(ByteBuffer raw, int index) {
    	byte[] buf = raw.array();
    	int offset = raw.position();
    	
        for (int i = index; i < offset; i++) {
            if (buf[i] == START[0]) {
            	offset -= i; 
            	
                System.arraycopy(buf, i, buf, 0, offset);
                
                raw.position(offset);
                
                return offset;
            }
        }
        
        raw.position(0);
        
        return 0;
    }

    public boolean validate(ByteBuffer raw) {
        byte myCs = CRCCheck.calcCrc8(raw.array(), OFF_DTU, length);
        System.out.println(TAG + " mycs = " + myCs + " hecs = " + cs);
        return myCs == cs;
    }

    public final class Action {
        public static final byte CREATE = 0;
        public static final byte GETONE = 1;
        public static final byte SEARCH = 2;
        public static final byte UPDATE = 3;
        public static final byte DELETE = 4;
        public static final byte CTRL = 5;
        public static final byte LOGIN = 7;
        public static final byte ERROR = 8;
        public static final byte NORMAL = 9;
        public static final byte SYNC = 10;
        public static final byte CONNECT = 11;
        public static final byte COUNT = 12;
        public static final byte ACK = 0x7f;
    }

    public final class Target {
        public static final byte CONCENTRATOR = 0;
        public static final byte ACTUATOR = 1;
        public static final byte HEATMETER = 2;
        public static final byte EVENT = 3;
        public static final byte HOUSE = 4;
        public static final byte OWNER = 5;
        public static final byte LOG = 6;
        public static final byte HEATING_DATA = 7;
        public static final byte COMMUNITY = 8;
        public static final byte HEATMETER_RAWDATA = 9;
        public static final byte ACTUATOR_RAWDATA = 10;
        public static final byte CHARGE_LIST = 11;
        public static final byte SMS_VERIFICATION = 12;
        public static final byte ALARM_STATUS = 13;
    }

    /**
     *
     * @param object object to json
     * @return object json bytes
     */
    public static ByteBuffer serializer(Object object) {
        if(object == null) {
            return null;
        } else {
            try {
                return ByteBuffer.wrap((new ObjectMapper()).writeValueAsString(object).getBytes());
            } catch (Exception var2) {
                System.out.println(" protocol serializer error : " + var2.getCause() + " --message : " + var2.getMessage());
                return null;
            }
        }
    }

    /**
     *
     * @param clazz deserializer target class
     * @param bb object bytes
     * @return a target class or null
     */
    public static Object deserializer(Class<?> clazz, ByteBuffer bb) {
    	if (bb == null || bb.capacity() == 0) {
			return null;
		}
        try {
            return (new ObjectMapper()).readValue(new String(bb.array()), clazz);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    /**
     * json arrays deserializer
     * @param bb Object json bytes
     * @param collectionClass collect type
     * @param elementClasses collect value type
     * @return a collect value is elementClass collector
     */
    public static Object deserializer(ByteBuffer bb, Class<?> collectionClass, Class... elementClasses) {
    	if (bb == null || bb.capacity() == 0) {
			return null;
		}
        try {
            ObjectMapper e = new ObjectMapper();
            JavaType javaType = e.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return e.readValue(new String(bb.array()), javaType);
        } catch (Exception var5) {
            System.out.println(" protocol deserializer error : " + var5.getCause() + " --message : " + var5.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return dtu != null ?
                TAG + "[action = " + action +
                        ", target = " + target +
                        ", sn = " + sn +
                        ", bResponse = " + bResponse +
                        ", length = " + length +
                        ", cs = " + cs +
                        ", dtu = " + getHex(dtu.array()) :
                TAG + "[action = " + action +
                        ", target = " + target +
                        ", sn = " + sn +
                        ", bResponse = " + bResponse +
                        ", length = " + length +
                        ", cs = " + cs +
                        ", dtu = null";
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


