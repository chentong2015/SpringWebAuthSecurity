package org.example.role_permission;

import org.example.object_permission.FilterObject;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MethodPermissionService {

    // 当前用户必须有ROLE_ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOrder(Long id) {
        // …
    }

    // 只能操作自己的订单
    @PreAuthorize("#username == authentication.name or hasAuthority('WRITE')")
    public void updateOrder(String username) {
        // …
    }

    // 只有当返回的订单属于当前用户时才允许返回
    @PostAuthorize("returnObject != null && returnObject.owner == authentication.name")
    public FilterObject findById(Long id) {
        // …
        return new FilterObject(101, "user1", true);
    }
}