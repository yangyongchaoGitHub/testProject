package test.main;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;

public class LoopTest {
	public static final byte[] CRLF = {0x0D, 0x0A};
	
	private static void strSplit() {
        String str = "123456:sdfsaf,7890 asdfasfdasfd.wrqwrwqer.asfsafasf.safgfdgdsg";
        String[] strs = str.split("[:.,]");
        for (String s : strs){
            System.out.println(s);
        }        
    }
	
	public static void main(String[] args) {
		Record  record = new Record();
		byte[][] data = new byte[][]{new byte[]{'\r', '\n', 'a', 'b', 'c', 'd', '\r', '\n'}, 
				new byte[]{'\r', '\n', 'O', 'K', '\n'}, 
				new byte[]{'\r', '\n', '0', ',', '1', '1', '\r', '\n'}};
		
		int i = 0;
		strSplit();
		/*while(true) {
			record.put(data[i], data[i].length);
			record.print();
			System.out.println("end-------------------");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			i++;
			if (i >= 3) {
				i=0;
			}
		}*/
	}
}

class Record {
        final private static int MAX = 1000;
        private ByteBuffer raw = ByteBuffer.allocate(MAX);
        private List<String> results = new ArrayList<>();
        private int rindex = 0;

        public void clear() {
            raw.clear();
            results.clear();
            rindex = 0;
        }

        private void lines() {
            int end = raw.position(), i = rindex;
            byte[] c = raw.array();

            while (i != end) {
                int ii = (i + 1) % MAX;

                if (ii == end) {
                    return;
                }
                              
                if (c[i] == '\r' && c[ii] == '\n') {
                    synchronized (results) {
                        if (i > rindex) {
                            results.add(new String(c, rindex, i - rindex));
                        } else if (i < rindex) {
                            results.add(new String(c, rindex, MAX - rindex - 1).concat(new String(c, 0, i)));
                        }
                        
                        i = rindex = (i + 2) % MAX;    
                        continue;
                    }
                }
                
                i = ii;
            }
        }

        public void put(byte[] data, int size) {
            int offset = raw.position();

            if (size < MAX) {
                if (offset + size < MAX) {
                	System.out.println(data.length + " " + offset + " " + size);
                	
                    raw.put(data, 0, size);
                    //raw.position(raw.position() + size);
                    //raw.limit(raw.position());
                } else {
                    int left = raw.remaining();

                    for (int i = 0; i < left; i++) {
                        raw.put(data[left + i]);
                    }

                    raw.position(0);
                    for (int i = 0; i < size - left; i++) {
                        raw.put(data[left + i]);
                    }
                }
            }
            
            System.out.println("start lines " + raw.position());
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
        	System.out.println("start print  results size = " + results.size());
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i));
            }
        }

    }