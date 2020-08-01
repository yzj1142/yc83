package com.yc.thread0729;

import java.io.IOException;

public interface Servlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException;
}
