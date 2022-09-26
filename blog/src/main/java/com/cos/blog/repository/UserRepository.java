package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO
// 자동으로 bean 등록이 된다.
// User 테이블을 관리하는 Repository이며 User 테이블의 PK는 Integer임을 뜻함.
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
}
