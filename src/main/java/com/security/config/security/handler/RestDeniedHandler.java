package com.security.config.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class RestDeniedHandler implements AccessDeniedHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ade)
			throws IOException, ServletException {
		logger.info("핸들러 - 엑세스 디나인");

		String accept = request.getHeader("accept");
		if (accept != null && accept.contains("html")) {
			RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
			redirectStrategy.sendRedirect(request, response, "/");
		} else {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");

			String data = "{'msg' : '권한없다'}";

			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
		}
	}

}
