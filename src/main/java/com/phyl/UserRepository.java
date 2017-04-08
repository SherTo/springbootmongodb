package com.phyl;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by xh on 2017/4/8.
 */
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByName(String name);
}
