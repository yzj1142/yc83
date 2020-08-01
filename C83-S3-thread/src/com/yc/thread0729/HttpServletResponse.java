package com.yc.thread0729;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpServletResponse {

	private OutputStream out;
	private int status;
	private String msg;
	
	private Map<String,String>headerMap=new HashMap<>();
	private List<Cookie>cookieList=new ArrayList<>();
	
	public HttpServletResponse(OutputStream out) {
		this.out=out;
	}
	private CharArrayWriter caw=new CharArrayWriter();
	private PrintWriter pw=new PrintWriter(caw);
	
	public PrintWriter getWriter() {
		return pw;
	}
	public void setStatus(int status,String msg) {
		if(this.status==0) {
		this.status=status;
		this.msg=msg;
		}
	}
	public void flushBuffer() throws IOException {
		out.write(("HTTP/1.1 "+status+" "+msg+" \n").getBytes());
		out.write(("Content-Type: text/html;charset=utf-8\n").getBytes());
		for(Entry<String,String>entry:headerMap.entrySet()) {
			out.write((entry.getKey()+":"+entry.getValue()+"\n").getBytes());
		}
		for(Iterator<Cookie>iterator=cookieList.iterator();iterator.hasNext();) {
			Cookie cookie=iterator.next();
			out.write(cookie.toString().getBytes());
		}
		out.write("\n".getBytes());
		out.write(caw.toString().getBytes());
	}
	public void sendRedirect(String uri) {
		setStatus(301,"Redirect");
		headerMap.put("Location", uri);
	}
	public void addCookie(Cookie cookie) {
		cookieList.add(cookie);
		
	}
	/*
	 * 获取请求cookie数据
	 */
	public Cookie[]getCookies(){
		String cookieString=headerMap.get("Cookie");
		if(cookieString==null) {
			return null;
		}else {
			List<Cookie>cookieList=new ArrayList<>();
			String[]sCookies=cookieString.split(";\\s");
			for(int i=0;i<sCookies[i].length();i++) {
				String []nv=sCookies[i].split("=");
				cookieList.add(new Cookie(nv[0],nv[1]));
			}
			return cookieList.toArray(new Cookie[0]);
		}
	}
}
