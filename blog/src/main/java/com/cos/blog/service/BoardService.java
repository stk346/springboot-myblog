package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// Service는 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. (IoC를 해준다.)
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(Board board, User user) { // title, content,
        board.setCount(0); // 글쓰기를 하면 조회수는 default로 0
        board.setUser(user);
        boardRepository.save(board);
    }

    public List<Board> 글목록() {
        return boardRepository.findAll();
    }

//    @Transactional(readOnly = true) // Select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성을 유지시킬 수 있음.)
//    public User 로그인(User user) {
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}
