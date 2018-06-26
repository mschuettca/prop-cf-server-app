package com.msapps.property.rental.db.mongo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.msapps.property.rental.UserPayment;

@RepositoryRestResource
public interface UserPaymentMongoRepository extends MongoRepository<UserPayment, Long>, PagingAndSortingRepository<UserPayment, Long> {
    List<UserPayment> findByName(String name);
    List<UserPayment> findByDateBetween(Date start, Date end);
   
}
