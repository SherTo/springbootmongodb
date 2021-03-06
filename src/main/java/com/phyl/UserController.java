package com.phyl;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
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
        System.out.println("查询单个User：" + one.toString());
        Update update = new Update().set("age", 24);
        User modify = mongoTemplate.findAndModify(query, update, User.class);//数据库更新了但是返回的是旧的对象
        System.out.println("返回更新前对象:" + modify);
        Update update2 = new Update().set("age", 26);
        //upsert(true),update和insert结合体默认为false,当它为true的时候，update方法会首先查找与第一个参数匹配的记录，在用第二个参数更新之，如果找不到与第一个参数匹配的的记录，就插入一条
        FindAndModifyOptions upsert = new FindAndModifyOptions().returnNew(true).upsert(true);
        User andModify = mongoTemplate.findAndModify(query, update2, upsert, User.class);//返回更新后对象
        System.out.println("返回更新后对象" + andModify);
//        mongoTemplate.updateFirst(query, update, User.class);//进行第一条符合要求的数据更新
//        mongoTemplate.updateMulti(query, update, User.class);//进行更新多行数据
//        mongoTemplate.remove(user);//进行数据删除

    }
}
