package uk.ac.ncl.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.rbac.common.entity.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * Test class for UserMapper
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testListUserPage() {
        System.out.println(("----- listUserPage method test ------"));
        Page<User> page = new Page<>(1, 10);
        IPage<User> users = userMapper.listUserPage(page);
        users.getRecords().forEach(System.out::println);
    }
}
