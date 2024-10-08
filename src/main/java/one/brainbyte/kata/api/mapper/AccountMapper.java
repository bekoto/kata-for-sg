package one.brainbyte.kata.api.mapper;

import one.brainbyte.kata.api.dto.AccountOperation;
import one.brainbyte.kata.api.dto.AccountBalanceStatement;
import one.brainbyte.kata.api.dto.AccountStatement;
import one.brainbyte.kata.model.Account;
import one.brainbyte.kata.model.Operation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AccountMapper {

    public static Account toAccount(AccountOperation accountOperation) {
        return Account.builder().accountNumber(accountOperation.getAccountNumber()).build();
    }

    public static AccountBalanceStatement toStatement(Account account, List<Operation> operations) {
        Optional<Operation> lastOperation;
        if(operations != null && !operations.isEmpty()) {
            lastOperation =  Optional.ofNullable(operations.get(operations.size() - 1));
            return new AccountBalanceStatement(account.getAccountNumber(), lastOperation.get().getAmount(), lastOperation.get().getBalance());
        }
        return new AccountBalanceStatement(account.getAccountNumber(), BigDecimal.ZERO, BigDecimal.ZERO);

    }
    public static AccountStatement printState(Account account, List<Operation> operations) {
        return  AccountStatement.builder().accountNumber(account.getAccountNumber()).operations(operations).build();
    }

}
