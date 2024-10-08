package one.brainbyte.kata.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



public enum OperationType {

    WITHDRAW("WITHDRAW"), DEPOSIT("DEPOSIT");

    OperationType(String type){
        this.typeOperation = type;
    }
    private String typeOperation;
    @JsonValue
    public String getTypeOperation() {
        return typeOperation;
    }
    @JsonCreator
    public OperationType forValue(String typeOperation) {
        return OperationType.valueOf(typeOperation);
    }
}
