package com.yonlog.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditor {

    private final String title;
    private final String content;

    @Builder
    private PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
