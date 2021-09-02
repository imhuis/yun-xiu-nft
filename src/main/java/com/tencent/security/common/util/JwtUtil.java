package com.tencent.security.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class JwtUtil {

    public static String create(String user, Date expire){
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer(user)
                    .withExpiresAt(expire)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            return null;
        }
    }

    public static boolean verify(String token, String user, String secret){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(user)
                    .build(); //Reusable verifier instance
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            return false;
        }
    }

    public static Optional<DecodedJWT> decode(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return Optional.of(jwt);
        } catch (JWTDecodeException exception){
            //Invalid token
            return null;
        }
    }

}
