package org.example.object_permission;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// TODO. hasPermission() 判断"当前用户对某个具体对象是否具备某种操作权限"
// @PreFilter("...")     方法执行前对集合类型的入参做过滤
// @PostFilter("...")    方法执行后对集合类型的返回值做过滤
@RestController
public class HasPermissionController {

    private final HasPermissionService hasPermissionService;

    public HasPermissionController(HasPermissionService hasPermissionService) {
        this.hasPermissionService = hasPermissionService;
    }

    @GetMapping("/filter/update")
    public String update() {
        List<FilterObject> filterObjects = new ArrayList<>();
        filterObjects.add(new FilterObject(10, "user1", false));
        filterObjects.add(new FilterObject(10, "test", true));
        filterObjects.add(new FilterObject(10, "admin1", true));
        hasPermissionService.batchUpdate(filterObjects);
        return "Update filter objects";
    }
}
