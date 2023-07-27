package com.yonlog.service;

import com.yonlog.domain.Post;
import com.yonlog.exception.PostNotFound;
import com.yonlog.repository.PostRepository;
import com.yonlog.request.PostCreate;
import com.yonlog.request.PostEdit;
import com.yonlog.request.PostSearch;
import com.yonlog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Post req = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(req);

        // when
        PostResponse post = postService.get(req.getId());

        // then
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("foo");
        assertThat(post.getContent()).isEqualTo("bar");
    }

    @DisplayName("글 1페이지 조회")
    @Test
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("foo" + i)
                        .content("bar" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        assertThat(posts.size()).isEqualTo(10L);
        assertThat(posts.get(0).getTitle()).isEqualTo("foo19");
    }

    @DisplayName("글 제목 수정")
    @Test
    void test4() {
        // given
        Post post = Post.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정")
                .content("내용 테스트")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        assertThat(changePost.getTitle()).isEqualTo("제목 수정");
        assertThat(changePost.getContent()).isEqualTo("내용 테스트");
    }

    @DisplayName("글 내용 수정")
    @Test
    void test5() {
        // given
        Post post = Post.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("내용 수정")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        assertThat(changePost.getTitle()).isEqualTo("제목 테스트");
        assertThat(changePost.getContent()).isEqualTo("내용 수정");
    }

    @DisplayName("게시글 삭제")
    @Test
    void test6() {
        // given
        Post post = Post.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .build();

        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        assertThat(postRepository.count()).isZero();
    }

    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    @Test
    void test7() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // expected
        assertThatThrownBy(() -> postService.get(post.getId() + 1))
                .isInstanceOf(PostNotFound.class)
                .hasMessageContaining("존재하지 않는 글입니다.");
    }

    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    @Test
    void test8() {
        // given
        Post post = Post.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .build();

        postRepository.save(post);

        // expected
        assertThatThrownBy(() -> postService.delete(post.getId() + 1))
                .isInstanceOf(PostNotFound.class)
                .hasMessageContaining("존재하지 않는 글입니다.");
    }

    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    @Test
    void test9() {
        // given
        Post post = Post.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("내용 수정")
                .build();

        // expected
        assertThatThrownBy(() -> postService.edit(post.getId() + 1, postEdit))
                .isInstanceOf(PostNotFound.class)
                .hasMessageContaining("존재하지 않는 글입니다.");
    }

}