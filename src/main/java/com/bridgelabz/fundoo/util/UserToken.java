package com.bridgelabz.fundoo.util;

import java.io.UnsupportedEncodingException;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

@Component
@PropertySource("classpath:message.properties")
public class UserToken {
	
	private static String TOKEN;
	
	public  String generateToken(long id) {
		TOKEN="Tasif";
		Algorithm algorithm = null;
		
			try {
				algorithm = Algorithm.HMAC256(TOKEN);
			} catch (IllegalArgumentException | UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
		
		String token=JWT.create().withClaim("ID", id).sign(algorithm);
		return token;		
	}
	
	public  long tokenVerify(String token){
		TOKEN="Tasif";

		long userid;
		//here verify the given token's algorithm
		Verification verification = null;
		
		try {
			verification = JWT.require(Algorithm.HMAC256(UserToken.TOKEN));
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		JWTVerifier jwtverifier=verification.build();
		DecodedJWT decodedjwt=jwtverifier.verify(token);
		Claim claim=decodedjwt.getClaim("ID");
		userid=claim.asLong();	
		System.out.println(userid);
		return userid;
	}
}
