package uk.ac.ncl.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.javassist.convert.TransformNewClass;
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
        String account = "user2";
        UserVo userVo = userService.getUserInfo(account);
        System.out.println(userVo);
        Assert.assertEquals(account, userVo.getAccount());
    }

    @Test
    public void testInsertUser() {
        System.out.println(("----- InsertUser method test ------"));
        User user = new User();
        String account, userName, userEmail, phoneNumber, password;
        account = "testUser";
        userName = "test";
        userEmail = "test@ncl.ac.uk";
        phoneNumber = "44[0]1911911234";
        password = "123456";
        user.setAccount(account);
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        String roleOfUser = "Member of Public";
        userService.insertUser(user, roleOfUser);

    }
}
