package auth.token;

import org.springframework.http.HttpHeaders;

public class HttpHeaderToken {

    // Spring Http Header头部的设置规则并添加认证的token
    // .httpBasic()对应默认的Basic <credentials>
    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic <credentials>");

        headers.setBearerAuth(token);
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }
}
