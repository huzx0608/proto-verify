package com.zetyun.protostuff.main;

import com.zetyun.protostuff.module.School;
import com.zetyun.protostuff.module.Student;
import com.zetyun.protostuff.wrapper.SerializationUtil;

import java.util.ArrayList;
import java.util.List;

public class ProtoStuffWrapMain {
    public static void main(String[] args) {
        Student stu1 = new Student(20, null,"zhangsan");
        Student stu2 = new Student(20, null, "lisi");
        Student stu3 = new Student(20, null, "wangwu");
        List<Student> students = new ArrayList<Student>();
        students.add(stu1);
        students.add(null);
        students.add(stu3);
        School school = new School( "school1", students);
        System.out.println("=====>Before school: " + school);

        byte[] bytes = SerializationUtil.marshall(school);
        School school2 = (School) SerializationUtil.unmarshall(bytes);
        System.out.println("=====>After school2: " + school2);
    }
}
