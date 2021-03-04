package uk.ac.ncl.rbac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ncl.rbac.common.entity.User;
import uk.ac.ncl.rbac.common.entity.vo.UserVo;
import uk.ac.ncl.rbac.common.results.JsonResult;
import uk.ac.ncl.rbac.common.results.Result;
import uk.ac.ncl.rbac.common.results.ResultCode;
import uk.ac.ncl.rbac.service.UserService;

import javax.annotation.Resource;


/**
 * User controller
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/getUserByAccount")
    public String getUserByAccount(@RequestParam(value = "account") String account) {
        User user = userService.getUserByAccount(account);
        return String.format("Hello %s!", user.getUserName());
    }

    @GetMapping("/listUserPage")
    public JsonResult listUserPage(@RequestParam("pageNum")int pageNum, @RequestParam("pageSize")int pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> userIPage = userService.listUserPage(page);
        return userIPage == null ? Result.fail() : Result.success(userIPage);
    }

    @GetMapping("/getUserInfoByAccount")
    public JsonResult getUserInfoByAccount(@RequestParam(value = "account") String account) {
        UserVo userVo = userService.getUserInfo(account);
        return userVo == null ? Result.fail(ResultCode.USER_NOT_EXIST) : Result.success(userVo);
    }
}
