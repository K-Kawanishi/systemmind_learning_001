package com.example.todo.entity;

public record OperatorUsers(
        Long id,
        String name,
        String kana,
        String email,
        String phoneNumber,
        Gender gender
) {
}
