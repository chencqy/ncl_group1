package uk.ac.ncl.rbac.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.rbac.common.entity.Role;

import javax.annotation.Resource;
import java.util.List;

/**
 * Test class for RoleMapper
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest {
    @Resource
    private RoleMapper roleMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Role> roles = roleMapper.selectList(null);
        roles.forEach(System.out::println);
    }
}
