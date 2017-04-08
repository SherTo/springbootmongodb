package com.phyl;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xh on 2017/4/8.
 */
@RestController
public class UserController {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    UserRepository userRepository;


    @RequestMapping("add")
    public String add() {
        User user = new User();
        user.setName("sher");
        user.setAge(11);
        return userRepository.save(user).toString();
    }

    @RequestMapping("edit/{id}")
    public String edit(@PathVariable String id) {
        User user = userRepository.findOne(id);
        user.setName("sher_edit");
        return userRepository.save(user).toString();
    }

    @RequestMapping(value = "del/{id}")
    public void del(@PathVariable String id) {
        userRepository.delete(id);
    }

    /**
     * mongoTemplate常用操作 具体参看Api
     */
    @RequestMapping("/test")
    public void test() {
        User user = new User();
        user.setName("sher");
        user.setAge(11);
        mongoTemplate.save(user);//添加

        Query query = new Query(Criteria.where("name").is("sher"));
        List<User> userList = mongoTemplate.find(query, User.class);//查询所有name是sher
        userList.forEach(System.out::println);

        User one = mongoTemplate.findOne(query, User.class);//单个结果查询操作
        System.out.println("查询单个User："+one.toString());
        Update update = new Update().set("age", 22);
        User modify = mongoTemplate.findAndModify(query, update, User.class);//查询name是sher并修改age为33
        System.out.println("更新的User:" + modify);
//        mongoTemplate.updateFirst(query, update, User.class);//进行第一条符合要求的数据更新
//        mongoTemplate.updateMulti(query, update, User.class);//进行更新多行数据
//        mongoTemplate.remove(user);//进行数据删除

    }
}
