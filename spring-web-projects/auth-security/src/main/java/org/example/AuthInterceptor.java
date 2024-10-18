package org.example;

import org.example.model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 后台根据用户的不同权限来设置访问的不同资源
// 不同的用户有不同的关联的资源
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        if (requestUrl.startsWith("/common-pages")) {
            return true;
        }
        if (request.getSession().getAttribute("FLAG-SESSION") == null) {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Please login");
            return false;
        } else {
            User user = (User) request.getSession().getAttribute("FLAG-SESSION");
            if (user.hasPermission("resource") && requestUrl.startsWith("/resource")) {
                return true;
            } else {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("No authorization to access");
                return false;
            }
        }
    }
}
