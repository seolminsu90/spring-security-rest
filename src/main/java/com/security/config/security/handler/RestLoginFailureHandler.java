package com.security.config.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class RestLoginFailureHandler implements AuthenticationFailureHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("핸들러 - 로그인실패");

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		String msg = "{'msg' : '로그인실패'}";
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
	}
}
