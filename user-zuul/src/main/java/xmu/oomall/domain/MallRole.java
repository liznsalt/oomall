package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Role;

/**
 * @author liznsalt
 */
@Alias("mallRole")
public class MallRole extends Role {
    public static Integer VISITOR = 0;
    public static Integer SUPER_ADMIN = 1;
    public static Integer USER = 4;

    public static boolean isUserId(Integer id) {
        return id != null && id.equals(USER);
    }

    public static boolean isSuperAdminId(Integer id) {
        return id != null && id.equals(SUPER_ADMIN);
    }

    public static boolean isCannotDelete(Integer id) {
        return VISITOR.equals(id) || isUserId(id) || isSuperAdminId(id);
    }
}
