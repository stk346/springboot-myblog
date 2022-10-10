package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController { // 컨트롤러에서 세션을 어떻게 찾는지?

    @Autowired
    private BoardService boardService;

    // 컨트롤러에서 세션을 어떻게 찾는지?
    // index라는 페이지로 "boards" 가 날라간다.
    @GetMapping({"", "/"})
    public String index(Model model, @PageableDefault(size=3, sort="id", direction= Sort.Direction.DESC) Pageable pageable) { // 스프링에서는 데이터를 가져갈 때 모델이 필요하다. (모델은 request 정보임)
        model.addAttribute("boards", boardService.글목록(pageable));
        return "index"; // RestController가 아닌 controller는 return할 때 viewResolver가 작동한다. 이 때 index 페이지로 model의 정보를 들고 이동한다.
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/updateForm";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));

        return "board/detail";
    }

    // User 권한이 필요
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
