package one.brainbyte.model;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Account {

    private String accountNumber;
    private LocalDateTime createdAt;
    private List<Operation> transactions = new ArrayList<>();

    @SneakyThrows
    public BigDecimal getBalance(){
        if(transactions == null || transactions.isEmpty()){
            transactions = new ArrayList<>();
        }
        return transactions.stream().map(operation -> operation.getAmount()).reduce(BigDecimal.valueOf(0), (prevBalance, currentBalance) -> currentBalance.add(prevBalance));
    }

    public List<Operation> getTransactions() {
        if(transactions == null)
            transactions = new ArrayList<>();
        return transactions;
    }
}
