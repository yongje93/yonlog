package com.yonlog.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = YonlogMockSecurityContent.class)
public @interface YonlogMockUser {

    String name() default "용제";

    String email() default "yongje@gmail.com";

    String password() default "";

}
