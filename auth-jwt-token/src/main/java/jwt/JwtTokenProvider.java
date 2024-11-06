package jwt;

import io.jsonwebtoken.Jwts;
import key.SignedKey;

import java.util.Date;

// JWT生成代码: 签名算法 + Payload信息设置 + SecretKey
public class JwtTokenProvider {

    private final String issuer = "TOKEN Provider";
    private final int expirationMs = 300000;

    // TODO. 基于用户的账号进行签名
    public String generateJwtToken(String username) {
        long issueTime = (new Date()).getTime();
        Date expirationTime = new Date(issueTime + expirationMs);
        return Jwts.builder()
                .subject(username) // Payload 用户名信息
                .issuer(issuer)    // Payload 由谁签发
                .issuedAt(new Date(issueTime)) // Payload 签发时间
                .expiration(expirationTime) // Payload 过期时间
                .signWith(SignedKey.getSigningKey()) // Signature 使用key签名
                .compact();
    }

    // TODO. 从签名后的Token中获取用户信息(必须使用相同SecretKey)
    // Sets the signature verification SecretKey used to verify all encountered JWS signatures.
    public String getUserNameFromJwtToken(String jwtToken) {
        return Jwts.parser()
                .verifyWith(SignedKey.getSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload()
                .getSubject(); // TODO. 从Payload Claims中获取签名信息
    }
}
