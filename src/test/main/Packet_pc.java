package test.main;


import java.nio.ByteBuffer;

import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;

/**
 * Created by Administrator on 2018/5/11 0011.
 *
 */
public class Packet_pc {
    public static final byte TYPE_HEART = 0x00;
    public static final byte TYPE_SETTING = 0x01;
    public static final byte TYPE_SEARCH = 0x02;
    public static final byte TYPE_SEARCH_RESULT = (byte) 0x82;
    public static final byte TYPE_DEVICE_ADD = 0x03;
    public static final byte TYPE_DEVICE_DEL = 0x04;
    public static final byte TYPE_FIND = 0x05;
    public static final byte TYPE_FIND_RESULT = (byte) 0x85;
    public static final byte TYPE_UPLOAD_DATA = (byte) 0x07;
    public static final byte TYPE_SEARCH_DEVICE = (byte) 0x08;
    public static final byte TYPE_SEARCH_DEVICE_RESULT = (byte) 0x88;
    public static final byte TYPE_COUNT_DEVICE = (byte) 0x09;
    public static final byte TYPE_COUNT_DEVICE_RESULT = (byte) 0x89;

    public byte head = (byte) 0xfe;
    public byte length = 0x04;
    public byte type;
    public byte cs;
    public ByteBuffer data;

    private int OFFSET_HEAD = 0;
    private int OFFSET_LENGTH = 1;
    private int OFFSET_TYPE = 2;
    private int OFFSET_DATA = 3;

    public boolean decode(ByteBuffer buffer,  int start) {
        int right = buffer.position();

        
        if (right - start >= 2) {
            byte[] bytes = buffer.array();
            if (bytes[start] == head) {

                int length = (bytes[start + 1] & 0xff);
                
                if (length == 254 || length == 0) {
                	return decode(buffer, start + 1);
				}
                
                if (length <= (right - start)) {
                	//System.out.println("left " + bytes[start + length - 1] + " right " + getCS(bytes, start, start + length - 1));
                	//System.out.println("start: " + start + " | length: " + length + " | " + ((int)(bytes[1] & 0xff)));
                    if (bytes[start + length - 1] == getCS(bytes, start, start + length - 1)) {
                        cs = bytes[start + length - 1];
                        type = bytes[start + 2];
                        this.length = (byte) length;
                        data = ByteBuffer.allocate(length - 4);
                        System.arraycopy(bytes, start + 3, data.array(), 0, length - 4);

                        System.arraycopy(bytes, start + length, buffer.array(), 0, right - start - length);
                        buffer.position(right - start - length);
                        return true;
                    } else {
                        return decode(buffer, start + 1);
                    }
                } else {
                    return false;
                }
            }
            return decode(buffer, start + 1);
        }
        System.out.println("return start " + start + "  " + buffer.get(start));
        return false;
    }

    private byte getCS(byte[] bytes, int start, int end) {
        byte cs = 0x00;
        if (bytes != null) {
            for (int i = start; i < end; i++) {
                cs += bytes[i];
            }
            return cs;
        }
        return 0x00;
    }

    public ByteBuffer encode() {
        ByteBuffer buffer;

        buffer = ByteBuffer.allocate(length);

        buffer.put(head);
        buffer.put(length);
        buffer.put(type);
        if (data != null) {
            buffer.put(data.array());
        }
        buffer.put(getCS(buffer.array(), 0, length));

        return buffer;
    }

    public String decodeDevice() {
        if (data != null) {
            return Helper.getHex(data.array());
        }
        return null;
    }

    public Packet_pc clone() {
        Packet_pc pkt = new Packet_pc();
        pkt.length = length;
        pkt.type = type;
        pkt.cs = cs;
        pkt.data = data;
        return pkt;
    }
}
