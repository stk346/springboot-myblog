package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import javax.persistence.*;

// ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Data // Getter Setter
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 전체 생성자
@Builder // 빌더패턴! (나중에 설명)
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert  insert시에 null인 필드를 제외시켜준다.
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 30) //null이 될 수 없고 최대 길이는 30
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 길이가 100으로 넉넉한 이유는 나중에 해쉬를 이용해서 암호회할 것이기 때문에
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    // @ColumnDefault("user")
    // DB는 RoleType이라는게 없다. 따라서 해당 오브젝트의 타입을 알려줘야한다.
    @Enumerated(EnumType.STRING)
    private RoleType role; // ADMIN, USER

    @CreationTimestamp // 시간이 자동으로 입력된다.
    private Timestamp createDate;
}
