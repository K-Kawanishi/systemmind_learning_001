package com.example.todo.entity;

/**
 * タスクを表すエンティティクラス。
 *
 * @param id タスクの一意な識別子
 * @param summary タスクの概要
 * @param description タスクの詳細説明
 * @param status タスクの現在の状態
 * @param priority タスクの優先度
 */
public record TaskEntity(
        Long id,
        String summary,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Long operatorId,
        String operatorName
) {
}
