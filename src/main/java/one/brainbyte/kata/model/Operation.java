package one.brainbyte.kata.model;

import one.brainbyte.kata.exception.OperationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Operation {

    BigDecimal apply() throws OperationException;
    BigDecimal getAmount();
    LocalDateTime getDateOperation();
    BigDecimal getBalance();


}
