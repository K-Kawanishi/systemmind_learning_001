package com.example.todo.entity;

import java.util.List;

/**
 * タスク検索エンティティを表します。
 *
 * @param summary タスクの概要を表す文字列
 * @param status  タスクの状態を表すリスト
 */
public record TaskSearchEntity(
        String summary,
        List<TaskStatus> status,
        List<TaskPriority> priority
) {
}
