package uk.ac.ncl.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.rbac.common.entity.User;
import uk.ac.ncl.rbac.common.entity.vo.UserVo;

import javax.annotation.Resource;

/**
 * Test class for UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testGetUserByAccount() {
        System.out.println(("----- GetUserByAccount method test ------"));
        String account = "user1";
        User user = userService.getUserByAccount(account);
        System.out.println(user);
        Assert.assertEquals(account, user.getAccount());
        Assert.assertThrows(IllegalArgumentException.class, () -> userService.getUserByAccount(""));
    }

    @Test
    public void testListUserPage() {
        System.out.println(("----- listUserPage method test ------"));
        Page<User> page = new Page<>(1, 10);
        IPage<User> users = userService.listUserPage(page);
        users.getRecords().forEach(System.out::println);
    }

    @Test
    public void testGetUserInfo() {
        System.out.println(("----- GetUserInfo method test ------"));
        String account = "user1";
        UserVo userVo = userService.getUserInfo(account);
        System.out.println(userVo);
        Assert.assertEquals(account, userVo.getAccount());
    }
}
