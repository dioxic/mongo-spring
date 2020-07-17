package org.mongodb.spring.multi;

import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mongodb.spring.multi.config.Cluster1MongoConfig;
import org.mongodb.spring.multi.config.Cluster2MongoConfig;
import org.mongodb.spring.multi.model.Account;
import org.mongodb.spring.multi.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataMongoTest(excludeAutoConfiguration = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@Import({Cluster1MongoConfig.class, Cluster2MongoConfig.class})
public class MongoTemplateTest {

    @Autowired
    @Qualifier(Cluster1MongoConfig.MONGO_TEMPLATE)
    private MongoTemplate template1;

    @Autowired
    @Qualifier(Cluster2MongoConfig.MONGO_TEMPLATE)
    private MongoTemplate template2;

    @BeforeEach
    void beforeEach() {
        template1.dropCollection(Account.class);
        template2.dropCollection(Transaction.class);
    }

    @Test
    void insertAccount() {
        Account account = Account.builder()
                .accountId(1)
                .balance(BigDecimal.TEN)
                .type(Account.Type.CHECKING)
                .build();

        template1.insert(account);

        Assertions.assertThat(template1.findById(account.getAccountId(), Account.class))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    void insertTransaction() {
        Transaction transaction = Transaction.builder()
                .transactionId(ObjectId.get())
                .date(LocalDateTime.of(2020, 7, 10, 1, 2, 3))
                .text("some transaction")
                .amount(BigDecimal.TEN)
                .build();

        template2.insert(transaction);

        Assertions.assertThat(template2.findById(transaction.getTransactionId(), Transaction.class))
                .isEqualToComparingFieldByField(transaction);
    }

}
