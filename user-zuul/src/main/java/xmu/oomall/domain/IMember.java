package xmu.oomall.domain;

import common.oomall.component.ERole;

/**
 * @author liznsalt
 */
public interface IMember {
    /**
     * 成员id
     * @return id
     */
    Integer getId();

    /**
     * 成员name
     * @return name
     */
    String getUsername();

    /**
     * 设置名字
     * @param username 成员名字
     */
    void setUsername(String username);

    /**
     * 密码
     * @return 密码
     */
    String getPassword();

    /**
     * 设置密码
     * @param password 密码
     */
    void setPassword(String password);

    /**
     * 角色id’
     * @return id
     */
    Integer getRoleId();

    /**
     * 成员是否可用
     * @return T/F
     */
    boolean beEnable();
}
