package com.jueun.toyproject.web.dto;

import com.jueun.toyproject.domain.posts.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public PostRequestDto(String title,
                              String content,
                              String author)
    {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Post toEntity(){
        return Post.builder().title(title)
                .author(author)
                .content(content)
                .build();
    }
}
