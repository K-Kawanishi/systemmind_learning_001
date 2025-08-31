package com.example.todo.form.operators;

import com.example.todo.dto.operators.OperatorSearchDTO;
import com.example.todo.entity.Gender;
import com.example.todo.entity.OperatorSearch;

import java.util.List;
import java.util.Optional;

public record OperatorSearchForm(
        String name,
        List<String> gender
) {
    /**
     * フォームデータをエンティティに変換します。
     *
     * @return OperatorSearch オブジェクト
     */
    public OperatorSearch toEntity() {
        var genderEntityList = Optional.ofNullable(gender())
                .map(genderList -> genderList.stream()
                        .map(Gender::valueOf).toList())
                .orElse(List.of());
        return new OperatorSearch (name(), genderEntityList);
    }

    /**
     * フォームデータをDTOに変換します。
     *
     * @return SearchDTO オブジェクト
     */
    public OperatorSearchDTO toDTO() {
        return new OperatorSearchDTO(name(), gender());
    }
}
