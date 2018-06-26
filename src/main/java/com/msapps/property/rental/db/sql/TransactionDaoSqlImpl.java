package com.msapps.property.rental.db.sql;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.msapps.property.rental.Transaction;
import com.msapps.property.rental.TransactionCostSum;
import com.msapps.property.rental.db.TransactionDao;

//@Repository
public class TransactionDaoSqlImpl implements TransactionDao {

    private TransactionSqlRepository transRepository;

    public TransactionDaoSqlImpl(TransactionSqlRepository transRepository) {
        this.transRepository = transRepository;
    }

    public Collection<Transaction> findByDateBetweenOrderByDateDesc(Date start, Date end) {
        return transRepository.findByDateBetweenOrderByDateDesc(start, end).stream()
                .collect(Collectors.toList());
    }
    public Collection<Transaction> findByDateBetweenOrderByDateAsc(Date start, Date end) {
        return transRepository.findByDateBetweenOrderByDateAsc(start, end).stream()
                .collect(Collectors.toList());
    }
    public void save(Transaction transaction) {
        transRepository.save(transaction);
    }
    @Override
    public Collection<TransactionCostSum> sum() {
        double sumCredits = 0;
        double sumDebits = transRepository.sum();
        ArrayList<TransactionCostSum> list = new ArrayList<>();
        
        TransactionCostSum sumCredit = new TransactionCostSum();
        sumCredit.set_id("Credit");
        sumCredit.setTotal(sumCredits);
        list.add(sumCredit);

        TransactionCostSum sumDebit = new TransactionCostSum();
        sumDebit.set_id("Debit");
        sumDebit.setTotal(sumDebits);
        list.add(sumDebit);
        return list;
    }
}
