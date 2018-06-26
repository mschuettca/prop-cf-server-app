package com.msapps.property.rental.db.mongo;

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
public interface TransactionMongoRepository extends MongoRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> findByDateBetweenOrderByDateDesc(Date start, Date end);
    List<Transaction> findByDateBetweenOrderByDateAsc(Date start, Date end);
        
 /*   @Query(value = "aggregate({$group:{_id:'',cost:{$sum:'$cost'}}})", nativeQuery = true)
    public String sum();*/
}
