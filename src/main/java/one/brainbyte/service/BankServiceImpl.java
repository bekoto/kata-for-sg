package one.brainbyte.service;

import one.brainbyte.model.DepositOperation;
import one.brainbyte.model.Operation;
import one.brainbyte.model.WithdrawOperation;
import one.brainbyte.exception.AccountNotFoundException;
import one.brainbyte.exception.BadDataException;
import one.brainbyte.exception.DuplicationAccountNumberException;
import one.brainbyte.exception.OperationException;
import one.brainbyte.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BankServiceImpl implements BankService{

    Logger log = LoggerFactory.getLogger(BankServiceImpl.class);
    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public void deposit(Account account, BigDecimal amount) throws AccountNotFoundException, OperationException {
        if(account == null || account.getAccountNumber() == null){
            throw new OperationException(BAD_ACCOUNT_NUMBER_EXCEPTION_MESSAGE);
        }

        Account foundAccount = accounts.get(account.getAccountNumber());
        if(foundAccount == null){
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new OperationException(NOT_ALLOWED_AMOUNT);
        }

        Operation withdraw = new DepositOperation(foundAccount, amount, LocalDateTime.now());
        withdraw.apply();

    }

    @Override
    public void withdraw(Account account, BigDecimal bigDecimal) throws AccountNotFoundException, OperationException {
        if(account == null || account.getAccountNumber() == null){
            throw new OperationException(BAD_ACCOUNT_NUMBER_EXCEPTION_MESSAGE);
        }
        if(bigDecimal == null){
            new OperationException(INVALID_AMOUNT_EXCEPTION_MESSAGE);
        }
        Account foundAccount = accounts.get(account.getAccountNumber());
        if(foundAccount == null){
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        Operation withdraw = new WithdrawOperation(foundAccount, bigDecimal, LocalDateTime.now());
        withdraw.apply();

    }

    @Override
    public BigDecimal getBalance(Account account) throws AccountNotFoundException, OperationException {
        if(account == null || account.getAccountNumber() == null){
            throw new OperationException(BAD_ACCOUNT_NUMBER_EXCEPTION_MESSAGE);
        }
        Account foundAccount = getAccount(account);
        return foundAccount.getBalance();
    }

    @Override
    public Account getAccount(Account account) throws AccountNotFoundException, OperationException {
        if(account == null || account.getAccountNumber() == null){
            throw new OperationException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        if(!accounts.containsKey(account.getAccountNumber()))
            throw new AccountNotFoundException("Account with {} not found".formatted(account.getAccountNumber()));
        return accounts.get(account.getAccountNumber());
    }

    @Override
    public void addAccount(Account account) throws BadDataException, DuplicationAccountNumberException {
        if(account == null || account.getAccountNumber() == null ||account.getAccountNumber().isEmpty()) {
            throw new BadDataException(INVALID_ACCOUNT_DATA_EXCEPTION_MESSAGE);
        }
        if(accounts.containsKey(account.getAccountNumber())) {
            throw new DuplicationAccountNumberException(ACCOUNT_NUMBER_ALREADY_EXISTS_EXCEPTION_MESSAGE);
        }
        accounts.put(account.getAccountNumber(), account);
    }

    @Override
    public List<Account> getAccounts() {
        return  accounts.values().stream().toList();
    }

    @Override
    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void printAccountOperations(Account account) throws AccountNotFoundException, OperationException {
        if(account == null || account.getAccountNumber() == null ||account.getAccountNumber().isEmpty()) {
            throw new OperationException(INVALID_ACCOUNT_DATA_EXCEPTION_MESSAGE);
        }

        log.info("history (operation, date, amount, balance)");
        log.info("-------------------------------------------");
        account.getTransactions().forEach(operation -> {
            log.info(" ({}, {}, {}, {})", operation.toString(), operation.getDateOperation(), operation.getAmount(), operation.getBalance());
        });

    }
}
