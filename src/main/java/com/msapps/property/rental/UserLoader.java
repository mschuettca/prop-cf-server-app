package com.msapps.property.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.msapps.property.rental.User.PaymentType;
import com.msapps.property.rental.db.mongo.UserMongoRepository;
import com.msapps.property.rental.db.sql.UserSqlRepository;

@Component
public class UserLoader implements CommandLineRunner {
//    private final UserMongoRepository repository;
    private final UserSqlRepository repository;

    @Autowired
    public UserLoader(UserSqlRepository repository) {
//        public UserLoader(UserMongoRepository repository) {
        this.repository = repository;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading Users...");
      //  this.repository.save(new User("Sarah", 0.5, PaymentType.BiMonthly, 10, 1000.00, "Sarah 50% share, pays once every 2 months"));
      //  this.repository.save(new User("Judith", 0.25, PaymentType.Monthly, 10, 500.00, "Judith 25% share, pay every month"));
      //  this.repository.findAll().forEach(System.out::println);
    }
}
