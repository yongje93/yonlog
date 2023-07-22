package com.yonlog.service;

import com.yonlog.domain.Post;
import com.yonlog.repository.PostRepository;
import com.yonlog.request.PostCreate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @DisplayName("글 작성")
    @Test
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertThat(postRepository.count()).isEqualTo(1);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("제목입니다.");
        assertThat(post.getContent()).isEqualTo("내용입니다.");
    }

    @DisplayName("글 1개 조회")
    @Test
    void test2() {
        // given
        Post reqeustPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(reqeustPost);

        // when
        Post post = postService.get(reqeustPost.getId());

        // then
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("foo");
        assertThat(post.getContent()).isEqualTo("bar");
    }

}