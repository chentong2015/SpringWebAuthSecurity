package auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OAuthClientDetailsService implements ClientDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private ClientDetailsService clientDetailsService;

    private static final String CLIENT_ID = "bookstore_frontend";
    // 客户端密钥, 密钥应当保密，用于证明当前申请授权的客户端是否被冒充
    private static final String CLIENT_SECRET = "bookstore_secret";

    // 提供客户端ID和密钥，并指定该客户端支持密码授权、刷新令牌两种访问类型
    // 授权Endpoint示例：
    // /oauth/token?grant_type=password & username=#USER# & password=#PWD# & client_id=bookstore_frontend & client_secret=bookstore_secret
    // 刷新令牌Endpoint示例：
    // /oauth/token?grant_type=refresh_token & refresh_token=#REFRESH_TOKEN# & client_id=bookstore_frontend & client_secret=bookstore_secret
    @PostConstruct
    public void init() throws Exception {
        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        builder.withClient(CLIENT_ID)
                .secret(passwordEncoder.encode(CLIENT_SECRET))
                .scopes("BROWSER")
                .authorizedGrantTypes("password", "refresh_token");
        clientDetailsService = builder.build();
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientDetailsService.loadClientByClientId(clientId);
    }
}
