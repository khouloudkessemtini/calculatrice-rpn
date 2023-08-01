package com.example.calculatrice.domain;


import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stack_calculator")
public class StackCalculator implements Serializable {
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code")
    private Integer code;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Size(min=1, max = 20)
    @NotNull
    @Column(name = "designation")
    public String designation;

    @Size(max = 50)
    @Column(name = "memo")
    public String memo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stackCalculator")
    private List<StackElement> stackElements;

    public StackCalculator() {
    }

    public StackCalculator(Integer code, String designation) {
        this.code = code;
        this.designation = designation;
    }

    @PrePersist
    public void StackCalculator(){
        this.dateCreation =  LocalDateTime.now();
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<StackElement> getStackElements() {
        return stackElements;
    }

    public void setStackElements(List<StackElement> stackElements) {
        this.stackElements = stackElements;
    }
}
