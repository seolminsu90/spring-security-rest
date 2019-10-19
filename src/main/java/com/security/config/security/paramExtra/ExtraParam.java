package com.security.config.security.paramExtra;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class ExtraParam extends WebAuthenticationDetails {

	private static final long serialVersionUID = 1L;
	private final String custom_parameter;
	
	public ExtraParam(HttpServletRequest request) {
	    super(request);
	    this.custom_parameter = request.getParameter("custom_parameter");
	}
	
	public String getCustom_parameter() {
	    return custom_parameter;
	}   
}
