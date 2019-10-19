package com.security.config.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("핸들러 - 로그아웃");

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		String data = "{'msg' : '로그아웃'}";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
		out.close();
	}
}
