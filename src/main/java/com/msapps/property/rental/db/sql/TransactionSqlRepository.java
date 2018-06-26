package com.msapps.property.rental.db.sql;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.msapps.property.rental.Transaction;

@RepositoryRestResource
public interface TransactionSqlRepository extends JpaRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> findByDateBetweenOrderByDateDesc(Date start, Date end);
    List<Transaction> findByDateBetweenOrderByDateAsc(Date start, Date end);
    
    //    @Query(value = "SELECT SUM(cost) FROM TRANSACTION WHERE date BETWEEN TO_DATE('2018-04-03', 'YYYY-MM-DD') AND TO_DATE('2018-04-03', 'YYYY-MM-DD')",

@Query(value = "SELECT SUM(cost) FROM TRANSACTION",
       nativeQuery = true)
public double sum();
    
 /*   @Query(value = "aggregate({$group:{_id:'',cost:{$sum:'$cost'}}})", nativeQuery = true)
    public String sum();*/
}
