package xmu.oomall.domain;

import common.oomall.component.ERole;

/**
 * @author liznsalt
 */
public interface IMember {
    Integer getId();
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    Integer getRoleId();
    boolean beEnable();
}
