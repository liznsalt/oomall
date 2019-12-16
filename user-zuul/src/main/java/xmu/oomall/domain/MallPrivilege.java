package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Privilege;

import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
@Alias("mallPrivilege")
public class MallPrivilege extends Privilege {
    /**
     * @deprecated
     */
    private static Map<Integer, List<MallPrivilege>> allPrivilege = null;

    /**
     * @deprecated
     * @param allPrivilege 全部权限
     */
    public static void setAllPrivilege(Map<Integer, List<MallPrivilege>> allPrivilege) {
        MallPrivilege.allPrivilege = allPrivilege;
    }

    /**
     * @deprecated
     * @return 全部权限
     */
    public static Map<Integer, List<MallPrivilege>> getAllPrivilege() {
        return MallPrivilege.allPrivilege;
    }
}
