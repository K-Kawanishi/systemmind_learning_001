package com.example.todo.service;

import com.example.todo.entity.Operators;
import com.example.todo.repository.OperatorsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorsService {

    private final OperatorsRepository operatorsRepository;

    public OperatorsService(OperatorsRepository operatorsRepository) {
        this.operatorsRepository = operatorsRepository;
    }

   public List<Operators> findAll() {
        return operatorsRepository.findALL();
    }
}
