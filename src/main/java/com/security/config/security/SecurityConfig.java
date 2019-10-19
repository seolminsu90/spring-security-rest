package com.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.config.security.handler.RestAuthenticationEntryPoint;
import com.security.config.security.handler.RestDeniedHandler;
import com.security.config.security.handler.RestLoginFailureHandler;
import com.security.config.security.handler.RestLoginSuccessHandler;
import com.security.config.security.handler.RestLogoutSuccessHandler;
import com.security.config.security.paramExtra.ExtraParamSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private RestDeniedHandler restDeniedHandler;

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private RestLoginSuccessHandler restLoginSuccessHandler;

	@Autowired
	private RestLogoutSuccessHandler restLogoutSuccessHandler;

	@Autowired
	private RestLoginFailureHandler restLoginFailureHandler;

	@Autowired
	private ExtraParamSource extraParamSource;
	
	@Autowired
	private AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("*.jsp", "/WEB-INF/views/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint)
				.accessDeniedHandler(restDeniedHandler)
			.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll()
				.antMatchers("/**")
				.permitAll()
			.and()
			.formLogin()
				.successHandler(restLoginSuccessHandler)
				.failureHandler(restLoginFailureHandler)
				.authenticationDetailsSource(extraParamSource)
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessHandler(restLogoutSuccessHandler)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
			.and()
				.addFilterBefore(authenticationTokenProcessingFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
