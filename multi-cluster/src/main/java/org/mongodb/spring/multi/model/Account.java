package org.mongodb.spring.multi.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
public class Account {

    public enum Type {
        CHECKING,
        SAVINGS,
        ISA
    }

    @MongoId
    private final Integer accountId;
    private final Type type;
    private final BigDecimal balance;

}
