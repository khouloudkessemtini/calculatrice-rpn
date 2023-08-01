package com.example.calculatrice.repository;


import com.example.calculatrice.domain.StackCalculator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StackRepository extends JpaRepository<StackCalculator, Integer> {
}
