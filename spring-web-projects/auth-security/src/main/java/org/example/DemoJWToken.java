package org.example;

import io.jsonwebtoken.*;

import java.security.Key;

public class DemoJWToken {

    public static void main(String[] args) {
        Claims claims = validateJwt("jwtToken");

        String issuer = claims.getIssuer();
        String audience = String.join(" ", claims.getAudience());
    }

    private static Claims validateJwt(final String accessToken)  throws JwtException {
        Jws<Claims> claims = Jwts.parser()
                .requireAudience("")
                .requireIssuer("issuer")
                .setSigningKeyResolver(new SigningKeyResolver() {

                    @Override
                    public Key resolveSigningKey(JwsHeader header, Claims claims) {
                        return resolveSigningKey(header);
                    }

                    @Override
                    public Key resolveSigningKey(JwsHeader jwsHeader, byte[] bytes) {
                        return null;
                    }

                    private Key resolveSigningKey(JwsHeader header) {
                        return null;
                    }

                    private String getX5t(JwsHeader header) {
                        return header.get("x5t").toString();
                    }

                }).build().parseSignedClaims(accessToken);

        return claims.getPayload();
    }
}
