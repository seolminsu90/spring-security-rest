package com.security.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.security.config.security.util.ResponseWriter;

@Component
public class RestLoginFailureHandler implements AuthenticationFailureHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("핸들러 - 로그인실패");
		ResponseWriter.write(response, "로그인실패", -1, null);
	}
}
