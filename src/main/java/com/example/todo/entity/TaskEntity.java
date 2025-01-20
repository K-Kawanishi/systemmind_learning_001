package com.example.todo.entity;

public record TaskEntity(
        Long id,
        String summary,
        String description,
        TaskStatus status
) {
}
