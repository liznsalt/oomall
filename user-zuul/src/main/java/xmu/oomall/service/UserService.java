package xmu.oomall.service;

import common.oomall.api.CommonResult;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.domain.MallMember;
import xmu.oomall.domain.MallUser;
import xmu.oomall.domain.details.MallMemberDetails;

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
    CommonResult register(String username, String password, String telephone, String authCode);
    @Transactional
    CommonResult register(MallUser user, String authCode);
    String generateAuthCode(String telephone);
    @Transactional
    CommonResult updatePassword(String telephone, String password, String authCode);
    @Transactional
    CommonResult updateTelephone(String telephone, String password, String authCode);
    @Transactional
    CommonResult updateRebate(Integer userId, Integer rebate);
    String login(String username, String password);
    String refreshToken(String token);

    MallMemberDetails findDetailsByName(String username);
    MallMember findMemberByName(String username);

    List<MallUser> list();
}
