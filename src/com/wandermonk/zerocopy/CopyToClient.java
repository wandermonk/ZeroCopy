package com.wandermonk.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class CopyToClient {

	public void sendData() throws IOException {
		String host = "localhost";
		int port = 8082;
		SocketAddress sad = new InetSocketAddress(host, port);
		SocketChannel sc = SocketChannel.open();
		sc.connect(sad);
		sc.configureBlocking(true);

		String filename = "sendfile/NetworkInterfaces.c";
		long filesize = 183678375L;
		@SuppressWarnings("resource")
		FileChannel fc = new FileInputStream(filename).getChannel();
		long start = System.currentTimeMillis();
		long curnset = 0;
		curnset = fc.transferTo(0, filesize, sc);
		System.out.println("total bytes transferred--" + curnset + " and time taken in MS--"
				+ (System.currentTimeMillis() - start));
	}

	public static void main(String[] args) throws IOException {
		CopyToClient ctc = new CopyToClient();
		ctc.sendData();
	}
}
