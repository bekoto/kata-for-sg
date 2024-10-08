package one.brainbyte.model;

import lombok.RequiredArgsConstructor;
import one.brainbyte.exception.OperationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class DepositOperation implements Operation {

    public static final String AMOUNT_MUST_BE_GREATER_THAN_ZERO_EXCEPTION_MESSAGE = "Amount must be greater than zero";
    private final Account account;
    private final BigDecimal amount;
    private final LocalDateTime dateOperation;
    private BigDecimal balance;


    @Override
    public BigDecimal apply() throws OperationException {

        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new OperationException(AMOUNT_MUST_BE_GREATER_THAN_ZERO_EXCEPTION_MESSAGE);
        }

        BigDecimal balance = account.getBalance();
        balance.add(amount);
        account.getTransactions().add(this);
        BigDecimal newBalence = account.getBalance();
        this.balance = newBalence;
        return newBalence;
    }

    @Override
    public BigDecimal getAmount() {
        return (this.amount);
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
        return "Deposit";
    }
}
