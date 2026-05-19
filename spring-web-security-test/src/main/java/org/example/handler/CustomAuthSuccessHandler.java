package org.example.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// TODO. 定义用户认证成功后的处理器: 根据用户的权限重定向
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy;
    private final Map<String, String> roleTargetUrlMap;

    public CustomAuthSuccessHandler() {
        redirectStrategy = new DefaultRedirectStrategy();
        roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/homepage.html");
        roleTargetUrlMap.put("ROLE_ADMIN", "/console.html");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        clearAuthenticationAttributes(request);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String targetUrl = getTargetUrlBasedOnAuthority(authorities);
        if (response.isCommitted()) {
            // "Response has already been committed. Unable to redirect to " + targetUrl
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    // 根据UserDetails的权限来解析跳转链接
    protected String getTargetUrlBasedOnAuthority(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }
        throw new IllegalStateException();
    }
}
