package one.brainbyte.kata.service;

import one.brainbyte.kata.exception.AccountNotFoundException;
import one.brainbyte.kata.exception.BadDataException;
import one.brainbyte.kata.exception.DuplicationAccountNumberException;
import one.brainbyte.kata.model.Account;
import one.brainbyte.kata.exception.OperationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BankService {

    String NOT_ALLOWED_AMOUNT = "Amount must be greater than zero";
    String ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE = "Account not found";
    String BAD_ACCOUNT_NUMBER_EXCEPTION_MESSAGE = "Bad account number";
    String INVALID_AMOUNT_EXCEPTION_MESSAGE = "Invalid amount";
    String INVALID_ACCOUNT_DATA_EXCEPTION_MESSAGE = "account should not be null or AccountNumber should not be empty";
    String ACCOUNT_NUMBER_ALREADY_EXISTS_EXCEPTION_MESSAGE = "account number already exists";
    void deposit(Account account, BigDecimal amount) throws AccountNotFoundException, OperationException;
    void withdraw(Account account, BigDecimal bigDecimal) throws AccountNotFoundException,OperationException;
    BigDecimal getBalance(Account account) throws AccountNotFoundException,OperationException;
    Account getAccount(Account account) throws AccountNotFoundException, OperationException;
    void addAccount(Account account) throws BadDataException, DuplicationAccountNumberException;
    List<Account> getAccounts();
    void setAccounts(Map<String, Account> accounts);
    void printAccountOperations(Account account) throws AccountNotFoundException, OperationException;

}
