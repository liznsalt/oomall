package xmu.oomall.domain;

import common.oomall.component.ERole;
import common.oomall.util.JwtTokenUtil;
import xmu.oomall.domain.details.MallMemberDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liznsalt
 */
public class MallMember {
    private IMember member;
    private MallMemberDetails userDetails;

    public MallMember(IMember member) {
        this.member = member;
        this.userDetails = new MallMemberDetails(member);
    }

    /**
     * 生成当前的token
     * @return token
     */
    public Map<String, Object> generateClaims() {
        Map<String, Object> map = new HashMap<>(3);
        map.put(JwtTokenUtil.CLAIM_KEY_USERID, member.getId());
        map.put(JwtTokenUtil.CLAIM_KEY_ROLEID, member.getRoleId());
        map.put(JwtTokenUtil.CLAIM_KEY_CREATED, new Date());
        return map;
    }

    /**
     * details
     * @return UserDetails
     */
    public MallMemberDetails getUserDetails() {
        return userDetails;
    }

    // get set

    public Integer getId() {
        return member.getId();
    }
    public String getUsername() {
        return member.getUsername();
    }
    public void setUsername(String username) {
        member.setUsername(username);
    }
    public String getPassword() {
        return member.getPassword();
    }
    public void setPassword(String password) {
        member.setPassword(password);
    }
    public Integer getRoleId() {
        return member.getRoleId();
    }
}
