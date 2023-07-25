package com.yonlog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yonlog.domain.Post;
import com.yonlog.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.yonlog.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
