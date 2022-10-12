package learn.budget.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class MyRole {

    private int roleId;
    private String roleName;
    private List<AppUser> roleUsers;

    public MyRole(int roleId, String roleName, List<AppUser> roleUsers) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleUsers = roleUsers;
    }

    public MyRole() {}

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<AppUser> getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(List<AppUser> roleUsers) {
        this.roleUsers = roleUsers;
    }

    public GrantedAuthority getAuthority(){
        return new SimpleGrantedAuthority(roleName);
    }
}
