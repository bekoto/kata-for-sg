package one.brainbyte.model;

import one.brainbyte.exception.OperationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Operation {

    BigDecimal apply() throws OperationException;
    BigDecimal getAmount();
    LocalDateTime getDateOperation();
    BigDecimal getBalance();


}
