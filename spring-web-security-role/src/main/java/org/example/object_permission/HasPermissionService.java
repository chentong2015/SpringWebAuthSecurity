package org.example.object_permission;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HasPermissionService {

    // 仅保留用户有写权限的条目(执行更新)
    @PreFilter(value = "hasPermission(filterObject, 'write')")
    public void batchUpdate(List<FilterObject> filterObjects) {
        System.out.println("Update: " + filterObjects.size());
        System.out.println("Update DONE");
    }

    // 仅返回当前用户可读的数据
    @PostFilter("hasPermission(filterObject, 'read')")
    public List<FilterObject> listAll() {
        // …
        return List.of(new FilterObject());
    }
}
