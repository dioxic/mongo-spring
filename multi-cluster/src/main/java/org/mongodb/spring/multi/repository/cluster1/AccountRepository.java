package org.mongodb.spring.multi.repository.cluster1;

import org.mongodb.spring.multi.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, Integer> {
}
