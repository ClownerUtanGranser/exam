package se.casparsylwan.cugexam.security;

import org.springframework.security.core.userdetails.UserDetails;
import se.casparsylwan.cugexam.entity.CugExamUser;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CugUserDetail implements UserDetails {

    private CugExamUser cugExamUser;

    public CugUserDetail(CugExamUser cugExamUser)
    {
        this.cugExamUser = cugExamUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return Arrays.stream(cugExamUser.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        //System.out.println(customer.getPassword());
        return cugExamUser.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        System.out.println(cugExamUser.getEmail());
        return cugExamUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}
