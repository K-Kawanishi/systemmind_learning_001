package com.example.todo.entity;

import java.util.List;

/**
 * タスク検索エンティティを表します。
 *
 * @param summary タスクの概要を表す文字列
 * @param status  タスクの状態を表すリスト
 * @param priority タスクの優先度を表すリスト（将来の拡張用）
 */
public record TaskSearchEntity(
        String summary,
        List<TaskStatus> status,
        List<TaskPriority> priority
) {
}
