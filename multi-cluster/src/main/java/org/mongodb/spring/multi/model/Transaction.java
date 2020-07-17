package org.mongodb.spring.multi.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class Transaction {

    enum Type {
        CONTACTLESS,
        DIRECT_DEBIT,
        TRANSFER,
        VISA
    }

    @MongoId
    private final ObjectId transactionId;
    private final BigDecimal amount;
    private final LocalDateTime date;
    private final String text;
}
