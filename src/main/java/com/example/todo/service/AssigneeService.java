package com.example.todo.service;

import com.example.todo.entity.Assignee;
import com.example.todo.repository.AssigneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssigneeService {
    @Autowired
    private AssigneeRepository assigneeRepository;

    public List<Assignee> findAll() {
        return assigneeRepository.findAll();
    }

    public Assignee findById(Long id) {
        return assigneeRepository.findById(id);
    }

    public Assignee findByName(String name) {
        return assigneeRepository.findByName(name);
    }

    public void insert(Assignee assignee) {
        assigneeRepository.insert(assignee);
    }
}

