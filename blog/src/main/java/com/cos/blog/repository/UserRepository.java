package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// DAO
// 자동으로 bean 등록이 된다.
// User 테이블을 관리하는 Repository이며 User 테이블의 PK는 Integer임을 뜻함.
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {

    // 로그인을 위한 함수. JPA Naming 전략
    // 이 함수는 JPA가 들고 있지는 않지만 이름을 아래 함수와 같이 만들면 아래와 같은 쿼리가 자동으로 실행된다. (?는 각각 함수의 파라미터로 들어오는 username, password를 뜻함)
    // SELECT * FROM user WHERE username = ? AND password = ?;
    User findByUsernameAndPassword(String username, String password);

    // 아래의 함수와 위의 함수는 똑같은 기능을 수행한다.
//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login (String username, String password);
}

