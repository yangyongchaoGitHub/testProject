package test.main;

import java.nio.ByteBuffer;

public class Packet_original_test {

	public static void main(String[] args) {
		Packet_original_test test = new Packet_original_test();
		Packet_original packet_original = new Packet_original();
		packet_original.sn = 0x01;
		packet_original.point = 2;
		packet_original.bPositive = true;
		packet_original.unit = 1;
		packet_original.bDelete = false;
		
		String rData = "+5.5716";
        String end;
        int point = 2;
        boolean bPositive = true;
        String default_end = "000000";
        String punctuation = rData.substring(0, 1);
        if (punctuation.equals("-")) {
            bPositive = false;
            rData = rData.substring(1, rData.length());
        }

        if (punctuation.equals("+")) {
            bPositive = true;
            rData = rData.substring(1, rData.length());
        }

        String[] data = rData.split("\\.");
        
        if (data.length == 2) {
        	String left_str = data[0];
            String right_str = data[1];
            int left = left_str.length();
            int right = right_str.length();
            
            while(left_str.length() > 0 && left_str.indexOf("0") == 0) {
            	
            	left_str = left_str.substring(1, left_str.length());
            	left = left_str.length();
            	if (left == 0) {
					left = 1;
					left_str = "0";
					break;
				}
            }
            
            if (left > 6) {
                end = "999999";
            } else if (left + right < 6) {
                point = 6-left;
                System.out.println(point + " pp");
                end = left_str + right_str + default_end.substring(0, 6 - left - right);
            } else {
				point = 6 - left;
				System.out.println("222  --- " + left_str);
				end = left_str + right_str.substring(0, 6 - left);
				
            }

        } else {
            end = default_end;
        }
        
        System.out.println("end " + end);
        
        byte[] ends = new byte[6];
        for (int i = 0; i < 6; i++) {
            ends[i] = Byte.parseByte(end.substring(i, i + 1));
        }
        byte[] final_ = new byte[3];
        final_[0] = (byte) ((ends[0] <<4) | (ends[1] & 0x0f));
        final_[1] = (byte) ((ends[2] <<4) | (ends[3] & 0x0f));
        final_[2] = (byte) ((ends[4] <<4) | (ends[5] & 0x0f));
        
        System.out.println("final  " + Helper.getHex(final_) + " point " + point);
        /*for (int i = 0; i < ends.length; i++) {
			System.out.println(ends[i]);
		}*/
        
        Packet_original packet = new Packet_original();
        packet.sn = 0x01;
        packet.point = point;
        packet.bPositive = bPositive;
        packet.unit = 1;
        packet.bDelete = false;
        packet.data = ByteBuffer.allocate(3);
        packet.data.put(final_);
        
        System.out.println(Helper.getHex(packet.encode().array()));
        //order
        byte[] datad = {0x30, 0x30, 0x30};
        byte[] dataf = {(byte) 0xff, (byte) 0xff, (byte) 0xff};
        System.out.println(new String(datad));
        System.out.println(new String(dataf));
        String ddfString = new String(dataf);
        
        byte[] dfdf = ddfString.getBytes();
        System.out.println(getHex(dfdf, dfdf.length));
        
        System.out.println(getHex(datad, datad.length));
        System.out.println(getHex(dataf, dataf.length));
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
