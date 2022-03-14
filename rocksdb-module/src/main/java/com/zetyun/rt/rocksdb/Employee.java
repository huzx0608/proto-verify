package com.zetyun.rt.rocksdb;

class Employee {
    private int id;
    private String name;
    private double salary;
    private State state;

    public Employee(int id, String name, double salary, State state) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", state=" + state +
                '}';
    }
}
