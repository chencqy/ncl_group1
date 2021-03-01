package uk.ac.ncl.rbac.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.rbac.common.entity.Permission;

import javax.annotation.Resource;
import java.util.List;

/**
 * Test class for PermissionMapper
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionMapperTest {
    @Resource
    private PermissionMapper permissionMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Permission> permissionList = permissionMapper.selectList(null);
        permissionList.forEach(System.out::println);
    }
}
