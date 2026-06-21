package org.example.filter_object;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;

// TODO. 自定义hasPermission()方法逻辑，基于特定Object判断权限
@Component
public class FilterObjectPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // 管理员直接放行
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true;
        }
        // 用户只能操作自己的订单
        // String username = authentication.getName();
        // return filterObject.getOwner().equals(username);

        FilterObject filterObject = (FilterObject) targetDomainObject;
        return permission.equals("write") && filterObject.isCanWrite();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}