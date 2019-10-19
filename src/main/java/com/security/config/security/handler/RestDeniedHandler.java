package com.security.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.security.config.security.util.ResponseWriter;

@Component
public class RestDeniedHandler implements AccessDeniedHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ade)
			throws IOException, ServletException {
		logger.info("핸들러 - 유효하지만 권한이 없는 사용자");
		ResponseWriter.write(response, "유효하지만 권한이 없는 사용자", -1, null);
	}

}
