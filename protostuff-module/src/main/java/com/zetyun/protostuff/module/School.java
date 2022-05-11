package com.zetyun.protostuff.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class School {
    private String name;
    private List<Student> students;
}
