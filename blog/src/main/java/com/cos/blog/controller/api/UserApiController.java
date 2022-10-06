package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private HttpSession session; // session 객체는 spring container가 bin으로 등록해서 가지고 있기 때문에 @AutoWired로 가능하다.

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
        System.out.println("UserApiController : save 호출됨");
        // 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // Jackson이 자바 오브젝트를 JSON으로 변환해서 리턴
    }

    // 스프링의 전통적인 로그인 방식(시큐리티 사용x)
//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//        System.out.println("UserApiController : login 호출됨");
//        User principal = userService.로그인(user); // principal: 접근 주체라는 뜻
//
//        if (principal != null) {
//            session.setAttribute("principal", principal);
//        }
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // Jackson이 자바 오브젝트를 JSON으로 변환해서 리턴
//    }
}
