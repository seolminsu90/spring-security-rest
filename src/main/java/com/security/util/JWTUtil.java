package com.security.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.security.api.domain.UserDetailsDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	private final String JWT_SIGN_KEY = "SIGN_KEY_HERE";

	public String createToken(UserDetailsDTO user, String ip_address) {
		Map<String, Object> claims = new HashMap<String, Object>();

		List<String> authList = new ArrayList<String>();

		user.getAuthorities().parallelStream().forEach(auth -> {
			authList.add(auth.getAuthority());
		});

		claims.put("id", user.getId());
		claims.put("authList", authList);

		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MINUTE, 3600);
		dt = c.getTime();

		String accessToken = Jwts.builder()
				.setHeaderParam("type", "app-access-key")
				.setHeaderParam("issueDate", System.currentTimeMillis())
				.setHeaderParam("ip", ip_address)
				.setClaims(claims).setExpiration(dt)
				.signWith(SignatureAlgorithm.HS512, "SIGN_KEY_HERE").compact();
		return accessToken;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> checkToken(String token, String ip_address) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("return", 0);
		try {
			Jws<Claims> jwt = signAndParseJWT(token);
			JwsHeader<?> token_header = jwt.getHeader();
			Claims token_body = jwt.getBody();

			List<String> auth_list = (List<String>) token_body.get("authList");
			String id = (String) token_body.get("id");
			String issueDate = (String) token_header.get("issueDate");

			//return 은 적당히 Handling
			if (id == null || auth_list == null) {
				map.put("return", -1);
				return map;
			} else {
				map.put("return", 0);
				map.put("id", id);
				map.put("authList", auth_list);
				map.put("issueDate", issueDate);
				return map;
			}
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			map.put("return", -2);
			return map;
		} catch (io.jsonwebtoken.SignatureException e) {
			map.put("return", -3);
			return map;
		} catch (io.jsonwebtoken.MalformedJwtException e) {
			map.put("return", -4);
			return map;
		} catch (Exception e) {
			map.put("return", -5);
			return map;
		}
	}

	public Jws<Claims> signAndParseJWT(String token) {
		return Jwts.parser().setSigningKey(JWT_SIGN_KEY).parseClaimsJws(token);
	}
}
