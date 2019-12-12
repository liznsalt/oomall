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
    private static Map<Integer, List<String>> allPrivilege = null;
    public static void setAllPrivilege(Map<Integer, List<String>> allPrivilege) {
        MallPrivilege.allPrivilege = allPrivilege;
    }
    public static Map<Integer, List<String>> getAllPrivilege() {
        return MallPrivilege.allPrivilege;
    }
}
