package auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;

// 预验证身份认证器
// - 预验证是指身份已经在第三方确认过
// - 如果没有问题，则可根据JWT令牌的刷新过期期限，重新给客户端发放访问令牌
@Component
public class PreAuthenticationProvider implements AuthenticationProvider {

    // 检查用户没有停用、锁定、密码过期、账号过期等
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication.getPrincipal();
            AuthenticAccount user = (AuthenticAccount) authToken.getPrincipal();
            if (user.isEnabled()
                    && user.isCredentialsNonExpired()
                    && user.isAccountNonExpired()
                    && user.isCredentialsNonExpired()) {
                return new PreAuthenticatedAuthenticationToken(user, "", user.getAuthorities());
            } else {
                throw new DisabledException("User Status Failed");
            }
        } else {
            throw new PreAuthenticatedCredentialsNotFoundException("Pre authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
