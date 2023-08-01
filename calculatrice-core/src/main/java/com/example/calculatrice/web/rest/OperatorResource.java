package com.example.calculatrice.web.rest;

import com.example.calculatrice.dto.Operator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/operator")
public class OperatorResource {

    @GetMapping
    public Map<Operator, String> getAll() {

        Map<Operator, String> operators = new HashMap();
        operators.put(Operator.ADD, Operator.ADD.getSymbol());
        operators.put(Operator.SUBTRACT, Operator.SUBTRACT.getSymbol());
        operators.put(Operator.MULTIPLY, Operator.MULTIPLY.getSymbol());
        operators.put(Operator.DIVIDE, Operator.DIVIDE.getSymbol());
        return operators;
    }

}
