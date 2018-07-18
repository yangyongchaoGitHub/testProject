package test.main;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2018/5/15 0015.
 *
 */
public class Packet_original {
    public byte head = (byte) 0xff;
    public byte sn;
    public int point; //2 3 4 5
    public boolean bPositive; //是否是正数
    public byte unit; //1 == inch   || 0 == mm
    public boolean bDelete;
    public ByteBuffer data;
    public byte cs;

    public ByteBuffer encode() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(head);
        buffer.put(sn);
        byte cfg = 0x00;
        if (!bPositive) {
            cfg = (byte) (cfg | 0x80);
        }

        byte point_b = (byte) point;
        if (point == 2) {
            point_b = 0x04;
        } else if (point == 3) {
            point_b = 0x0c;
        } else if (point == 4) {
            point_b = 0x02;
        } else if (point == 5) {
            point_b = 0x0a;
        }

        cfg = (byte) (cfg | ((point_b & 0x0f) << 3));
        cfg = (byte) (cfg | (unit & 0x07));

        buffer.put(cfg);

        buffer.put(data.array());
        buffer.put((byte) (bDelete? 0x00: 0x01));
        buffer.put(Helper.getCS(buffer.array(), 0, 8));

        return buffer;
    }
}
