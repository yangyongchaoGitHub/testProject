package test.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPserver {

	public static void main(String[] args) {
		try {
			ServerSocket tcpServer = new ServerSocket(9972);
			while(true) {
				Socket clientSocket = tcpServer.accept();
				String ip = clientSocket.getInetAddress().getHostAddress();
				System.out.println(ip + "....connected....");
				InputStream is =  clientSocket.getInputStream();
				while(true) {
					System.out.print(is.read() + " ");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
