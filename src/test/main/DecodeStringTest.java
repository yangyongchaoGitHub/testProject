package test.main;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DecodeStringTest {
    static Record record;
	public static void main(String[] args) {
		record = new Record();
		byte bs[] =  new byte[] {0x0d, 0x0a, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31,
				 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31,
				 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31,
				 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31,
				 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x0d, 0x0a};
		record.put(bs, bs.length);
		record.print();
		byte []bs2 = new byte[]{0x0d, 0x0a, 0x32, 0x32, 0x32, 0x32, 0x0d, 0x0a};
		record.put(bs2, bs2.length);
		record.print();
		bs2 = new byte[]{0x0d, 0x0a, 0x33, 0x33, 0x33, 0x33, 0x0d, 0x0a};
		record.put(bs, bs.length);
		record.print();
		record.put(bs2, bs2.length);
		record.print();
		bs2 = new byte[]{0x0d, 0x0a, 0x34, 0x34, 0x34, 0x34, 0x0d, 0x0a};
		record.put(bs, bs.length);
		record.print();
		record.put(bs2, bs2.length);
		record.print();
		bs2 = new byte[]{0x0d, 0x0a, 0x35, 0x35, 0x35, 0x35, 0x0d, 0x0a};
		record.put(bs, bs.length);
		record.print();
		record.put(bs2, bs2.length);
		record.print();
		bs2 = new byte[]{0x0d, 0x0a, 0x36, 0x36, 0x36, 0x36, 0x0d, 0x0a};
		record.put(bs, bs.length);
		record.print();
		record.put(bs2, bs2.length);
		record.print();
	}
	
	private static class Record {
        final private static int MAX = 60;
        private ByteBuffer raw = ByteBuffer.allocate(MAX);
        private List<String> results = new ArrayList<>();
        private int rindex = 0;

        public void clear() {
            raw.clear();
            results.clear();
            rindex = 0;
        }

        private void lines() {
            byte[] c = raw.array();
            int end = raw.position(), i = rindex, ii = (i + 1)%MAX;
            
            System.out.println(getHex(raw.array(), raw.capacity()));
            while (i != end && ii != end) {
                ii = (i + 1) % MAX;

                if (c[i] == '\r' && c[ii] == '\n') {
                    synchronized (results) {
                        if (i > rindex) {
                            results.add(new String(c, rindex, i - rindex));
                        } else if (i < rindex) {
                        	/*System.out.println(new String(c, rindex, MAX - rindex) + " " + 
                        			new String(c, 0, i));*/
                            results.add(new String(c, rindex, MAX - rindex).concat(new String(c, 0, i)));
                        }
                    }
                    i = rindex = (i + 2) % MAX;
                } else {
                    i = ii;
                }
            }
        }

        public void put(byte[] data, int size) {
            int offset = raw.position();

            if (size < MAX) {
                if (offset + size < MAX) {
                    //Log.i(TAG, "Record put 1 test  " + raw.position() + " limit " + raw.limit());
                    raw.put(data, 0, size);
                    //Log.i(TAG, "Record put 2 test  " + raw.position() + " limit " + raw.limit());
                    //raw.limit(raw.position());
                    //Log.i(TAG, "Record put 3 test  " + raw.position() + " limit " + raw.limit());
                } else {
                    
                    int left = raw.remaining();
                    System.out.println(left);
                    for (int i = 0; i < left; i++) {
                        raw.put(data[i]);
                        System.out.println(raw.position());
                    }

                    raw.position(0);
                    for (int i = 0; i < size - left; i++) {
                        raw.put(data[left+i]);
                        System.out.println(raw.position());
                    }
                }
            }

            lines();
        }

        public String peek(int index) {
            synchronized (results) {
                if (results.size() > 0 && index < results.size()) {
                    return results.get(index);
                } else {
                    return null;
                }
            }
        }

        public void erase(int index) {
            synchronized (results) {
                if (results.size() > 0 && index < results.size()) {
                    results.remove(index);
                }
            }
        }

        public void print() {
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i));
            }
            System.out.println("");
        }

        public int getResultCount() {
            return results.size();
        }
    }
	
	public static String getHex(byte[] data, int size) {
        String result = "";
        for (int i = 0; i < size; i++) {
            String str = Integer.toHexString(data[i] & 0xff);
            if (str.length() == 1) {
                str = "0" + str;
            }
            result += str;
        }
        return result;
    }
}
