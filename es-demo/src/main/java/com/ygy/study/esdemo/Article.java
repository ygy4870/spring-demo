package com.ygy.study.esdemo;

import lombok.Data;

@Data
public class Article {
    private Integer id;
    private String title;
    private String author;
    private String content;
}
