package xmu.oomall.domain;

import common.oomall.util.JwtTokenUtil;
import org.springframework.security.core.userdetails.UserDetails;
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
        map.put(JwtTokenUtil.CLAIM_KEY_USERNAME, member.getUsername());
        map.put(JwtTokenUtil.CLAIM_KEY_ROLE, member.getRole().getName());
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
    public Role getRole() {
        return member.getRole();
    }
}
