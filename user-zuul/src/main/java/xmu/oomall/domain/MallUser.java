package xmu.oomall.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.oomall.component.ERole;
import org.apache.ibatis.type.Alias;
import standard.oomall.domain.User;

/**
 * @author liznsalt
 */
@Alias("mallUser")
public class MallUser extends User implements IMember {
    @JsonIgnore
    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public void setUsername(String username) {
        setName(username);
    }

    @Override
    public boolean beEnable() {
        return !getBeDeleted();
    }
}
