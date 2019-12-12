package xmu.oomall.domain;

import common.oomall.component.ERole;
import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Admin;

/**
 * @author liznsalt
 */
@Alias("mallAdmin")
public class MallAdmin extends Admin implements IMember {

    @Override
    public ERole getRole() {
        return ERole.ADMIN;
    }

    @Override
    public boolean beEnable() {
        return !getBeDeleted();
    }

}
