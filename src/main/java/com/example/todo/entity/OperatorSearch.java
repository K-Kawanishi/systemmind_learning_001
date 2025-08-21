package com.example.todo.entity;

import java.util.List;

public record OperatorSearch(
        String name,
        List<Gender> gender
) {
}
