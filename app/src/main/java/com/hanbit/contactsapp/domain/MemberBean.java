package com.hanbit.contactsapp.domain;

/**
 * Created by hb2008 on 2017-03-08.
 */

public class MemberBean extends T{
    protected String id,name,phone,age,address,salary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return age;
    }

    public void setPhone(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MemberBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + phone + '\'' +
                ", salary='" + age + '\'' +
                ", address='" + address + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
