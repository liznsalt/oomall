package common.oomall.component;

/**
 * @author liznsalt
 */
public enum ERole {
    // 用户
    USER("用户", 2),
    // 管理员
    ADMIN("管理员", 1);

    private String name;
    private Integer roleId;

    private ERole(String name, Integer roleId) {
        this.name = name;
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
