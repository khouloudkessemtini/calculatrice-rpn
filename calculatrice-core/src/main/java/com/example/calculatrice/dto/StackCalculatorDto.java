package com.example.calculatrice.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class StackCalculatorDto {

    private Integer code;
    private LocalDateTime dateCreation;

    @Size(min=1, max = 20)
    @NotNull
    public String designation;

    @Size(max = 50)
    public String memo;

    private List<StackElementDto> stackElements;

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

    public List<StackElementDto> getStackElements() {
        return stackElements;
    }

    public void setStackElements(List<StackElementDto> stackElements) {
        this.stackElements = stackElements;
    }
}
