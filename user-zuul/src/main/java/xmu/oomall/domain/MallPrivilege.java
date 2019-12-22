package xmu.oomall.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private static List<MallPrivilege> whitePrivilegeList = null;

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

    public static void setWhitePrivilegeList(List<MallPrivilege> privilegeList) {
        MallPrivilege.whitePrivilegeList = privilegeList;
    }

    public static List<MallPrivilege> getWhitePrivilegeList() {
        return MallPrivilege.whitePrivilegeList;
    }
}
