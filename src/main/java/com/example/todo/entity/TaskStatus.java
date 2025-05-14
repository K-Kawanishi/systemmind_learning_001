package com.example.todo.entity;

/**
 * タスクの状態を表す列挙型。
 * 各状態は以下の通り:
 * - TODO: タスクが未着手の状態
 * - DOING: タスクが進行中の状態
 * - DONE: タスクが完了した状態
 */
public enum TaskStatus {
    TODO,
    DOING,
    DONE
}
