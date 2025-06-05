package com.example.todo.form;

import com.example.todo.dto.TaskSearchDTO;
import com.example.todo.entity.TaskPriority;
import com.example.todo.entity.TaskSearchEntity;
import com.example.todo.entity.TaskStatus;

import java.util.List;
import java.util.Optional;

/**
 * タスク検索フォームを表すレコードクラス。
 *
 * @param summary 検索対象のタスクの概要
 * @param status  検索対象のタスクのステータスリスト
 */
public record TaskSearchForm(
        String summary,
        List<String> status,
        List<String> priority
) {
    /**
     * フォームデータをエンティティに変換します。
     *
     * @return TaskSearchEntity オブジェクト
     */
    public TaskSearchEntity toEntity() {
        var statusEntityList = Optional.ofNullable(status())
                .map(statusList -> statusList.stream()
                        .map(TaskStatus::valueOf).toList())
                .orElse(List.of());
        var priorityEntityList = Optional.ofNullable(priority())
                .map(priorityList -> priorityList.stream()
                        .map(TaskPriority::valueOf).toList())
                .orElse(List.of());
        return new TaskSearchEntity(summary(), statusEntityList, priorityEntityList);
    }

    /**
     * フォームデータをDTOに変換します。
     *
     * @return TaskSearchDTO オブジェクト
     */
    public TaskSearchDTO toDTO() {
        return new TaskSearchDTO(summary(), status(), priority());
    }
}