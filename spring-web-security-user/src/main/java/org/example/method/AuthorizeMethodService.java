package org.example.method;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO. 使用注解配置"方法级"的认证和权限
// prePostEnabled = true：启用Pre/Post风格的注解支持:
// @PreAuthorize("...") 方法执行前基于表达式SpEL做授权判断
// @PostAuthorize("...") 方法执行后基于返回结果做授权判断
// @PreFilter("...") 方法执行前对集合类型的入参做过滤
// @PostFilter("...") 方法执行后对集合类型的返回值做过滤
@Service
public class AuthorizeMethodService {

    // 方法执行前校验：当前用户必须有ROLE_ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOrder(Long id) {
        // …
    }

    // 方法执行前校验：只能操作自己的订单
    @PreAuthorize("#userId == authentication.name or hasAuthority('order:write')")
    public void updateOrder(String userId) {
        // …
    }

    // 方法执行后校验：只有当返回的订单属于当前用户时才允许返回
    @PostAuthorize("returnObject != null && returnObject.owner == authentication.name")
    public String findById(Long id) {
        // …
        return "order";
    }

    // 入参集合过滤：仅保留用户有写权限的条目
    @PreFilter(filterTarget = "orders", value = "hasPermission(filterObject, 'write')")
    public void batchUpdate(List<String> orders) {
        // …
    }

    // 返回集合过滤：仅返回当前用户可读的数据
    @PostFilter("hasPermission(filterObject, 'read')")
    public List<String> listAll() {
        // …
        return List.of("items");
    }
}
