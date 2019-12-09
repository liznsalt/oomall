package xmu.oomall.domain;

/**
 * @author liznsalt
 */
public interface IMember {
    Integer getId();
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    Role getRole();
    boolean beEnable();
}
