package one.brainbyte.service;


import one.brainbyte.model.DepositOperation;
import one.brainbyte.model.Operation;
import one.brainbyte.model.WithdrawOperation;
import one.brainbyte.exception.AccountNotFoundException;
import one.brainbyte.exception.BadDataException;
import one.brainbyte.exception.DuplicationAccountNumberException;
import one.brainbyte.exception.OperationException;
import one.brainbyte.model.Account;
import one.brainbyte.util.MemoryAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static one.brainbyte.service.BankService.ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE;
import static one.brainbyte.service.BankService.BAD_ACCOUNT_NUMBER_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {

    private BankService bankService = new BankServiceImpl();
    private MemoryAppender memoryAppender;
    Logger logger =     LoggerFactory.getLogger(BankServiceImpl.class);

    @BeforeEach
    void setUp() {

        memoryAppender = new MemoryAppender();
        ((ch.qos.logback.classic.Logger) logger).addAppender(memoryAppender);

    }

    @AfterEach
    void tearDown() {
        bankService.setAccounts(new HashMap());
    }



    @Test
    void depositShouldFailedCauseOfAccountNotFound() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToDeposit= Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = BigDecimal.valueOf(100);

        //when
        //bankService.withdraw(accountToWithdraw, amountToWithdraw);
        //Then

        assertThrows(AccountNotFoundException.class,() -> bankService.deposit(accountToDeposit, amountToWithdraw));

    }


    @Test
    void depositShouldFailedCauseOfAmountIsNotAllowed() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToDeposit= Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = BigDecimal.valueOf(0);

        //when
        bankService.addAccount(account);
        //bankService.withdraw(accountToWithdraw, amountToWithdraw);

        //Then
        OperationException exp = assertThrows(OperationException.class,() -> bankService.deposit(accountToDeposit, amountToWithdraw));
        assertEquals(BankService.NOT_ALLOWED_AMOUNT, exp.getMessage());

    }

    @Test
    void depositShouldFailedCauseOfAccountNumberIsNull() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToDeposit= Account.builder().accountNumber(null).createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = BigDecimal.valueOf(0);

        //when
        bankService.addAccount(account);
        //bankService.withdraw(accountToWithdraw, amountToWithdraw);

        //Then
        OperationException exp = assertThrows(OperationException.class,() -> bankService.deposit(accountToDeposit, amountToWithdraw));
        assertEquals(BAD_ACCOUNT_NUMBER_EXCEPTION_MESSAGE, exp.getMessage());

    }

    @Test
    void depositShouldFailedCauseOfAccountIsNull() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToDeposit= null;
        BigDecimal amountToWithdraw = BigDecimal.valueOf(0);

        //when
        bankService.addAccount(account);
        //bankService.withdraw(accountToWithdraw, amountToWithdraw);

        //Then
        OperationException exp = assertThrows(OperationException.class,() -> bankService.deposit(accountToDeposit, amountToWithdraw));
        assertEquals(BAD_ACCOUNT_NUMBER_EXCEPTION_MESSAGE, exp.getMessage());

    }

    @Test
    void depositShouldSuccess() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToDeposit= Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = BigDecimal.valueOf(1000);

        //when
        bankService.addAccount(account);
        bankService.deposit(accountToDeposit, amountToWithdraw);

        //Then
        BigDecimal currentBalance = bankService.getBalance(account);
        assertNotNull(currentBalance);
        assertEquals(currentBalance, amountToWithdraw);

    }

    @Test
    void addAccountShouldSuccessed() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToDeposit= Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = BigDecimal.valueOf(500);
        BigDecimal amountToDeposit = BigDecimal.valueOf(1000);
        BigDecimal expectedBalance = BigDecimal.valueOf(500);

        //When

            bankService.addAccount(account);
            bankService.deposit(accountToDeposit, amountToDeposit);
            bankService.withdraw(account, amountToWithdraw);

        //then
            Account foundAccount = bankService.getAccount(account);
            assertNotNull(foundAccount);
            assertEquals(account.getAccountNumber(), foundAccount.getAccountNumber());
            assertEquals(account.getCreatedAt(), foundAccount.getCreatedAt());
            assertEquals(account.getBalance(), expectedBalance);

    }

    @Test
    void addAccountShouldThrowException(){

        //Given
        Account account = null;

        //When

        // bankService.addAccount(account) with null account data;

        //then
        assertThrows(BadDataException.class, () -> bankService.addAccount(account));
    }
    @Test
    void addAccountShouldFailedCauseOfUniqueAccountNumber() throws BadDataException, DuplicationAccountNumberException {

        //Given
        Account account1 = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account account2 = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();

        //When

        bankService.addAccount(account1);
        //bankService.addAccount(account2);

        //then
        assertThrows(DuplicationAccountNumberException.class, () -> bankService.addAccount(account2));
    }


    @Test
    void withdrawShouldFailedAmountAllowed() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToWithdraw = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = BigDecimal.valueOf(100);

        //when
        bankService.addAccount(account);
        //bankService.withdraw(accountToWithdraw, amountToWithdraw);
        //Then

        var exception  = assertThrows(OperationException.class,() -> bankService.withdraw(accountToWithdraw, amountToWithdraw));
        assertEquals(WithdrawOperation.OPERATION_NOT_ALLOWED_INSUFFICIENT_BALANCE_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void withdrawShouldFailedAmountLessThanZeroAllowed() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToWithdraw = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = BigDecimal.ZERO;

        //when
        bankService.addAccount(account);
        //bankService.withdraw(accountToWithdraw, amountToWithdraw);
        //Then

        var exception  = assertThrows(OperationException.class,() -> bankService.withdraw(accountToWithdraw, amountToWithdraw));
        assertEquals(WithdrawOperation.OPERATION_NOT_ALLOWED_INSUFFICIENT_BALANCE_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void withdrawShouldFailedCauseOfAccountNotFound() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToWithdraw = Account.builder().accountNumber("00002").createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = BigDecimal.valueOf(100);

        //when
        bankService.addAccount(account);
        //bankService.withdraw(accountToWithdraw, amountToWithdraw);
        //Then

       var exception  = assertThrows(AccountNotFoundException.class,() -> bankService.withdraw(accountToWithdraw, amountToWithdraw));
       assertEquals(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void withdrawShouldFailedCauseOfAmoundIsNullFound() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToWithdraw = Account.builder().accountNumber("00002").createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = null;

        //when
        bankService.addAccount(account);
        //bankService.withdraw(accountToWithdraw, amountToWithdraw);
        //Then

        var exception  = assertThrows(AccountNotFoundException.class,() -> bankService.withdraw(accountToWithdraw, amountToWithdraw));
        assertEquals(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void withdrawShouldFailedCauseOfAccountNumberNotFound() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Account accountToWithdraw = Account.builder().accountNumber(null).createdAt(LocalDateTime.now()).build();
        BigDecimal amountToWithdraw = BigDecimal.valueOf(100);

        //when
        bankService.addAccount(account);
        //bankService.withdraw(accountToWithdraw, amountToWithdraw);
        //Then

        var exception  = assertThrows(OperationException.class,() -> bankService.withdraw(accountToWithdraw, amountToWithdraw));
        assertEquals(BAD_ACCOUNT_NUMBER_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void withdrawShouldSuccessed() throws AccountNotFoundException, OperationException, BadDataException, DuplicationAccountNumberException {
        //Given
            Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
            Operation depositOperation = new DepositOperation(account, BigDecimal.valueOf(1000), LocalDateTime.now());
            depositOperation.apply();
            List<Operation> operations = new ArrayList<>();
            operations.add(depositOperation);
            account.setTransactions(operations);
            BigDecimal amountToWithdraw = BigDecimal.valueOf(100);
            BigDecimal  expectedBalance = BigDecimal.valueOf(900);

        //when
            bankService.addAccount(account);
            bankService.withdraw(account, amountToWithdraw);
        //Then

            BigDecimal currentBalance = bankService.getBalance(account);
            assertNotNull(currentBalance);
            assertEquals(expectedBalance, currentBalance);

    }

    @Test
    void getBalanceShouldFailedCauseAccountNumberIsNull() {
        //Given
        String accountNumber = null;

        //When
        //Account foundAccount = bankService.getAccount(Account.builder().accountNumber(accountNumber).build());

        //then
        OperationException exp = assertThrows(OperationException.class, ()-> bankService.getAccount(Account.builder().accountNumber(accountNumber).build()));
        assertEquals(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE, exp.getMessage());


    }


    @Test
    void getAccountShouldFailedCauseAccountNotFound() throws AccountNotFoundException, OperationException {
        //Given
        String accountNumber = "00001";

        //When
        //Account foundAccount = bankService.getAccount(Account.builder().accountNumber(accountNumber).build());

        //then
        assertThrows(AccountNotFoundException.class, ()-> bankService.getAccount(Account.builder().accountNumber(accountNumber).build()));
    }

    @Test
    void printOperations() throws OperationException, BadDataException, DuplicationAccountNumberException, AccountNotFoundException {
        memoryAppender.start();
        //Given
        Account account = Account.builder().accountNumber("00001").createdAt(LocalDateTime.now()).build();
        Operation depositOperation = new DepositOperation(account, BigDecimal.valueOf(1000), LocalDateTime.now());
        depositOperation.apply();
        List<Operation> operations = new ArrayList<>();
        operations.add(depositOperation);
        account.setTransactions(operations);
        BigDecimal amountToWithdraw = BigDecimal.valueOf(100);
        BigDecimal  expectedBalance = BigDecimal.valueOf(900);

        //when
        bankService.addAccount(account);
        bankService.withdraw(account, amountToWithdraw);
        //Then

        BigDecimal currentBalance = bankService.getBalance(account);
        assertNotNull(currentBalance);
        assertEquals(expectedBalance, currentBalance);
        bankService.printAccountOperations(account);
        String logOutput = memoryAppender.getLogOutput();

        assertTrue(logOutput.contains("history (operation, date, amount, balance)"));
        assertTrue(logOutput.contains(expectedBalance.toString()));
        // Clean up the appender
        ((ch.qos.logback.classic.Logger) logger).detachAppender(memoryAppender);
    }

}