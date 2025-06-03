package com.example.todo.entity;

/**
 * タスクの状態を表す列挙型。
 * 各状態は以下の通り:
 * - HIGH: 優先度が高い
 * - NORMAL: 優先度が普通
 * - LOW:  優先度が低い
 */
public enum TaskPriority {
    HIGH,
    NORMAL,
    LOW
}
