package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

// RestController는 html파일이 아니라 data를 리턴해주는 controller
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id); //  이렇게 하고 코드를 마무리할 경우 id에 대한 null값 처리가 안됨
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }

        return "삭제 되었습니다. id: " + id;
    }

    // 밑의 /dummy/user/{id}와 url이 겹치는데, 각각 Get과 Put이기 때문에 상관 없음.
    // save 함수는 id를 전달하지 않으면 insert를 해주고
    // save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    // save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 진행
    // email. password를 받아서 수정할 예정.
    @Transactional // 함수 종료시에 자동 commit이 됨.
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json데이터를 요청 -> MassageConverter의 Jackson 라이브러리가 Java Object로 변환해서 받아준다. 이 때 필요한 어노테이션이 @RequestBody임.
        System.out.println("id: " + id);
        System.out.println("password: " + requestUser.getPassword());
        System.out.println("email: " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다");
        }); // 이 user는 데이터베이스에서 존재하던 기존의 값을 가져온 것임.
        user.setPassword(requestUser.getPassword()); // 기존 값에 파라미터로 받은 값을 넣어줌
        user.setEmail(requestUser.getEmail()); // 기존 값에 파라미터로 받은 값을 넣어줌

        // userRepository.save(user); // 위에서 변경된 Object를 집어넣음

        // 더티 체킹
    return user;
    }

    // http://localhost:8000/blog/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한 페이지당 2건의 데이터를 리턴받아 볼 예정, Paging해서 2개를 가져옴. 순서는 최신순으로
    // http://localhost:8000/blog/dummy/user?page=0 url로 페이지별로 볼 수 있음.
    @GetMapping("/dummy/user")
    public Page<User> pageList(@PageableDefault(size=2, sort="id", direction= Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUsers =  userRepository.findAll(pageable);

        List<User> users = pagingUsers.getContent();
        return pagingUsers;
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
