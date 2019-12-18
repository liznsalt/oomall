package xmu.oomall.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.oomall.component.ERole;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;
import standard.oomall.domain.Admin;

/**
 * @author liznsalt
 */
@Alias("mallAdmin")
public class MallAdmin extends Admin implements IMember {

    @Override
    @JsonIgnore
    public boolean beEnable() {
        return !getBeDeleted();
    }

    @JsonIgnore
    public boolean isValid() {
        return !StringUtils.isEmpty(getPassword())
                && !StringUtils.isEmpty(getUsername())
                && getRoleId() != null && getRoleId() > 0;
    }

}
