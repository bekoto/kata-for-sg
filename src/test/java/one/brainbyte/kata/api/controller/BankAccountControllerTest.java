package one.brainbyte.kata.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import one.brainbyte.kata.api.dto.AccountOperation;
import one.brainbyte.kata.service.BankService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class BankAccountControllerTest {

    @Autowired
    BankService bankService;

    @Autowired
    private MockMvc mvc;

    private final static String BASE_API = "/api/v1/accounts";
    @Test
    void getAccounts() throws Exception {

        mvc.perform(get(BASE_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAccount() {
    }

    @Test
    void account() {
    }

    @Test
    void addAccountShouldSuccess() throws Exception {
        //String reqBody = new ObjectMapper().writeValueAsString(AccountOperation());

        //Given

        //When
        mvc.perform(post(BASE_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Then
        var bankAccounts = bankService.getAccounts();
        Assertions.assertNotNull(bankAccounts);
        Assertions.assertTrue(bankAccounts.size() > 0);
    }

    @Test
    void getAccountBalance() {

    }

    @Test
    void printAccountStatements() {

    }
}