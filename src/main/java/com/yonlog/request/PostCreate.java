package com.yonlog.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostCreate {

    private String title;
    private String content;
}
