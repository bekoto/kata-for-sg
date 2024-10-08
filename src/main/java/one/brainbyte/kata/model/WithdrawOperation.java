package one.brainbyte.kata.model;

import lombok.RequiredArgsConstructor;
import one.brainbyte.kata.exception.OperationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class WithdrawOperation implements Operation {

    public static final String OPERATION_NOT_ALLOWED_INSUFFICIENT_BALANCE_EXCEPTION_MESSAGE = "Operation not allowed, insufficient balance";
    private final Account account;
    private final BigDecimal amount;
    private final LocalDateTime dateOperation;
    private BigDecimal balance;


    @Override
    public BigDecimal apply() throws OperationException {

        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new OperationException("Amount must be greater than zero");
        }
        BigDecimal balance = account.getBalance();
        boolean isDeposit = amount.compareTo(balance) < 0;

        if (!isDeposit) {
            throw new OperationException(OPERATION_NOT_ALLOWED_INSUFFICIENT_BALANCE_EXCEPTION_MESSAGE);
        }
        BigDecimal newBalence =  balance.subtract(amount);
        account.getTransactions().add(this);
        this.balance = newBalence;
        return newBalence;
    }

    @Override
    public BigDecimal getAmount() {
        return this.amount.negate();
    }

    @Override
    public LocalDateTime getDateOperation() {
        return dateOperation;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    public String toString(){
        return "Withdraw";
    }
}
