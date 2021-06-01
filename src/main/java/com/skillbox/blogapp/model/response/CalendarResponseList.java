package com.skillbox.blogapp.model.response;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import lombok.Getter;

@Getter
public class CalendarResponseList {

    private final Set<Integer> years;
    private final Map<String, BigInteger> posts;

    public CalendarResponseList(Set<Integer> years, Map<String, BigInteger> posts) {
        this.years = years;
        this.posts = posts;
    }

}
