package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인이 됨.

    @ColumnDefault("0")
    private int count; // 조회수

    @ManyToOne // Many = Board, User = One (한 명의 유저는 여러개의 게시글을 쓸 수 있다.)
    @JoinColumn(name="userId") // 필드 값은 userId라고 만들어지고 연관관계는 ManyToOne으로 만들어짐
    private User user; // DB는 오브젝트를 저장할 수 없다. 따라서 ForeignKey를 사용. (자바는 오브젝트를 저장할 수 있음.)

    @CreationTimestamp
    private Timestamp createDate;
}
