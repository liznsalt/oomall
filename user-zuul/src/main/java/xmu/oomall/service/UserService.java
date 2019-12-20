package xmu.oomall.service;

import common.oomall.api.CommonResult;
import common.oomall.util.ResponseUtil;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.domain.MallMember;
import xmu.oomall.domain.MallUser;
import xmu.oomall.domain.details.MallMemberDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liznsalt
 */
public interface UserService {
    MallUser findByName(String username);
    MallUser findByTelephone(String telephone);
    MallUser findById(Integer id);
    List<MallUser> findUsers(Integer page, Integer limit);

    @Transactional
    Object register(String username, String password, String telephone, String authCode);

    /**
     * @deprecated
     */
    @Transactional
    CommonResult register(MallUser user, String authCode);

    String generateAuthCode(String telephone);

    @Transactional
    Object updatePassword(String telephone, String password, String authCode);

    @Transactional
    Object updateTelephone(String telephone, String password, String authCode, String newPhone);

    @Transactional
    Object updateRebate(Integer userId, Integer rebate);

    String login(String username, String password, HttpServletRequest request);

    String refreshToken(String token);

    MallMemberDetails findDetailsByName(String username);
    MallMember findMemberByName(String username);

    List<MallUser> list();
    List<MallUser> listByCondition(String username, Integer page, Integer limit);
}
