package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Bean 등록. 서비스임을 알려줌
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public PrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 스프링이 로그인 요청을 가로챌 때 username, password 변수 두개를 가로채는데
    // password 부분 처리는 알아서 함.
    // username이 DB에 있는지만 확인해 주면 됨. 이 부분을 아래 메소드에서 처리
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // findByusername 메소드는 Optional 타입을 리턴하기 때문에 User 타입으로 받아줄 수 없는데 아래와 같이 코드를 작성하면 받을 수 있음.
        User principal = userRepository.findByUsername(username)
                .orElseThrow(() -> {
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. :" + username);
        });
        return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장이 됨.
    }
}
