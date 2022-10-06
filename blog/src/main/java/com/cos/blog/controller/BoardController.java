package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController { // 컨트롤러에서 세션을 어떻게 찾는지?

    @GetMapping({"", "/"}) // 아무것도 안붙였을 때랑 /를 붙였을 때 둘 다 "joinForm.jsp"로 간다.
    public String index(@AuthenticationPrincipal PrincipalDetail principal) {
//         WEB-INF/views/joinForm.jsp
        if (principal == null) {
            throw new RuntimeException("not loged in");
        }
        System.out.println("로그인 사용자 아이디: " + principal.getUsername());
        return "index";
    }

    @GetMapping("/test") // 아무것도 안붙였을 때랑 /를 붙였을 때 둘 다 "joinForm.jsp"로 간다.
    public String testController() {
//         WEB-INF/views/joinForm.jsp
        System.out.println("로그인 사용자 아이디: ");
        return "index";
    }
}
