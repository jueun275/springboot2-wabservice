package com.jueun.toyproject.wab;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jueun.toyproject.domain.posts.Post;
import com.jueun.toyproject.domain.posts.PostRepository;
import com.jueun.toyproject.web.dto.PostRequestDto;
import com.jueun.toyproject.web.dto.PostUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception{
        postRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostRequestDto requestDto = PostRequestDto.builder().title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/post";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                        .andExpect(status().isOk());

        //then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post_수정된다() throws Exception{
        //given
        Post savedPost = postRepository.save(Post.builder()
               .title("create")
               .content("create")
               .author("jun")
               .build());

        Long updateId = savedPost.getId();

        String expectedTile = "update";
        String expectedContent = "update";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(expectedTile)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/post/" + updateId;

        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                        .andExpect(status().isOk());
        //then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTile);

    }
}
