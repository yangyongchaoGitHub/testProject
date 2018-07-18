package test.main.iotprotocoltest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Scanner;
import java.util.Set;

public class UDPClient {
	private static String ip = "127.0.0.1";
	//private static String ip = "114.115.143.70";
	private static int port = 9654;
	public static DatagramChannel sendChannel;
	private static Selector selector;
	public static SocketAddress target;
	public static SocketAddress changeTarget;
	
	public static void main(String[] args) {
		UDPClient client = new UDPClient();
		client.exec();
	}

	private void exec() {
		try {
			sendChannel = DatagramChannel.open();
			sendChannel.configureBlocking(false);
			selector = Selector.open();
			target = new InetSocketAddress(ip, port);
			sendChannel.connect(target);
			sendChannel.register(selector, SelectionKey.OP_READ);
		} catch (Exception e) {
			e.printStackTrace();
		}

		LoopThread loopThread = new LoopThread(sendChannel, selector, this);
		loopThread.start();

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String str = scanner.nextLine().trim();
			System.out.println("System.in " + str);
			System.out.println("1".equals(str));
			ByteBuffer buffer = null;
			
			
			
			if (buffer != null) {
				buffer.flip();
				loopThread.send(buffer, target);
			}
		}
	}

	private static String getHex(byte[] bytes) {
		String result = "";
		for (byte b : bytes) {
			String str = Integer.toHexString(b & 0xFF);
			if (str.length() == 1) {
				str = '0' + str;
			}
			result += str;
		}
		return result;
	}
	
	public static void changeClannel(SocketAddress target) {
		try {
			sendChannel.disconnect();
			sendChannel.connect(target);
			sendChannel.register(selector, SelectionKey.OP_READ);
			System.out.println("changeClannel to " + sendChannel.getRemoteAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class LoopThread extends Thread {
	DatagramChannel send;
	Selector selector;
	UDPClient client;
	SocketAddress localTarget;

	public LoopThread(DatagramChannel send, Selector selector, UDPClient client) {
		this.send = send;
		this.selector = selector;
		this.client = client;
	}

	@Override
	public void run() {
		ByteBuffer buffer = ByteBuffer.allocate(50);
		while (true) {
			int nKeys = 0;
			try {
				Thread.sleep(50);
				nKeys = selector.select();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (nKeys > 0) {
				Set<SelectionKey> keys = selector.selectedKeys();
				for (SelectionKey key : keys) {
					System.out.println(key.isConnectable() + " " + key.isWritable() + " " + key.isReadable() + " "
							+ key.isAcceptable());
					if (key.isConnectable()) {
						try {
							System.out.println("key.isConnectable()");
							send.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
						} catch (ClosedChannelException e) {
							e.printStackTrace();
						}
					}
					
					if (key.isReadable()) {
						System.out.println("key.isReadable()");
						
						DatagramChannel sc = (DatagramChannel) key.channel();

						try {
							while (sc.read(buffer) > 0) {
							}

							HSTVCPacket packet = new HSTVCPacket();
							if (packet.decode(buffer, 0) == 0) {
								if (!packet.bResponse) {
									send(ByteBuffer.wrap(packet.encodeRecv()),
										sc.getRemoteAddress());
								
								System.out.println("b_door: " + packet.b_door + 
										" b_fan: " + packet.b_fan + " b_power: " + packet.b_power);
								System.out.println("door value : " + packet.door + 
										" temp: " + packet.temperature + " power: " + packet.power);
								}
							}

							System.out.println("buffer length: "
									+ buffer.array().length
									+ " data: "
									+ new String(buffer.array(), 0, buffer
											.position()));
							send.register(selector, SelectionKey.OP_WRITE
									| SelectionKey.OP_READ);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void send(ByteBuffer buffer, SocketAddress target) {
		if (localTarget == null) {
			localTarget = target;
		}
		
		System.out.println(" check target " + localTarget.toString() + " " + target.toString());
		if (!localTarget.toString().equals(target.toString())) {
			client.changeClannel(target);
			localTarget = target;
		}
		
		try {
			send.send(buffer, target);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getCMD(String str, ByteBuffer buffer) {
		if ("10".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x00, 0x02});
			
		} else if ("11".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x01, 0x03});
		} else if ("12".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x02, 0x04});
		} else if ("13".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x03, 0x05});
		} else if ("14".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x04, 0x06});
		} else if ("15".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x05, 0x07});
		} else if ("16".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x06, 0x08});
		} else if ("17".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x07, 0x09});
		} else if ("18".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x08, 0x10});
		} else if ("19".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x09, 0x11});
		} else if ("110".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x10, 0x12});
		} else if ("111".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x11, 0x13});
		} else if ("112".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x12, 0x14});
		} else if ("113".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x13, 0x15});
		} else if ("114".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x14, 0x16});
		} else if ("115".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x15, 0x17});
		} else if ("116".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x16, 0x18});
		} else if ("117".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x17, 0x19});
		} else if ("118".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x18, 0x20});
		} else if ("119".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x19, 0x21});
		} else if ("120".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x20, 0x22});
		} else if ("121".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x21, 0x23});
		} else if ("122".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x22, 0x24});
		} else if ("123".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x23, 0x25});
		} else if ("124".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x24, 0x26});
		} else if ("125".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x25, 0x27});
		} else if ("126".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x26, 0x28});
		} else if ("127".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x27, 0x29});
		} else if ("128".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x28, 0x30});
		} else if ("129".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x29, 0x31});
		} else if ("130".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x30, 0x32});
		} else if ("131".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x01, 0x31, 0x33});
		} else if ("20".equals(str)) {
			buffer = ByteBuffer.allocate(9);
			buffer.put(new byte[]{(byte) 0xff, 0x06, 0x02, 0x15, 0x48, (byte) 0xdd, 0x05, 0x56, (byte) 0x98});
		} else if ("30".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x00, 0x04});
		} else if ("31".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x01, 0x05});
		} else if ("32".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x02, 0x06});
		} else if ("33".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x03, 0x07});
		} else if ("34".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x04, 0x08});
		} else if ("35".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x05, 0x09});
		} else if ("40".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x05, 0x09});
		} else if ("41".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x05, 0x09});
		} else if ("42".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x05, 0x09});
		} else if ("43".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x05, 0x09});
		} else if ("44".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x05, 0x09});
		} else if ("45".equals(str)) {
			buffer = ByteBuffer.allocate(5);
			buffer.put(new byte[]{(byte) 0xff, 0x02, 0x03, 0x05, 0x09});
		}
	}
}
