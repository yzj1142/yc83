package com.yc.thread0725;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class FileTransferServer {

	public static void main(String[] args) throws IOException {
		ServerSocket server=new ServerSocket(8888);
		System.out.println("服务器启动成功，监听8888接口");
		//接收客户端的连接
		//IO操作，导致程序再次阻塞，客户端连接成功后结束阻塞，同时返回socket（套接字对象）
		Socket socket=server.accept();
		//我的地址
		InetAddress myAddress=socket.getInetAddress();
		SocketAddress otherAddress=socket.getRemoteSocketAddress();
		System.out.println("我的地址"+ myAddress);
		System.out.println("客户端的地址"+ otherAddress);
		
		DataInputStream dis=new DataInputStream(socket.getInputStream());
		File directory=new File("D:/a");
		if(!directory.exists()) {
			directory.mkdir();
		}
		File file=new File(directory.getAbsolutePath()+File.separatorChar+dis.readUTF());
		FileOutputStream fos=new FileOutputStream(file);
		System.out.println("file.........."+file);
		System.out.println("fileName.........."+dis.readUTF());
		System.out.println("============开始接收文件===========");
		
		byte []bytes=new byte[1024];
		int length=0;
		while((length=dis.read(bytes,0,bytes.length))!=-1) {
			fos.write(bytes,0,length);
			fos.flush();
		}
		System.out.println("===========文件接收成功=========");
		
		try {
			if(fos!=null)
				fos.close();
			if(dis!=null)
			dis.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
		

}
