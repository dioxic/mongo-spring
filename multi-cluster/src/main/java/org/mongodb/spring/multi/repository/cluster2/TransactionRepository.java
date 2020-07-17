package org.mongodb.spring.multi.repository.cluster2;

import org.bson.types.ObjectId;
import org.mongodb.spring.multi.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, ObjectId>  {
}
