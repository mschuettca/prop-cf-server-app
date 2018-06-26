package com.msapps.property.rental.db.mongo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.msapps.property.rental.UserPayment;
import com.msapps.property.rental.db.UserPaymentDao;

public class UserPaymentDaoMongoImpl implements UserPaymentDao {

    private UserPaymentMongoRepository userPaymentRepository;
    MongoTemplate mongoTemplate;

    public UserPaymentDaoMongoImpl(MongoTemplate mongoTemplate, UserPaymentMongoRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
        this.mongoTemplate = mongoTemplate;
    }
    public Collection<UserPayment> getUserPayments(String name) {
        List<UserPayment> userPayments = userPaymentRepository.findByName(name);
        return userPayments.stream().collect(Collectors.toList());
    }
    @Override
    public double sumUserPayments(String name) {
        //Use the same method as for TransactionCostSum for Mongo
        return 0.0;
    }
    public void save(UserPayment user) {
        userPaymentRepository.save(user);
    }

}
