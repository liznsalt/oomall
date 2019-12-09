package xmu.oomall.service;

import common.oomall.api.CommonResult;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallMember;
import xmu.oomall.domain.details.MallMemberDetails;

import java.util.List;

/**
 * @author liznsalt
 */
public interface AdminService {
    MallAdmin findById(Integer id);
    MallAdmin findByName(String name);
    List<MallAdmin> findAdmins(Integer page, Integer limit, String sort, String order);
    MallMemberDetails findDetailsByName(String username);
    MallMember findMemberByName(String username);

    boolean delete(Integer id);
    MallAdmin update(Integer id, MallAdmin admin);
    MallAdmin add(MallAdmin admin);

    String login(String username, String password);
    CommonResult logout(String username);
    String refreshToken(String token);
}
