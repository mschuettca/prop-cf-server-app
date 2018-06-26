package com.msapps.property.rental.db;

import java.util.Collection;

import com.msapps.property.rental.UserPayment;

public interface UserPaymentDao {
    public Collection<UserPayment> getUserPayments(String name);
    public double sumUserPayments(String name);
    void save(UserPayment userPayment);

}
