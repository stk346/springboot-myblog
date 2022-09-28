package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

// RestController는 html파일이 아니라 data를 리턴해주는 controller
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI)
    private UserRepository userRepository;

    // http://localhost:8000/blog/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한 페이지당 2건의 데이터를 리턴받아 볼 예정, Paging해서 2개를 가져옴. 순서는 최신순으로
    // http://localhost:8000/blog/dummy/user?page=0 url로 페이지별로 볼 수 있음.
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction= Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUsers =  userRepository.findAll(pageable);

        List<User> users = pagingUsers.getContent();
        return users;
    }

    // {id} 주소로 파라미터를 전달 받을 수 있음.
    // http://localhost:8000/blog/dummy/user/5
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // findById의 return 타입이 Optional인 이유: user/4를 찾을 때 데이터베이스에서 못찾아오게 되면 user가 null이 될 수 있음.
        // 그럼 return null이 되기 때문에 프로그램에 문제가 생길 수 있음.
        // 따라서 Optional로 User객체를 감싸서 가져오며 이로써 null인지 아닌지 판단해서 return할 수 있음.

        // 람다식
//        User user = userRepository.findById(id).orElseThrow(() -> {
//            return new IllegalArgumentException("해당 사용자는 없습니다.");
//        });
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });
        // 요청은 웹 브라우저에서 했음. 웹브라우저는 html이나 java만 이해 가능.
        // user 객체 = 자바 오브젝트. 따라서 웹 브라우저가 이해하지 못함
        // 따라서 웹브라우저가 이해할 수 있는 데이터로 변환해야함. -> json(Gson 라이브러리)
        // 스프링부트는 MessageConverter가 응답시에 자동으로 작동함.
        // 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해 브라우저에게 던져줌.
        return user;
    }

    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 요청함
    // 그렇게 되면 파라미터에 해당 값들이 들어가고 데이터가 생성됨. 이외의 데이터는 자동으로 생성되니 안받아도 됨
    @PostMapping("/dummy/join")
    public String join(User user) { // x-www-form-urlencoded 형식으로 데이터가 통신되는데, 이 때의 key:value 형태를 스프링이 자동으로 파싱해 파라미터로 넣어준다.
        System.out.println("id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());
        System.out.println("role: " + user.getRole());
        System.out.println("createDate: " + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
