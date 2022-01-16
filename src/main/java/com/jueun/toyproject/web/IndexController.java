package com.jueun.toyproject.web;

import com.jueun.toyproject.config.auth.LoginUser;
import com.jueun.toyproject.config.auth.dto.SessionUser;
import com.jueun.toyproject.service.PostService;
import com.jueun.toyproject.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("post", postService.findAllDesc());

        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/post/save")
    public String postsSave(Model model, @LoginUser SessionUser user) {
        model.addAttribute("userName", user.getName());
        return "posts-save";
    }

    @GetMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
