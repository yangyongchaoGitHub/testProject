package test.main;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
    private Socket socket = null;
    private InetSocketAddress isa;
    
	public static void main(String[] args) {
		
		TCPClient client = new TCPClient();
		try {
			System.out.println("2323");
			client.socket = new Socket();
			client.socket.setSoTimeout(0);
			client.socket.setKeepAlive(true);
			client.socket.setTcpNoDelay(true);
			//client.isa = new InetSocketAddress("120.78.74.142", 9972);
			client.isa = new InetSocketAddress("192.168.1.35", 4000);
			client.socket.connect(client.isa);	
			
			OutputStream os = client.socket.getOutputStream();
			InputStream is = client.socket.getInputStream();
			
			while(true) {
				//is.read(b, off, len)
				os.write(new byte[]{0x22,0x30, 0x31, 0x32, 0x33});
				System.out.println("write 0x22,0x30, 0x31, 0x32, 0x33");
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
