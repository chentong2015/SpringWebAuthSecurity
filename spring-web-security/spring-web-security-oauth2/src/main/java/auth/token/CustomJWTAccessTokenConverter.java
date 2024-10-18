package auth.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// JwtAccessTokenConverter提供令牌基础结构(令牌头、部分负载，如过期时间、JTI)的转换实现
@Component
public class CustomJWTAccessTokenConverter extends JwtAccessTokenConverter {

    // 签名私钥: 注意保密，不对外公开
    // JWT约定默认256 Bits，其实任何格式都可以
    private static final String JWT_TOKEN_SIGNING_PRIVATE_KEY = "601304E0-8AD4-40B0-BD51-0B432DC47461";

    // 设置从资源请求中带上来的JWT令牌转换回安全上下文中的用户信息的查询服务
    // 如果不设置该服务，则从JWT令牌获得的Principal就只有一个用户名(令牌中确实就只存了用户名)
    // 将用户用户信息查询服务提供给默认的令牌转换器，使得转换令牌时自动根据用户名还原出完整的用户对象
    // 方便后面编码(可以在直接获得登陆用户信息)，但也稳定地为每次请求增加了一次(从数据库/缓存)查询
    @Autowired
    CustomJWTAccessTokenConverter(UserDetailsService userDetailsService) {
        setSigningKey(JWT_TOKEN_SIGNING_PRIVATE_KEY);
        DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(userDetailsService);
        ((DefaultAccessTokenConverter) getAccessTokenConverter()).setUserTokenConverter(converter);
    }

    // 增强令牌：在令牌的负载中加入额外的信息
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Authentication user = authentication.getUserAuthentication();
        String[] authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        Map<String, Object> payLoad = new HashMap<>();
        payLoad.put("username", user.getName());
        payLoad.put("authorities", authorities);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(payLoad);
        return super.enhance(accessToken, authentication);
    }
}
