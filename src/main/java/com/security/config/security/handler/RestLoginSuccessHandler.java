package com.security.config.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.security.api.domain.UserDetailsDTO;
import com.security.config.security.util.ResponseWriter;
import com.security.util.JWTUtil;

@Component
public class RestLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private JWTUtil jwtUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("핸들러 - 로그인");

		String ip_address = request.getRemoteAddr();

		UserDetailsDTO info = (UserDetailsDTO) authentication.getPrincipal();
		String authenticationToken = jwtUtil.createToken(info, ip_address);
		logger.debug("AuthenticationToken : " + authenticationToken);
		
		ResponseWriter.write(response, "로그인", -1, authenticationToken);
	}
}
