package com.lucy.board.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.lucy.board.config.domain.ErrorCode;
import com.lucy.board.config.exception.CustomException;

public class JwtUtil {
    private final static String KEY = "BOARD";
    private final static long EXPIREDTIME = 24 * 60 * 60 * 1000;
    public static String createToken(long seq) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            return JWT.create()
                    .withIssuer("lucy")
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIREDTIME))
                    .withClaim("seq", seq)
                    .sign(algorithm);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.TOKEN_ERROR);
        }
    }

    public static Long verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                                            .withIssuer("lucy")
                                            .acceptExpiresAt(EXPIREDTIME)
                                            .build();
            return verifier.verify(token).getClaim("seq").asLong();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.TOKEN_ERROR);
        }
    }
}