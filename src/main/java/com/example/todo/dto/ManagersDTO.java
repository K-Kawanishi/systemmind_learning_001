package com.example.todo.dto;

import com.example.todo.entity.ManagersEntity;

public record ManagersDTO(
    Long id,
    String name,
    String age,
    String gender
) {
    public static ManagersDTO fromEntity(ManagersEntity entity) {
        return new ManagersDTO(
            entity.id(),
            entity.name(),
            entity.age(),
            entity.gender()
        );
    }
}

