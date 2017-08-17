package com.wandermonk.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class CopyToServer {
	ServerSocketChannel ssc = null;
	
	public void bootstrap() {
		InetSocketAddress isa = new InetSocketAddress(8082);
		try {
			ssc = ServerSocketChannel.open();
			ServerSocket ss = ssc.socket();
			ss.setReuseAddress(true);
			ss.bind(isa);
			System.out.println("Listening to the port "+ isa.getPort());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readData() {
		ByteBuffer bb = ByteBuffer.allocate(1024);
		try {
			while(true) {
				SocketChannel sc = ssc.accept();
				System.out.println("connection established "+sc);
				sc.configureBlocking(true);
				int n = 0;
				while(n != -1) {
					try {
						n = sc.read(bb);
					}catch(Exception e) {
						e.printStackTrace();
						n = -1;
					}
					bb.rewind();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CopyToServer cts = new CopyToServer();
		cts.bootstrap();
		cts.readData();
	}
}
