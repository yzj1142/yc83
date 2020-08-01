package com.yc.thread0725;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class FileTransferClient {

	public static void main(String[] args) throws UnknownHostException, IOException{
		//只要new出socket就会立即和服务器进行连接
		//默认使用随机端口
		Socket socket=new Socket("127.0.0.1",8888);
		
		//我的地址
		InetAddress myAddress=socket.getInetAddress();
		SocketAddress otherAddress=socket.getRemoteSocketAddress();
		System.out.println("我的地址"+ myAddress);
		System.out.println("服务器的地址"+ otherAddress);
		
		File file=new File("D:/a.txt");
		FileInputStream fis=new FileInputStream(file);
		DataOutputStream  dos=new  DataOutputStream(socket.getOutputStream());//返回套接字的输出流
		dos.writeUTF(file.getName());//文件名
		dos.flush();
		dos.writeLong(file.length());
		dos.flush();
		System.out.println("=======开始传输文件========");
		try {
		byte []bytes=new byte[1024];
		int length=0;
		while((length=fis.read(bytes,0,bytes.length))!=-1) {
			dos.write(bytes,0,length);
			dos.flush();
		}
		System.out.println("========文件传输成功===========");
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("=========文件传输异常=========");
		}
		fis.close();
		dos.close();
	    
	}
}
