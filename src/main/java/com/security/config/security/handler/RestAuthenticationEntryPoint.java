package com.security.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.security.config.security.util.ResponseWriter;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.info("핸들러 - 유효하지 않은 사용자의 접근");
		ResponseWriter.write(response, "유효하지 않은 사용자의 접근", -1, null);
	}
}
