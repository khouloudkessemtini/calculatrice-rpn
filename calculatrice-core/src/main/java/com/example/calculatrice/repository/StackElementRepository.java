package com.example.calculatrice.repository;

import com.example.calculatrice.domain.StackElement;
import com.example.calculatrice.domain.StackElementPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StackElementRepository  extends JpaRepository<StackElement, StackElementPK> {
   List<StackElement> findTop2ByStackElementPK_CodeStackCalculatorOrderByStackElementPK_OrderValueDesc(Integer codeStackCalculator);
   List<StackElement> findByStackElementPK_CodeStackCalculator(Integer codeStackCalculator);

}
