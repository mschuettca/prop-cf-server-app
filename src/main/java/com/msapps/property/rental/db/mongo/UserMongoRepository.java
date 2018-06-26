package com.msapps.property.rental.db.mongo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.msapps.property.rental.User;

@RepositoryRestResource
public interface UserMongoRepository extends MongoRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    List<User> findByName(String name);

}
