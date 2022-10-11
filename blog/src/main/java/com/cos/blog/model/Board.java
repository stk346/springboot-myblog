package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    private int count; // 조회수

    @ManyToOne // Many = Board, User = One (한 명의 유저는 여러개의 게시글을 쓸 수 있다.)
    @JoinColumn(name="userId") // 필드 값은 userId라고 만들어지고 연관관계는 ManyToOne으로 만들어짐
    private User user; // DB는 오브젝트를 저장할 수 없다. 따라서 ForeignKey를 사용. (자바는 오브젝트를 저장할 수 있음.)

    // mappedBy가 적혀있으면 연관관계의 주인이 아니다.(FK가 아니기에 DB에 컬럼을 만들지 않음) Reply의 board가 FK임
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 댓글을 불러오는 방식은 lazy와 eager(즉시)중 선택 가능. 이 프로젝트에서는 펼치기 등의 버튼이 없으므로 즉시 데이터를 불러와야 함
    @JsonIgnoreProperties("board")
    @OrderBy("id desc")
    private List<Reply> replys; // 하나의 게시글에는 여러개의 댓글이 달릴 수 있음

    @CreationTimestamp
    private Timestamp createDate;
}
