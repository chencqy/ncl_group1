package uk.ac.ncl.rbac.common.entity.vo;

import uk.ac.ncl.rbac.common.entity.Permission;
import uk.ac.ncl.rbac.common.entity.Role;
import uk.ac.ncl.rbac.common.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This entity is used to return to front-end
 */
public class UserVo {

    private Integer userId;

    private String account;

    private String userName;

    private String userEmail;

    private String phoneNumber;

    private String password;

    private Boolean enabled;

    List<Role> roles;

    List<Permission> permissions;

    public UserVo(User user, List<Role> roles, List<Permission> permissions) {
        this.userId = user.getUserId();
        this.account = user.getAccount();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.password = user.getPassword();
        this.enabled = user.getEnabled();
        this.roles = new ArrayList<>(roles);
        this.permissions = new ArrayList<>(permissions);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }


    @Override
    public String toString() {
        return "UserVo{" +
                "userId=" + userId +
                ", account='" + account + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
