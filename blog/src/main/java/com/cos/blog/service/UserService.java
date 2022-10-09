package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// Service는 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. (IoC를 해준다.)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired // DI가 돼서 주입된다.
    private BCryptPasswordEncoder encoder;

    // Transactional로 인해 회원가입 전체 서비스가 하나의 transaction으로 묶임. 다 끝나면 커밋됨
    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword(); // 1234 원문
        String encPassword = encoder.encode(rawPassword); // 해쉬화
        user.setPassword(encPassword);
        user.setRole(RoleType.USER); // 다른 정보들은 자동적으로 들어가지만 role은 그렇지 않기에 수동으로 넣어줌
        userRepository.save(user);
    }

    @Transactional
    public void 회원수정(User user) {
        // 수정시에는 영속성 컨텐스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정하는 것이 좋다.
        // select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 사기 위해서
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주기 때문에
        User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());

        // 회원 수정 함수 종료 = 서비스 종료 = 트랜잭션 종료 -> commit이 자동으로 된다.
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update 문을 날려준다.
    }

//    @Transactional(readOnly = true) // Select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성을 유지시킬 수 있음.)
//    public User 로그인(User user) {
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}
