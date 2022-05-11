package com.zetyun.protostuff.main;

import com.zetyun.protostuff.ProtostuffUtils;
import com.zetyun.protostuff.module.School;
import com.zetyun.protostuff.module.Student;
import com.zetyun.protostuff.module.XSchool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProtoStuffMain {
    public static void main(String[] args) {
        Student stu1 = new Student(20, null,"zhangsan");
        Student stu2 = new Student(20, null, "lisi");
        Student stu3 = new Student(20, null, "wangwu");
        List<Student> students = new ArrayList<Student>();
        students.add(stu1);
        students.add(null);
        students.add(stu3);

        List<Student> fakeList = new ArrayList<>();
        School school = new School("abc", students);
        System.out.println("Before===>" + school);
        //首先是序列化
        byte[] bytes = ProtostuffUtils.serialize(school);
        System.out.println("序列化后: " + bytes.length);
        //然后是反序列化
        School group1 = ProtostuffUtils.deserialize(bytes, School.class);
        System.out.println("反序列化后: " + school.toString());
        System.out.println("==========================================================");

        HashMap<String, Student> localStudents = new HashMap<>();
        localStudents.put("stu1", stu1);
        localStudents.put("stu2", null);
        localStudents.put("stu3", stu3);
        XSchool newSchool = new XSchool(localStudents);
        System.out.println("Before===>" + newSchool.toString());
        byte[] newBytes = ProtostuffUtils.serialize(newSchool);
        XSchool newSchool2 = ProtostuffUtils.deserialize(newBytes, XSchool.class);
        System.out.println("After===>" + newSchool2.toString());
    }
}
