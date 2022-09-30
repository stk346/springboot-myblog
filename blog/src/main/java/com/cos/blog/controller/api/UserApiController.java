package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;


    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
        System.out.println("UserApiController : save 호출됨");
        // 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
        user.setRole(RoleType.USER); // 다른 정보들은 자동적으로 들어가지만 role은 그렇지 않기에 수동으로 넣어줌
        int result = userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK, result); // Jackson이 자바 오브젝트를 JSON으로 변환해서 리턴
    }
}