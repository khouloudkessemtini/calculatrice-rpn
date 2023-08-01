package com.example.calculatrice.service;


import com.example.calculatrice.Preconditions;
import com.example.calculatrice.domain.StackCalculator;
import com.example.calculatrice.domain.StackElement;
import com.example.calculatrice.domain.StackElementPK;
import com.example.calculatrice.dto.Operator;
import com.example.calculatrice.dto.StackCalculatorDto;
import com.example.calculatrice.dto.StackElementDto;
import com.example.calculatrice.factory.StackFactory;
import com.example.calculatrice.repository.StackElementRepository;
import com.example.calculatrice.repository.StackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StackService {

    private final Logger log = LoggerFactory.getLogger(StackService.class);
    private final StackRepository stackRepository;
    private final StackElementRepository stackElementRepository;


    public StackService(StackRepository stackRepository, StackElementRepository stackElementRepository) {
        this.stackRepository = stackRepository;
        this.stackElementRepository = stackElementRepository;
    }

    @Transactional(readOnly = true)
    public StackCalculatorDto findOne(Integer id) {
        Optional<StackCalculator> resultOptional = stackRepository.findById(id);
        Preconditions.checkBusinessLogique(resultOptional.isPresent(), "StackCalculator not found");
        return   StackFactory.stackCalculatorToStackCalculatorDto(resultOptional.get());
   }
    @Transactional(readOnly = true)
    public List<StackCalculatorDto> findAll() {
        List<StackCalculator> result = stackRepository.findAll();
        return  StackFactory.stackCalculatorListToStackCalculatorDtoList(result);
    }

    @Transactional
    public StackCalculatorDto create(StackCalculatorDto stackCalculatorDto) {
        StackCalculator stackCalculatorToSave =  StackFactory.stackCalculatorDtoToStackCalculator(stackCalculatorDto);
        StackCalculator stackCalculatorSaved = stackRepository.save(stackCalculatorToSave);
        return   StackFactory.stackCalculatorToStackCalculatorDto(stackCalculatorSaved);
    }

    @Transactional
    public StackCalculatorDto empilerStack(Integer id, Integer valueToPush) {
        Optional<StackCalculator> resultOptional = stackRepository.findById(id);
        Preconditions.checkBusinessLogique(resultOptional.isPresent(), "La pile n'existe pas");
        Integer stackOrder = null;
        if(resultOptional.get().getStackElements() != null && resultOptional.get().getStackElements().isEmpty()) {
            stackOrder = 0;
        }else {
            List<StackElementDto> stackElementDtos = StackFactory.stackElementsToStackElementsDto(resultOptional.get().getStackElements());
            stackOrder =   stackElementDtos.stream().sorted(Comparator.comparing(StackElementDto::getOrderValue).reversed())
                    .findFirst().get().getOrderValue();
        }

        resultOptional.get().getStackElements().add(new StackElement(new StackElementPK(id, stackOrder + 1), valueToPush));
        return   StackFactory.stackCalculatorToStackCalculatorDto(resultOptional.get());
    }

    @Transactional
    public StackCalculatorDto depilerStack(Integer id) {
        Optional<StackCalculator> resultOptional = stackRepository.findById(id);
        Preconditions.checkBusinessLogique(resultOptional.isPresent(), "La pile n'existe pas");

        List<StackElement> stockToDepiler = stackElementRepository.findByStackElementPK_CodeStackCalculator(id);
        if(!stockToDepiler.isEmpty()) {
            stackElementRepository.deleteAll(stockToDepiler);
            stackElementRepository.flush();
        }

        StackCalculatorDto stackCalculatorDto = StackFactory.stackCalculatorToStackCalculatorDtoLazy(resultOptional.get());
        stackCalculatorDto.setStackElements(new ArrayList<>());
        return   stackCalculatorDto;
    }

    @Transactional
    public StackCalculatorDto applyOperatorToStack(Integer id, Operator operator) {
        Optional<StackCalculator> resultOptional = stackRepository.findById(id);
        Preconditions.checkBusinessLogique(resultOptional.isPresent(), "La pile n'existe pas");

        List<StackElement> stackCalculatorDetailLastTwo = stackElementRepository.findTop2ByStackElementPK_CodeStackCalculatorOrderByStackElementPK_OrderValueDesc(id);
        Preconditions.checkBusinessLogique(!stackCalculatorDetailLastTwo.isEmpty(), "Stack is empty");
        Preconditions.checkBusinessLogique(stackCalculatorDetailLastTwo.size() > 1, "La pile doit comporter plus d'un élément.");
        Integer lastOrderInserted = stackCalculatorDetailLastTwo.stream().map(stackCalculatorDetail -> stackCalculatorDetail.getStackElementPK().getOrderValue()).max(Integer::compare).get();
        Integer valueToPush = null;
        switch (operator) {
            case ADD:
                valueToPush = stackCalculatorDetailLastTwo.stream().map(StackElement::getValue).mapToInt(Integer::intValue).sum();
                break;
            case SUBTRACT:
                valueToPush = stackCalculatorDetailLastTwo.stream().filter(x -> x.getStackElementPK().getOrderValue().compareTo(lastOrderInserted) < 0 ).findAny().get().getValue() -
                        stackCalculatorDetailLastTwo.stream().filter(x -> x.getStackElementPK().getOrderValue().equals(lastOrderInserted)).findAny().get().getValue();

                break;
            case MULTIPLY:
                valueToPush =  stackCalculatorDetailLastTwo.stream().map(StackElement::getValue).reduce(1, (elt1, elt2) -> elt1 * elt2);
                break;
            case DIVIDE:
                Integer eltLastIn = stackCalculatorDetailLastTwo.stream().filter(x -> x.getStackElementPK().getOrderValue().equals(lastOrderInserted)).findAny().get().getValue();
                Preconditions.checkBusinessLogique(eltLastIn != 0 ,"Error: Cannot divide by zero!");
                valueToPush =  stackCalculatorDetailLastTwo.stream().filter(x -> x.getStackElementPK().getOrderValue().compareTo(lastOrderInserted) < 0 ).findAny().get().getValue() /
                        eltLastIn;
                break;
            default:
               Preconditions.checkBusinessLogique(false, "Error: Invalid operator!");
        }
        log.error("**valueToPush :{}", valueToPush.toString());

        if(valueToPush != 0) {
            stackElementRepository.save(new StackElement(new StackElementPK(id, lastOrderInserted + 1), valueToPush));
        }
        List<StackElementPK> idsToDelete = stackCalculatorDetailLastTwo.stream()
                .map(StackElement::getStackElementPK).collect(Collectors.toList());

        idsToDelete.forEach(idToDelete -> {
            log.error("**idsToDelete :{}", idToDelete.getOrderValue().toString());
            stackElementRepository.deleteById(idToDelete);
            stackElementRepository.flush();
        } );
        List<StackElement> allElementsOfStack = stackElementRepository.findByStackElementPK_CodeStackCalculator(id);
        StackCalculatorDto stackCalculatorDto = StackFactory.stackCalculatorToStackCalculatorDtoLazy(resultOptional.get());
        if(!allElementsOfStack.isEmpty())
            stackCalculatorDto.setStackElements(StackFactory.stackElementsToStackElementsDto(allElementsOfStack));

        return stackCalculatorDto;
    }

    @Transactional
    public void delete(Integer id) {
        Optional<StackCalculator> resultOptional = stackRepository.findById(id);
        Preconditions.checkBusinessLogique(resultOptional.isPresent(), "La pile n'existe pas");
        stackRepository.deleteById(id);
    }

}
