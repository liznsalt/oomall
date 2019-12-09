package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.User;

/**
 * @author liznsalt
 */
@Alias("mallUser")
public class MallUser extends User implements IMember {
    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public void setUsername(String username) {
        setName(username);
    }

    @Override
    public Role getRole() {
        return Role.USER;
    }

    @Override
    public boolean beEnable() {
        return !getBeDeleted();
    }
}
