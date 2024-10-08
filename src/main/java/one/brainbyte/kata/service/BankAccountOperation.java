package one.brainbyte.kata.service;

import lombok.RequiredArgsConstructor;
import one.brainbyte.kata.api.dto.AccountOperation;
import one.brainbyte.kata.api.mapper.AccountMapper;
import one.brainbyte.kata.exception.AccountNotFoundException;
import one.brainbyte.kata.exception.OperationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BankAccountOperation {

    final private BankService bankService;

    public void doOperationOf(AccountOperation accountOperation) throws AccountNotFoundException, OperationException {
        switch (accountOperation.getOperationType()){
            case DEPOSIT ->
                    bankService.deposit(AccountMapper.toAccount(accountOperation),(accountOperation.getAmount()));
            case WITHDRAW ->
                    bankService.withdraw(AccountMapper.toAccount(accountOperation), (accountOperation.getAmount()));
        }
    }
}
