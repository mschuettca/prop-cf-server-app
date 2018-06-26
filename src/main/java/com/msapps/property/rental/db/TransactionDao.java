package com.msapps.property.rental.db;

import java.sql.Date;
import java.util.Collection;

import com.msapps.property.rental.Transaction;
import com.msapps.property.rental.TransactionCostSum;

public interface TransactionDao {
    Collection<Transaction> findByDateBetweenOrderByDateDesc(Date start, Date end);
    Collection<Transaction> findByDateBetweenOrderByDateAsc(Date start, Date end);
    void save(Transaction transaction);
    Collection<TransactionCostSum> sum();
   
}
