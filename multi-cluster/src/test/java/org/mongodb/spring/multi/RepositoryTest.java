package org.mongodb.spring.multi;

import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mongodb.spring.multi.config.Cluster1MongoConfig;
import org.mongodb.spring.multi.config.Cluster2MongoConfig;
import org.mongodb.spring.multi.model.Account;
import org.mongodb.spring.multi.model.Account.Type;
import org.mongodb.spring.multi.model.Transaction;
import org.mongodb.spring.multi.repository.cluster1.AccountRepository;
import org.mongodb.spring.multi.repository.cluster2.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataMongoTest(excludeAutoConfiguration = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@Import({Cluster1MongoConfig.class, Cluster2MongoConfig.class})
public class RepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void beforeEach() {
        accountRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    @Test
    void insertAccount() {
        Account account = Account.builder()
                .accountId(1)
                .balance(BigDecimal.TEN)
                .type(Type.CHECKING)
                .build();

        accountRepository.insert(account);

        Assertions.assertThat(accountRepository.findById(account.getAccountId()))
                .get()
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

        transactionRepository.insert(transaction);

        Assertions.assertThat(transactionRepository.findById(transaction.getTransactionId()))
                .get()
                .isEqualToComparingFieldByField(transaction);
    }

}
