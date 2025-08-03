package com.example.todo.dto;

import com.example.todo.entity.TaskEntity;


/**
 * タスクを表すDTO（Data Transfer Object）クラス。
 * このクラスはタスクのデータを転送するために使用されます。
 *
 * @param id          タスクの一意の識別子
 * @param summary     タスクの概要
 * @param description タスクの詳細説明
 * @param status      タスクの状態（例: "PENDING", "COMPLETED"）
 * @param priority    タスクの優先度（例: "HIGH", "MEDIUM", "LOW"）
 * @param managerName 担当者名
 */
public record TaskDTO(
        long id,
        String summary,
        String description,
        String status,
        String priority,
        String managerName
         ){
    /**
     * TaskEntityオブジェクトをTaskDTOに変換します。
     *
     * @param entity TaskEntityオブジェクト
     * @param managerName 担当者名
     * @return TaskDTOオブジェクト
     */
    public static TaskDTO toDTO(TaskEntity entity, String managerName) {
        return new TaskDTO(
                entity.id(),
                entity.summary(),
                entity.description(),
                entity.status().name(),
                entity.priority().name(),
                managerName
        );
    }
}

/*aa*/