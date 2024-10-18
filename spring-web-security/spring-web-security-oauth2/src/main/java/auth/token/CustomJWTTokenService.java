package auth.token;

import auth.service.OAuthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

@Component
public class CustomJWTTokenService extends DefaultTokenServices {

    @Autowired
    public CustomJWTTokenService(CustomJWTAccessTokenConverter token,
                                 OAuthClientDetailsService clientService,
                                 AuthenticationManager authenticationManager) {
        // 设置令牌持久化容器
        setTokenStore(new JwtTokenStore(token));
        setClientDetailsService(clientService);
        setAuthenticationManager(authenticationManager);
        setTokenEnhancer(token);

        // access_token有效期，单位：秒，默认12小时
        setAccessTokenValiditySeconds(60 * 60 * 3);

        // refresh_token的有效期，单位：秒, 默认30天
        // 这决定了客户端选择“记住当前登录用户”的最长时效，即失效前都不用再请求用户赋权了
        setRefreshTokenValiditySeconds(60 * 60 * 24 * 15);

        // 是否支持refresh_token，默认false
        setSupportRefreshToken(true);

        // 是否复用refresh_token，默认为true
        // 如果为false，则每次请求刷新都会删除旧的refresh_token，创建新的refresh_token
        setReuseRefreshToken(true);
    }
}
