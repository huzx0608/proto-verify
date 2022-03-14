package com.zetyun.rt.rocksdb;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LambdaMain {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "aaa", 1.1, State.BUSY));
        employees.add(new Employee(2, "bbb", 10.1, State.BUSY));
        employees.add(new Employee(3, "aaa", 20.1, State.BUSY));

        List<String> results = employees.stream()
                         .map(Employee::getName)
                         .collect(Collectors.toList());

        results.forEach(System.out::println);

        Map<State, Set<String>> stateSets = employees.stream().collect(Collectors.groupingBy(Employee::getState, Collectors.mapping(Employee::getName, Collectors.toSet())));
        System.out.println(stateSets);

        Map<State, Map<String, List<Employee>>> result = employees.stream()
                .collect(Collectors.groupingBy(Employee::getState, Collectors.groupingBy(x -> {
                    if (x.getSalary() < 10) {
                        return "贫穷";
                    } else if (x.getSalary() > 10 && x.getSalary() < 20) {
                        return "小康";
                    } else {
                        return "富人";
                    }
                })));

        System.out.println(result.toString());


        String strResult = employees.stream().map(Employee::getName).collect(Collectors.joining(",", "[", "]"));
        System.out.println(strResult);
    }
}
