package com.example.calculatrice.web.rest;

import com.example.calculatrice.Preconditions;
import com.example.calculatrice.dto.Operator;
import com.example.calculatrice.dto.StackCalculatorDto;
import com.example.calculatrice.service.StackService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/stack")
public class StackResource {

    private final StackService stackService;

    public StackResource(StackService stackService) {
        this.stackService = stackService;
    }

    @Operation(summary = "Get all stacks")
    @GetMapping
    public List<StackCalculatorDto> getAll() {
        return stackService.findAll();
    }

    @GetMapping("{id}")
    public StackCalculatorDto getOne(@PathVariable Integer id) {
        return stackService.findOne(id);
    }

    @Operation(summary = "Create a stack")
    @PostMapping
     public ResponseEntity<StackCalculatorDto> create(@Valid @RequestBody StackCalculatorDto stackCalculatorDto) throws URISyntaxException {

        StackCalculatorDto result = stackService.create(stackCalculatorDto);
        return ResponseEntity.created(new URI("/api/stack/" + result.getCode())).body(result);

    }

    @Operation(summary = "Push a value to stack")
    @PostMapping("{id}")
    public ResponseEntity<StackCalculatorDto> empilerStack(
            @PathVariable Integer id,
            @RequestBody Integer value) {
        StackCalculatorDto result = stackService.empilerStack(id, value);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Reset the stack or apply operator (+,-,/,*) to Stack")
    @PutMapping("{id}")
    public ResponseEntity<StackCalculatorDto> updateStack(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = "false") Boolean depiler,
            @RequestParam(required = false) Operator operator ) {
        StackCalculatorDto result = null;
        Preconditions.checkBusinessLogique(!(Boolean.TRUE.equals(depiler) && operator != null), "Depiler et appliquer Operateur en meme est temps impossible");
        if(Boolean.TRUE.equals(depiler)){
            result = stackService.depilerStack(id);
        }else if(operator != null){
            result = stackService.applyOperatorToStack(id, operator);
        }

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        stackService.delete(id);
        return ResponseEntity.ok().build();
    }

}
