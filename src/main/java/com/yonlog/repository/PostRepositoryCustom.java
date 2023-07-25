package com.yonlog.repository;

import com.yonlog.domain.Post;
import com.yonlog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
