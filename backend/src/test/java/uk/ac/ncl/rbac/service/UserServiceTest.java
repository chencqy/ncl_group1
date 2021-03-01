package uk.ac.ncl.rbac.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.rbac.common.entity.User;

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
}
