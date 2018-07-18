package test.main.iotprotocoltest;

import java.nio.ByteBuffer;

public class HSTVCPacket {
	final static public int OFFSET_MAGIC = 0;
	final static public int OFFSET_SIZE = 1;
	final static public int OFFSET_DATA = 2;
	final static public byte head = (byte) 0xff;
	final static public int data_out_size = 3;

	final public static byte TYPE_SWITCH = 0x01;
	final public static byte TYPE_READING = 0x02;
	final public static byte TYPE_NETCOM = 0x03;
	final public static byte TYPE_12V = 0x04;
	final public static byte TYPE_24V = 0x05;
	final public static byte TYPE_DOOR = 0x11;
	final public static byte TYPE_POWER = 0x12;
	final public static byte TYPE_FAN = 0x13;

	public byte type;
	public byte length = 0;
	public byte cs;
	public boolean bResponse = false;
	public boolean bStatusJXF = false;
	public boolean bSensor = false;
	public boolean bNet = false;
	public boolean b12V = false;
	public boolean b24V = false;
	public boolean b_door = false;
	public boolean b_power = false;
	public boolean b_fan = false;
	
	public byte status_JXF = 0x00;
	public byte[] sensors = null;
	public byte net = 0x00;
	public byte v12_status = 0x00;
	public byte v24_status = 0x00;
	public byte door = (byte) 0xff;
	public byte power = (byte) 0xff;
	public byte temperature = 0x00;
	
	public byte[] temperature() {
		byte[] temp = new byte[2];
		int ti = (int) ((temperature*100)%10000);
		temp[0] = (byte) ((((ti/1000) & 0x0f) << 4) | ((((ti/100)%10) & 0x0f)));
		temp[1] = (byte) (((((ti/10)%10) & 0x0f) << 4) | ((ti%10)));
		return temp;
	}
	
	public static byte cs(byte[] raw, int left, int length) {
 		if (left == length || raw.length < length) {
			return 0;
		}
		return (byte) (raw[left] + cs(raw, ++left, length));
	}
	
	public byte[] encode() {
		ByteBuffer buffer;
		length = 2;
		buffer = ByteBuffer.allocate(length + data_out_size);
		buffer.put(head);
		buffer.put(length);
		buffer.put(type);
			
		if (type == TYPE_DOOR) {
			buffer.put(door);
			
		} else if (type == TYPE_POWER) {
			buffer.put(power);
			
		} else if (type == TYPE_FAN) {
			buffer.put(temperature);
		}
		
		buffer.put(cs(buffer.array(), 0, buffer.position()));
		return buffer.array();
	}
	
	public byte[] encodeRecv() {
		ByteBuffer buffer = ByteBuffer.allocate(2 + data_out_size);
		buffer.put(head);
		buffer.put((byte)0x02);
		buffer.put((byte)(((byte)0x80) | type));
		buffer.put((byte)0x00);
		buffer.put(cs(buffer.array(), 0, buffer.position()));
		return buffer.array();
	}
	
	/**
	 * decode a ByteBuffer return complete HSTVC packet bytes
	 * this bytes checksum is really
	 * @param raw 
	 * @param skip start position
	 * @return complete HSTVC packet byte[]
	 */
	public byte[] decodeWithByte(ByteBuffer raw, int skip) {
		int left = raw.position();
		int end = skip;
		int remaining = left - end;
		byte[] braw = raw.array();
		
		if (left - skip >= 5) {
			if (braw[skip] == head) {
				
				length = braw[skip + 1];
				end = skip + length + data_out_size;
				remaining = left - end;
				
				if (remaining >= 0) {
					byte cs = braw[end - 1];
					byte ccs = cs(braw, skip, end - 1);
					
					if (cs == ccs) {
						byte[] data = new byte[length + data_out_size];
						bResponse = (data[OFFSET_DATA] & 0x80) != 0;
						if (!bResponse) {
							System.arraycopy(braw, OFFSET_DATA + skip, data, 0, length + data_out_size);	
						}
						
						System.arraycopy(braw, end, raw.array(), 0, remaining);
						
						raw.position(remaining);
						
						return data;
					} else {
						System.out.println("cs error!");
						return decodeWithByte(raw, skip + 1);
					}
				}
			} else {
				System.out.println("head error");
				return decodeWithByte(raw, skip + 1);
			}
		}
		
		if (skip > 0) {
			System.arraycopy(braw, skip, raw.array(), 0, remaining);
			raw.position(remaining);
		}
		return null;
	}
	
