package com.example.calculatrice.factory;

import com.example.calculatrice.domain.StackCalculator;
import com.example.calculatrice.domain.StackElement;
import com.example.calculatrice.dto.StackCalculatorDto;
import com.example.calculatrice.dto.StackElementDto;

import java.util.ArrayList;
import java.util.List;

public class StackFactory {
    public static StackCalculator stackCalculatorDtoToStackCalculator(StackCalculatorDto stackCalculatorDto) {
        StackCalculator stackCalculator= new StackCalculator();
        stackCalculator.setDesignation(stackCalculatorDto.getDesignation());
        stackCalculator.setMemo(stackCalculatorDto.getMemo());
        return stackCalculator;
    }
    public static StackCalculatorDto stackCalculatorToStackCalculatorDtoLazy(StackCalculator stackCalculator) {
        StackCalculatorDto stackCalculatorDto = new StackCalculatorDto();
        stackCalculatorDto.setCode(stackCalculator.getCode());
        stackCalculatorDto.setDateCreation(stackCalculator.getDateCreation());
        stackCalculatorDto.setDesignation(stackCalculator.getDesignation());
        stackCalculatorDto.setMemo(stackCalculator.getMemo());

        return stackCalculatorDto;
    }
    public static StackCalculatorDto stackCalculatorToStackCalculatorDto(StackCalculator stackCalculator) {
        StackCalculatorDto stackCalculatorDto = stackCalculatorToStackCalculatorDtoLazy(stackCalculator);
        if(stackCalculator.getStackElements() != null && !stackCalculator.getStackElements().isEmpty())
        stackCalculatorDto.setStackElements(stackElementsToStackElementsDto(stackCalculator.getStackElements()));

        return stackCalculatorDto;
    }
    public static List<StackCalculatorDto> stackCalculatorListToStackCalculatorDtoList(List<StackCalculator> stackCalculators) {
        List<StackCalculatorDto> stackCalculatorDtos = new ArrayList<>();
        stackCalculators.forEach( stackCalculator -> {
            stackCalculatorDtos.add(stackCalculatorToStackCalculatorDto(stackCalculator));
        });
        return stackCalculatorDtos;
    }
    public static StackElementDto stackElementToStackElementDto(StackElement stackElement) {
        StackElementDto stackElementDto = new StackElementDto();
        stackElementDto.setCodeStackCalculator(stackElement.getStackElementPK().getCodeStackCalculator());
        stackElementDto.setOrderValue(stackElement.getStackElementPK().getOrderValue());
        stackElementDto.setValue(stackElement.getValue());
        return stackElementDto;
    }

    public static List<StackElementDto> stackElementsToStackElementsDto(List<StackElement> stackElements) {
        List<StackElementDto> stackElementDtos = new ArrayList<>();
        stackElements.forEach(stackElement -> {
            stackElementDtos.add(stackElementToStackElementDto(stackElement));
        });
        return stackElementDtos;
    }
}
