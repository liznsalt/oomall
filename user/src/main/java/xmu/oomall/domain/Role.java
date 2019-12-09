package xmu.oomall.domain;

/**
 * @author liznsalt
 */
public enum Role {
    // 用户
    USER("用户"),
    // 管理员
    ADMIN("管理员");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
