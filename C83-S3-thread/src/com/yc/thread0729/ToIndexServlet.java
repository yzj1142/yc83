package com.yc.thread0729;

import java.io.IOException;

public class ToIndexServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		response.sendRedirect("/photo/index.html");
	}

}
