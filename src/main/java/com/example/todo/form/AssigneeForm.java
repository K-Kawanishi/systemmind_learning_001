package com.example.todo.form;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class AssigneeForm {
    @NotBlank(message = "担当者名は必須です")
    private String name;
}

