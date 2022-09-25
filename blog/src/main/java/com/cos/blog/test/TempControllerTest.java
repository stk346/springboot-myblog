package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

    // http://localhost:8000/blog/temp/home
    // system.out.println 로그를 출력하지만 404 메시지를 띄움 (경로를 바꿨기 때문에)
    @GetMapping("temp/home")
    public String tempHome() {
        System.out.println("tempHome");
        // 파일리턴 기본경로: src/main/resources/static
        // 리턴명: /home.html
        // 풀경로: /src/main/resources/static/home.html
        return "/home.html";
    }

    @GetMapping("/temp/img") // static 폴더 안에 있는 정적 파일이기 떄문에 찾아준다.
    public String tempImg() {
        return "/a.png";
    }

    @GetMapping("/temp/jsp") // jsp파일은 정적인 파일이 아닌 동적인 파일이기 때문에 찾지 못한다.
    public String tempJsp() {
        // prefix: /WEB-INF/views/
        // suffix: .jsp
        // 풀네임: /WEB-INF/views/test.jsp
        return "/test";
    }
}
