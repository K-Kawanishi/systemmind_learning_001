package com.example.todo.service;

import com.example.todo.entity.OperatorUsers;
import com.example.todo.repository.OperatorUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final OperatorUsersRepository operatorUsersRepository;

    public List<OperatorUsers> findAll() {
        return operatorUsersRepository.findALL();
    }

}
