package com.ygy.study.mysql;

import lombok.Data;

@Data
public class TwoColorBall {

    private String red;
    private String blue;
    private String term;
    private int total;

    private RandomRedBlue redBlue;
}
