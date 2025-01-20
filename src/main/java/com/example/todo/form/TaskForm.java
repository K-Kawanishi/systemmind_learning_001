package com.example.todo.form;

import com.example.todo.entity.TaskEntity;
import com.example.todo.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TaskForm (
        @NotBlank
        @Size(max = 256 , message = "概要は256文字以内で入力してください")
        String summary,
        String description,
        @NotBlank
        @Pattern( regexp = "TODO|DOING|DONE" , message = "ステータスはTODO, DOING, DONEのいずれかを指定してください")
        String status
){

    public static Object fromEntity(TaskEntity taskEntity) {
        return new TaskForm(taskEntity.summary(), taskEntity.description(), taskEntity.status().name());
    }

    public TaskEntity toEntity() {
        return new TaskEntity(null, summary(), description(), TaskStatus.valueOf(status()));
    }

    public TaskEntity toEntity(Long id) {
        return new TaskEntity(id, summary(), description(), TaskStatus.valueOf(status()));
    }
}
