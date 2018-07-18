package test.main;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2018/5/9 0009.
 *
 */
public class Packet {
    public static final byte TYPE_DATA = 0x10;

    private byte head = (byte) 0xfa;
    public byte type = TYPE_DATA;
    public byte serial;
    public ByteBuffer data;
    public byte cs;

    public ByteBuffer encode() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(head);
        buffer.put(type);
        buffer.put(serial);
        buffer.put(data.array());
        cs = getCS(buffer.array(), 0, 16);
        buffer.put(cs);
        return buffer;
    }

    public boolean decode(ByteBuffer buffer, int start) {
        if (buffer.position() >= 16) {
            byte[] bytes = buffer.array();
            if (bytes[start] != head) {
                return decode(buffer, start + 1);
            }
            if (bytes[start + 16 - 1] == getCS(bytes, start, start + 15)) {
                type = bytes[start + 1];
                serial = bytes[start + 2];
                data = ByteBuffer.allocate(12);
                System.arraycopy(bytes, start + 3, data.array(), 0, 12);
                cs = bytes[start + 15];

                System.arraycopy(bytes, start + 16, buffer.array(), 0, buffer.position() - start - 16);
                buffer.position(buffer.position() - start - 16);
                return true;
            }
        }

        return false;
    }
    
    public CollectorData decodeDATA() {
        if (data != null) {
            CollectorData data = new CollectorData();
            byte[] dataData = new byte[10];
            byte[] dataSrc = this.data.array();
            System.arraycopy(dataSrc, 0, dataData, 0, 10);
            byte bat_H = dataSrc[10];
            byte bat_L = dataSrc[11];
            double bat_code = (((bat_H & 0xff) << 8) | (bat_L & 0xff));
            data.battery = (float) (bat_code/1024*3.3*2);
            data.data = new String(dataData);
            data.serial = serial;
            return data;
        }
        return null;
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


    public ByteBuffer encodeConfigData(byte initPower, byte bandSelect, int arbitrationFrequency, int workerFrequency) {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put(initPower);
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        buffer.put(bandSelect);
        short af = (short) arbitrationFrequency;
        buffer.putShort(af);
        short wf = (short) workerFrequency;
        buffer.putShort(wf);
        return buffer;
    }
    
    @Override
    public String toString() {
        if (data != null) {
            return "type: " + type + " serial: " + serial + " data: " + new String(data.array()) + " cs: " + cs;
        }
        return "type: " + type + " serial: " + serial  + " cs: " + cs + " data is null";
    }
}
