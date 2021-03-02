package uk.ac.ncl.rbac.common.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Role entity
 */
public class Role implements Serializable {

    private static final long serialVersionUID = -827696761558756932L;

    private Integer roleId;

    private String roleName;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return getRoleName().equals(role.getRoleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleName());
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
