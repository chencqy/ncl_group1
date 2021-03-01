package uk.ac.ncl.rbac.common.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Permission entity
 */
public class Permission implements Serializable {

    private static final long serialVersionUID = -5288264976576952756L;

    private Integer permissionId;

    private String permissionName;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return getPermissionName().equals(that.getPermissionName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPermissionName());
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }
}
