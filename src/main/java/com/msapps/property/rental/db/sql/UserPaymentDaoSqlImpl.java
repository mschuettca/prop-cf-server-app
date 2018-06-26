package com.msapps.property.rental.db.sql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.msapps.property.rental.UserPayment;
import com.msapps.property.rental.db.UserPaymentDao;

public class UserPaymentDaoSqlImpl implements UserPaymentDao {
    private UserPaymentSqlRepository userPaymentRepository;

    public UserPaymentDaoSqlImpl(UserPaymentSqlRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }
    public Collection<UserPayment> getUserPayments(String name) {
        List<UserPayment> userPayments = userPaymentRepository.findByName(name);
        return userPayments.stream().collect(Collectors.toList());
    }
    @Override
    public double sumUserPayments(String name) {
        //Use the same method as for TransactionCostSum for Sql
        return 0.0;
    }
    public void save(UserPayment user) {
        userPaymentRepository.save(user);
    }

}
