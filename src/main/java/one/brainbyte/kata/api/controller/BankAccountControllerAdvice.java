package one.brainbyte.kata.api.controller;

import lombok.extern.slf4j.Slf4j;
import one.brainbyte.kata.api.dto.ApiError;
import one.brainbyte.kata.exception.AccountNotFoundException;
import one.brainbyte.kata.exception.BadDataException;
import one.brainbyte.kata.exception.DuplicationAccountNumberException;
import one.brainbyte.kata.exception.OperationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BankAccountControllerAdvice {

    @ExceptionHandler({AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError accountNotFound(AccountNotFoundException e) {
        return ApiError.builder().message(e.getMessage()).status(HttpStatus.NOT_FOUND.value()+"").build();
    }

    @ExceptionHandler({OperationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiError handleInternalError(OperationException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder().message("an error is happened, please try log").status(HttpStatus.INTERNAL_SERVER_ERROR.value()+"").build();
    }

    @ExceptionHandler({BadDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleBadRequest(OperationException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder().message("an error is happened, please try log").status(HttpStatus.BAD_REQUEST.value()+"").build();
    }

    @ExceptionHandler({DuplicationAccountNumberException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleDuplicationAccountNumberRequest(DuplicationAccountNumberException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder().message("account number already exists").status(HttpStatus.BAD_REQUEST.value()+"").build();
    }
}
