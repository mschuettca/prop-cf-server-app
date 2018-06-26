package com.msapps.property.rental;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.msapps.property.rental.Transaction.Category;
import com.msapps.property.rental.Transaction.Type;
import com.msapps.property.rental.db.sql.TransactionSqlRepository;

@Component
public class TransactionLoader implements CommandLineRunner {
    private final TransactionSqlRepository repository;

@Autowired
    public TransactionLoader(TransactionSqlRepository repository) {
        this.repository = repository;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading Transactions...");
        //TODO start
  //      this.repository.save(new Transaction(6005.70, Type.Debit, Category.Mortgage, "TMS", "Initial Mortgage Payment for Jan/Feb/March"));
  //      this.repository.save(new Transaction(95.10, Type.Debit, Category.GarbageService, "Gilton Waste Mgmt", "Garbage services all units (March)"));
  //      this.repository.save(new Transaction(605.32, Type.Debit, Category.WaterService, "City of Modesto", "Water service all units - Jan/Feb/March"));
        //TODO end
        this.repository.findAll().forEach(System.out::println);
    }
}
