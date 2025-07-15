package com.example.todo.entity;

import java.sql.Date;

public record Operators(
        Long id,
        String name,
        String nameKana,
        Gender gender,
        Date birthday,
        String email

) {
}
