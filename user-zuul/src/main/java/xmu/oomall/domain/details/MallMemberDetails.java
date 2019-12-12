package xmu.oomall.domain.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xmu.oomall.domain.IMember;

import java.util.Collection;
import java.util.Collections;

/**
 * @author liznsalt
 */
public class MallMemberDetails implements UserDetails {

    private IMember admin;

    public MallMemberDetails(IMember admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return Collections.singletonList(new SimpleGrantedAuthority("TEST"));
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !admin.beEnable();
    }

    public IMember getAdmin() {
        return admin;
    }
}
