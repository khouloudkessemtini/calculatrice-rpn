package com.example.calculatrice.domain;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "stack_element")
public class StackElement implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private StackElementPK stackElementPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private Integer value;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "code_stack_calculator", referencedColumnName = "code", insertable=false, updatable=false)
    private StackCalculator stackCalculator;

    public StackElement() {
    }

    public StackElement(StackElementPK stackElementPK, Integer value) {
        this.stackElementPK = stackElementPK;
        this.value = value;
    }

    public StackElementPK getStackElementPK() {
        return stackElementPK;
    }

    public void setStackElementPK(StackElementPK stackElementPK) {
        this.stackElementPK = stackElementPK;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public StackCalculator getStackCalculator() {
        return stackCalculator;
    }

    public void setStackCalculator(StackCalculator stackCalculator) {
        this.stackCalculator = stackCalculator;
    }
}
