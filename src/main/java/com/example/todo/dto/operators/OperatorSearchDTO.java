package com.example.todo.dto.operators;

import java.util.List;
import java.util.Optional;

public record OperatorSearchDTO(
        String name,
        List<String> genderList
) {
    public boolean isChecked(String gender) {
        return Optional.ofNullable(genderList())
                .map(genderList -> genderList.contains(gender))
                .orElse(false)
                ;
    }
}
