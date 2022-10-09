package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 로브젝트를
// 스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다. 이 때 저장되는 타입을 지정
@Data
public class PrincipalDetail implements UserDetails {

    public PrincipalDetail(User user) {
        this.user = user;
    }

    private User user; // 콤포지션(객체를 품고 있는 것)

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 리턴한다. (ture: 만료가 안됐음을 의미)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않는지 리턴한다. (ture: 잠기지 않았음을 의미)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않는지 리턴한다. (ture: 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    // 비밀번호가 활성화(사용가능)인지 리턴한다. (ture: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정이 어떤 권한을 가졌는지 리턴함 (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 여기서는 한개만 있음)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();

        // 익명함수 -> 람다로 변환
//        collectors.add(new GrantedAuthority() {
//            public String getAuthority() {
//                return "ROLE_" + user.getRole(); // ROLE_은 스프링에서 role을 받을 때의 규칙임.
//            }
//        });

        collectors.add(() -> {return "ROLE_" + user.getRole();});
        return collectors;
    }
}
