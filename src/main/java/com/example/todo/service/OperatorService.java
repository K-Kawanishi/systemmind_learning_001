package com.example.todo.service;

import com.example.todo.entity.OperatorSearch;
import com.example.todo.entity.OperatorUsers;
import com.example.todo.repository.OperatorUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final OperatorUsersRepository operatorUsersRepository;

    public List<OperatorUsers> findAll() {
        return operatorUsersRepository.findALL();
    }

    public List<OperatorUsers> find(OperatorSearch searchEntity) {
        return operatorUsersRepository.select(searchEntity);
    }

    public Optional<OperatorUsers> findById(long operatorId) {
        return operatorUsersRepository.selectById(operatorId);
    }

    @Transactional
    public void create(OperatorUsers newEntity) {
        operatorUsersRepository.insert(newEntity);
    }

    @Transactional
    public void update(OperatorUsers entity) {
        operatorUsersRepository.update(entity);
    }

    @Transactional
    public void delete(long id) {
        operatorUsersRepository.delete(id);
    }


}
