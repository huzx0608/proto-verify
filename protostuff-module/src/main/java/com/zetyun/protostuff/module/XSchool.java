package com.zetyun.protostuff.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class XSchool {
    private Map<String, Student> students;
}
