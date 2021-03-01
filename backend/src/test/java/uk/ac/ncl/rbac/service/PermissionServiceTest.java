package uk.ac.ncl.rbac.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.rbac.common.entity.Permission;

import javax.annotation.Resource;
import java.util.List;

/**
 * Test class for PermissionService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionServiceTest {
    @Resource
    private PermissionService permissionService;

    @Test
    public void testListPermissionsByUserId() {
        System.out.println(("----- listPermissionsByUserId method test ------"));
        int userId = 1;
        List<Permission> permissions = permissionService.listPermissionsByUserId(userId);
        permissions.forEach(System.out::println);
    }
}
