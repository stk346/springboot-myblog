package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// Service는 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. (IoC를 해준다.)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Transactional로 인해 회원가입 전체 서비스가 하나의 transaction으로 묶임. 다 끝나면 커밋됨
    @Transactional
    public void 회원가입(User user) {
        userRepository.save(user);
    }

//    @Transactional(readOnly = true) // Select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성을 유지시킬 수 있음.)
//    public User 로그인(User user) {
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}
