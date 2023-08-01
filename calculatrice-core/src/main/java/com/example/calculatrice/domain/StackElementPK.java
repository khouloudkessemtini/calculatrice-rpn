package com.example.calculatrice.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
public class StackElementPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "code_stack_calculator")
    private Integer codeStackCalculator;

    @Basic(optional = false)
    @NotNull
    @Column(name = "order_value")
    private Integer orderValue;

    public StackElementPK() {

    }

    public StackElementPK(Integer codeStackCalculator, Integer orderValue) {
        this.codeStackCalculator = codeStackCalculator;
        this.orderValue = orderValue;
    }

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
}