	/**
	 * decode a HSTVC packet raw length is complete
	 * @param raw
	 * @return if can decode a packet
	 */
	public boolean decode(ByteBuffer raw) {
		int left = raw.position();
		byte[] braw = raw.array();
		
		if (left >= 5) {
			if (braw[0] == head) {
				length = braw[OFFSET_SIZE];
				
				if (length + data_out_size == left) {
					byte cs = braw[left - 1];
					byte ccs = cs(braw, 0, left - 1);
					
					if (cs == ccs) {
						byte[] data = new byte[length];
						System.arraycopy(braw, OFFSET_DATA, data, 0, length);
						return deserializer(data, 0);
					}
				}
			}
		}
		
		return false;
	}
	
	public int decode(ByteBuffer raw, int skip) {
		int left = raw.position();
		int end = skip;
		int remaining = left - end;
		byte[] braw = raw.array();
		
		if (left - skip >= 5) {
			if (braw[skip] == head) {
				
				length = braw[skip + 1];
				end = skip + length + data_out_size;
				remaining = left - end;
				
				if (remaining >= 0) {
					byte cs = braw[end - 1];
					byte ccs = cs(braw, skip, end - 1);
					
					if (cs == ccs) {
						byte[] data = new byte[length];
						System.arraycopy(braw, OFFSET_DATA + skip, data, 0, length);
						bResponse = (data[OFFSET_DATA] & 0x80) != 0;
						
						if (!bResponse) {
							//System.out.println("is response");
							deserializer(data, 0);
						}
						
						System.arraycopy(braw, end, raw.array(), 0, remaining);
						
						raw.position(remaining);
						
						return 0;
					} else {
						System.out.println("cs error!");
						return decode(raw, skip + 1);
					}
				}
			} else {
				System.out.println("head error");
				return decode(raw, skip + 1);
			}
		}
		
		if (skip > 0) {
			System.arraycopy(braw, skip, raw.array(), 0, remaining);
			raw.position(remaining);
		}
		return -1;
	}
	
	private boolean deserializer(byte[] data, int start) {
		if (data.length <= start) {
			return false;
		}
		
		int restart = 0;
		
		if (data[start] == TYPE_SWITCH) {
			type = TYPE_SWITCH;
			bStatusJXF = true;
			status_JXF = data[start + 1];
			restart = start + 2;
			
		} else if (data[start] == TYPE_READING) {
			type = TYPE_READING;
			bSensor = true;
			sensors = new byte[5];
			System.arraycopy(data, start + 1, sensors, 0, 5);
			restart = start + 6;
			
		} else if (data[start] == TYPE_NETCOM) {
			type = TYPE_NETCOM;
			bNet = true;
			net = data[start + 1];
			restart = start + 2;
			
		} else if (data[start] == TYPE_12V) {
			type = TYPE_12V;
			b12V = true;
			v12_status = data[start + 1];
			restart = start + 2;
			
		} else if (data[start] == TYPE_24V) {
			type = TYPE_24V;
			b24V = true;
			v24_status = data[start + 1];
			restart = start + 2;
		}
		
		if (restart != 0) {
			 deserializer(data, restart);
		} else {
			System.out.println("data must error !!! " + data[start]);
			return false;
		}
		
		return true;
	}
}
