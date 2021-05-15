package com.ygy.study.mysql;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RandomRedBlue {

    private String red1;
    private String red2;
    private String red3;
    private String red4;
    private String red5;
    private String red6;
    private String blue;

    private int number;

    public Set<String> listRed() {
        Set<String> set = new HashSet<>();
        set.add(red1);
        set.add(red2);
        set.add(red3);
        set.add(red4);
        set.add(red5);
        set.add(red6);
        return set;
    }

}
