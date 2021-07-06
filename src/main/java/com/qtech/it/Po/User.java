package com.qtech.it.Po;

import javax.persistence.*;

@Entity
@Table(name = "users",schema = "testdemo")
//Entity,说白了就是会被映射到数据库中的Java Object
//加了@Entity注解, Spring框架会得知这个类是一个Entity, Hibernate会把这个类映射到数据库中的关系表, 这个关系表的列与User的所有属性一一对应
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
