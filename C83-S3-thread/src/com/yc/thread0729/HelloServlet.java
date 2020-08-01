package com.yc.thread0729;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HelloServlet extends HttpServlet {
public void doGet(HttpServletRequest request,HttpServletResponse response) {
	String name=request.getParameter("name");
	PrintWriter out=response.getWriter();
	//输出页面
	try {
		name=URLDecoder.decode(name, "utf-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	out.print("<h1>hello,"+name+"</h1>");
	System.out.println("hello,world");
}
}
