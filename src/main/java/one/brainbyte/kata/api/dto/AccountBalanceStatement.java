package one.brainbyte.kata.api.dto;

import java.math.BigDecimal;

public record AccountBalanceStatement(String accountNumber, BigDecimal amount, BigDecimal balance){

}
