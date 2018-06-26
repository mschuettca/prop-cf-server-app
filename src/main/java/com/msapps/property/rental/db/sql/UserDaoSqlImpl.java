package com.msapps.property.rental.db.sql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.msapps.property.rental.Transaction;
import com.msapps.property.rental.User;
import com.msapps.property.rental.db.UserDao;

//@Repository
public class UserDaoSqlImpl implements UserDao {

    private UserSqlRepository userRepository;

    public UserDaoSqlImpl(UserSqlRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Collection<User> getUsers() {
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
