package com.example.calculatrice.dto;


import jakarta.validation.constraints.Size;

public class StackElementDto {

    private Integer codeStackCalculator;

    private Integer orderValue;

    @Size(min = 1)
    private Integer value;

    public Integer getCodeStackCalculator() {
        return codeStackCalculator;
    }

    public void setCodeStackCalculator(Integer codeStackCalculator) {
        this.codeStackCalculator = codeStackCalculator;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
