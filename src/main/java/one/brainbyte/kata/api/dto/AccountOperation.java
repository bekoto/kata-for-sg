package one.brainbyte.kata.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import one.brainbyte.kata.model.OperationType;

import java.math.BigDecimal;


@Data
@Builder
public class AccountOperation {
    @NotNull
    @NotEmpty
    private String accountNumber;
    @Positive
    private BigDecimal amount;
    @NotNull
    private OperationType operationType ;

}
