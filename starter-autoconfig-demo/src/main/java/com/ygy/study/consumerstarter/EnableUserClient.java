package com.ygy.study.consumerstarter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(UserAutoConfigure.class)
public @interface EnableUserClient {
}
