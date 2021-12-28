package com.jueun.toyproject.web;

import com.jueun.toyproject.service.PostService;
import com.jueun.toyproject.web.dto.PostListResponseDto;
import com.jueun.toyproject.web.dto.PostRequestDto;
import com.jueun.toyproject.web.dto.PostResponseDto;
import com.jueun.toyproject.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public Long postSave(@RequestBody PostRequestDto requestDto){
        return postService.save(requestDto);
    }

    @PutMapping("/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @DeleteMapping("post/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }

    @GetMapping("/post/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @GetMapping("/post/list")
    public List<PostListResponseDto> findAll() {
        return postService.findAllDesc();
    }
}
