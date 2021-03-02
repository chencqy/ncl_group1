package uk.ac.ncl.rbac.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.rbac.common.entity.Role;

import javax.annotation.Resource;
import java.util.List;

/**
 * Test class for RoleService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest {
    @Resource
    private RoleService roleService;

    @Test
    public void testListRolesByUserId() {
        System.out.println(("----- listRolesByUserId method test ------"));
        int userId = 1;
        List<Role> roles = roleService.listRolesByUserId(userId);
        roles.forEach(System.out::println);
    }
}
