package com.qtech.it.Dao;

import com.qtech.it.Po.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByName(String name);
//    List<User> findByEmail(String email);
    void deleteByName(String name);
//    void deleteByEmail(String email);
}
