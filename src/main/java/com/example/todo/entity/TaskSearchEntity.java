package com.example.todo.entity;

import com.example.todo.repository.TaskRepository;

import java.util.List;

public record TaskSearchEntity(
        String summary,
        List<TaskStatus> status
) {
}
