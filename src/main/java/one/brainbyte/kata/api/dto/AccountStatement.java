package one.brainbyte.kata.api.dto;

import lombok.Builder;
import lombok.Data;
import one.brainbyte.kata.model.Operation;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class AccountStatement {
    private String accountNumber;
    private List<Operation> operations = new ArrayList<>();

}
