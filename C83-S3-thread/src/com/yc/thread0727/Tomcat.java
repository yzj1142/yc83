package com.yc.thread0727;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomcat {

	public static void main(String[] args) throws IOException {
		ServerSocket tomcat=new ServerSocket(8080);
		System.out.println("tomcat 服务器启动成功，监听8080接口");
		boolean running=true;
		while(running) {
			Socket socket=tomcat.accept();
	  new Thread() {
		  public void run() {
			  try {
				  System.out.println("接收到请求");
					InputStream in=socket.getInputStream();
					OutputStream out=socket.getOutputStream();
					
					byte []buffer=new byte[1024];
					int count;
					count=in.read(buffer);
					if(count>0) {
						String requestText= new String(buffer,0,count);
						System.out.println(requestText);
						
						/*
						 * 解析出请求资源路径
						 */
						String []lines=requestText.split("\\n");
						String []requestLines=lines[0].split("\\s");
						String webpath=requestLines[1];
						String contentType;
						int statusCode=200;
						String path="D:/"+webpath;
						File file=new File(path);
						if(!file.exists()) {
							statusCode=404;
							path="D:/photo/404.html";
						}
						if(webpath.endsWith(".js")) {
							contentType="application/javascript;charset=utf-8";
						}else if(webpath.endsWith(".css")) {
							contentType="text/css;charset=utf-8";
						}else {
							contentType="text/html;charset=utf-8";
						}
						out.write(("HTTP/1.1 "+statusCode+" OK\n").getBytes());
						out.write(("Content-Type:"+contentType+"\n").getBytes());
						out.write("\n".getBytes());
					
						FileInputStream fis=new FileInputStream(path);
						while((count=fis.read(buffer))>0) {
							out.write(buffer,0,count);
						}
						/*
						 * 问题：
						 * 1.只能回复一次
						 * 2.回复的内容永远不变
						 * 1)解析出请求行中的资源名
						 * 2)读取文件内容输出到实体中
						 */
						fis.close();
					}
					socket.close();
			  }catch(IOException e) {
				  e.printStackTrace();
			  }
		  }
	  }.start();
		}	
	tomcat.close();
	}

}
