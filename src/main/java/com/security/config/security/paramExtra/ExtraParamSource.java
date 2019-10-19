package com.security.config.security.paramExtra;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class ExtraParamSource implements AuthenticationDetailsSource<HttpServletRequest, ExtraParam> {

	public ExtraParam buildDetails (HttpServletRequest context) {
	
	return new ExtraParam(context);
	}
}