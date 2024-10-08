package one.brainbyte.kata.api.controller;

import lombok.RequiredArgsConstructor;
import one.brainbyte.kata.api.dto.AccountOperation;
import one.brainbyte.kata.api.dto.AccountBalanceStatement;
import one.brainbyte.kata.api.dto.AccountStatement;
import one.brainbyte.kata.api.mapper.AccountMapper;
import one.brainbyte.kata.exception.AccountNotFoundException;
import one.brainbyte.kata.exception.BadDataException;
import one.brainbyte.kata.exception.DuplicationAccountNumberException;
import one.brainbyte.kata.exception.OperationException;
import one.brainbyte.kata.model.Account;
import one.brainbyte.kata.service.BankAccountOperation;
import one.brainbyte.kata.service.BankService;
import one.brainbyte.kata.util.AccountNumberGenerator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BankAccountController.BASE_URL)
public class BankAccountController {


    private final BankService bankService;
    private final BankAccountOperation bankAccountOperation;
    public static final String BASE_URL = "/api/v1/accounts";
    public static final String ACCOUNT_PATH ="/{accountNumber}";
    public static final String ACCOUNT_NUMBER_BALANCE_PATH = "/{accountNumber}/statement";
    public static final String ACCOUNT_NUMBER_STATEMENTS_PATH = "/{accountNumber}/statements";

    @GetMapping("")
    List<Account> getAccounts() throws AccountNotFoundException, OperationException {
        return  bankService.getAccounts();
    }

    @GetMapping(ACCOUNT_PATH)
    Account getAccount(@PathVariable("accountNumber") String accountNumber) throws AccountNotFoundException, OperationException {
        return  bankService.getAccount(Account.builder().accountNumber(accountNumber).build());
    }

    @PatchMapping(ACCOUNT_PATH)
    void Account(@RequestBody @Validated AccountOperation accountOperation) throws AccountNotFoundException, OperationException {
        bankAccountOperation.doOperationOf(accountOperation);
    }

    @PostMapping("")
    void addAccount() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        bankService.addAccount(Account.builder().createdAt(LocalDateTime.now()).accountNumber(AccountNumberGenerator.generateAccountNumber()).build());
    }

    @GetMapping(ACCOUNT_NUMBER_BALANCE_PATH)
    AccountBalanceStatement getAccountBalance(@PathVariable("accountNumber") String accountNumber) throws AccountNotFoundException, OperationException {
           Account account = bankService.getAccount(Account.builder().accountNumber(accountNumber).build());
        var operations = account.getTransactions();
        return AccountMapper.toStatement(account, operations);
    }

    @GetMapping(ACCOUNT_NUMBER_STATEMENTS_PATH)
    AccountStatement printAccountStatements(@PathVariable("accountNumber") String accountNumber) throws AccountNotFoundException, OperationException {
        Account account = bankService.getAccount(Account.builder().accountNumber(accountNumber).build());
        var operations = account.getTransactions();
        return AccountMapper.printState(account, operations);
    }

}
