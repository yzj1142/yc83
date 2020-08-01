package com.yc.thread0729;

public class CookieServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
	   Cookie cookie=new Cookie("name","wusong");
	   cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
		
		  cookie=new Cookie("sex","0");
		  cookie.setMaxAge(60*60*24);
		 response.addCookie(cookie);
		response.getWriter().append("cookie");
	}

}
