package com.bridgelabz.fundooApp.utility;


import java.time.LocalTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JWTTokenGenerator implements ITokenGenerator {

    String secret_token="fundoo";
	@Override
	public String generateToken(String id) {
		String token = Jwts.builder()
				  .setSubject("fundooNotes")
				  .setId(id)
				  .signWith(SignatureAlgorithm.HS256, secret_token)
				  .compact();
		return token;
	}

	@Override
	public String verifyToken(String token) {
		Jws<Claims> claims = Jwts.parser()
		  .setSigningKey(secret_token)
		  .parseClaimsJws(token);
		String userId = claims.getBody().getId();
		return userId;
	}
	
	
	
	

	
}
