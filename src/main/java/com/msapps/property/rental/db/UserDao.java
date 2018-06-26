package com.msapps.property.rental.db;

import java.util.Collection;

import com.msapps.property.rental.Transaction;
import com.msapps.property.rental.User;

public interface UserDao {
    public Collection<User> getUsers();
    public User getUser(String name);    
    void save(User user);
}
