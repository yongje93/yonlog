package com.yonlog.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SessionResponse {

    private final String accessToken;
}
