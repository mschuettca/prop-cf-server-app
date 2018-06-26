package com.msapps.property.rental.db.mongo;

import java.sql.Date;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import com.msapps.property.rental.Transaction;
import com.msapps.property.rental.TransactionCostSum;
import com.msapps.property.rental.db.TransactionDao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

//@Repository
public class TransactionDaoMongoImpl implements TransactionDao {

    private TransactionMongoRepository transRepository;
    MongoTemplate mongoTemplate;

    public TransactionDaoMongoImpl(MongoTemplate mongoTemplate, TransactionMongoRepository transRepository) {
        this.transRepository = transRepository;
        this.mongoTemplate = mongoTemplate;
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
     /*   Aggregation agg = newAggregation(
                match(Criteria.where("_id").lt(10)),
                group("hosting").count().as("total"),
                project("total").and("hosting").previousOperation(),
                sort(Sort.Direction.DESC, "total")
                    
            );*/

        /*   (value = "aggregate({$group:{_id:'',cost:{$sum:'$cost'}}})", nativeQuery = true)
        public String sum();*/
        Aggregation agg = Aggregation.newAggregation(group("type").sum("cost").as("total"));
        AggregationResults<TransactionCostSum> groupResults = 
                mongoTemplate.aggregate(agg, Transaction.class, TransactionCostSum.class);
        List<TransactionCostSum> result = groupResults.getMappedResults(); 
        result.forEach(System.out::println);
        return result;
    }
}
