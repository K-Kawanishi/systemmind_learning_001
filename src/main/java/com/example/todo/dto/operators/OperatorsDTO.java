package com.example.todo.dto.operators;

import com.example.todo.entity.OperatorUsers;

public record OperatorsDTO(
        Long id,
        String name,
        String kana,
        String email,
        String phoneNumber,
        String gender
) {
    public static OperatorsDTO toDTO(OperatorUsers users) {
        return new OperatorsDTO(
                users.id(),
                users.name(),
                users.kana(),
                users.email(),
                users.phoneNumber(),
                users.gender().name()
        );
    }
}
