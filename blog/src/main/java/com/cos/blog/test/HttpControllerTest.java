package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(HTML파일) = @Controller
// RestController는 사용자가 요청한 응답에 대해 응답하는 역할을 함
@RestController
public class HttpControllerTest { // id=1&username=ssar&password=1234&email=ssar@nate.com, 매핑 작업은 MessageConverter (스프링부트)가 한다.
    private static String TAG = "HttpControllerTest";

    // 인터넷 브라우저 요청은 무조건 get 요청밖에 될 수 없다.
    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "get 요청:" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    //http://localhost:8080/http/post (insert)
    @PostMapping("/http/post") // text plain, application/json
    public String postTest(@RequestBody Member m) { // 매핑 작업은 MessageConverter (스프링부트)가 한다.
        return "post 요청:" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    //http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m) {
        return "put 요청:" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    //http://localhost:8080/http/delete (delete)
    @DeleteMapping("http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
