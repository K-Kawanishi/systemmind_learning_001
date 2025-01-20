package com.example.todo.form;

import com.example.todo.dto.TaskSearchDTO;
import com.example.todo.entity.TaskSearchEntity;
import com.example.todo.entity.TaskStatus;
import org.springframework.scheduling.config.Task;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public record TaskSearchForm(
        String summary,
        List<String> status
) {
    public TaskSearchEntity toEntity() {
        var statusEntityList = Optional.ofNullable(status())
                .map(statusList -> statusList.stream()
                        .map(TaskStatus::valueOf).toList())
                .orElse(List.of());
        return new TaskSearchEntity(summary(), statusEntityList);
    }

    public TaskSearchDTO toDTO() {
        return new TaskSearchDTO(summary(), status());
    }

}
