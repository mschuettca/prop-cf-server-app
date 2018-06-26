package com.msapps.property.rental.db.mongo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.msapps.property.rental.User;
import com.msapps.property.rental.db.UserDao;

//@Repository
public class UserDaoMongoImpl implements UserDao {

    private UserMongoRepository userRepository;
    MongoTemplate mongoTemplate;

    public UserDaoMongoImpl(MongoTemplate mongoTemplate, UserMongoRepository userRepository) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }
    public Collection<User> getUsers() {
        boolean exists = mongoTemplate.collectionExists("user");
        boolean exists2 = mongoTemplate.collectionExists("transaction");
        boolean exists3 = mongoTemplate.collectionExists("shit");
        return userRepository.findAll().stream().collect(Collectors.toList());
    }
    public User getUser(String name) {
        List<User> users = userRepository.findByName(name);
        return users.get(0);
    }
    public void save(User user) {
        userRepository.save(user);
    }
}
