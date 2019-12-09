package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Admin;

/**
 * @author liznsalt
 */
@Alias("mallAdmin")
public class MallAdmin extends Admin implements IMember {

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public boolean beEnable() {
        return !getBeDeleted();
    }

}
