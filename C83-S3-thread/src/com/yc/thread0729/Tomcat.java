package com.yc.thread0729;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Tomcat {
	private HashMap<String, Servlet> servletMap;
	public void startup() throws IOException {
		//服务器初始化Servlet容器==》map集合==>URL:Servlet对象
		
		servletMap=new HashMap<>();
		servletMap.put("/photo/hello", new HelloServlet());
		servletMap.put("/", new ToIndexServlet());
		servletMap.put("/index", new ToIndexServlet());
		servletMap.put("/toindex", new ToIndexServlet());
		servletMap.put("/cookie", new CookieServlet());
		servletMap.put("/login.jsp", new LoginPageServlet());
		servletMap.put("/photo/post.do", new LoginPageServlet());
		
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
						//打印请求报文
						String requestText= new String(buffer,0,count);
						System.out.println(requestText);
					
						//解析请求报文
						HttpServletRequest request=buildRequest(requestText);
						HttpServletResponse response=new HttpServletResponse(out);
						//使用资源地址区分动态请求和静态请求
						//使用资源地址到Servlet容器中获取servlet对象
						String uri=request.getRequestURI();
						Servlet servlet=servletMap.get(uri);
						if(servlet!=null) {
							//使用Servlet处理动态请求
							servlet.service(request, response);
						}else {
							//如果没有找到对应的Servlet对象，那么将其视为静态请求
							processStaticReuqest(request,out);
						}					
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

	public void shutdown() {
		
	}
	//解析请求对象
	protected HttpServletRequest buildRequest(String requestText) throws UnsupportedEncodingException {
		return new HttpServletRequest(requestText);
	}
public static void main(String []args) throws IOException {
	new Tomcat().startup();
}
protected void processStaticReuqest(HttpServletRequest request, OutputStream out) throws IOException {
	String webpath=request.getRequestURI();
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
	out.write(("Content-Type: "+contentType+"\n").getBytes());
	out.write("\n".getBytes());

	FileInputStream fis=new FileInputStream(path);
	byte[]buffer=new byte[1024];
	int count;
	while((count=fis.read(buffer))>0) {
		out.write(buffer,0,count);
	//处理静态请求
	//判断资源是否存在，如果不存在返回404
		
}
	fis.close();
	
}

}
