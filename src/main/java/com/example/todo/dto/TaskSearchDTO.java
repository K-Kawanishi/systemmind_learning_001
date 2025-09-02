package com.example.todo.dto;

import java.util.List;
import java.util.Optional;

/**
 * タスク検索用のデータ転送オブジェクト (DTO)。
 *
 * @param summary    タスクの概要またはタイトル
 * @param status フィルタリングに使用するステータスのリスト
 * @param priority フィルタリングに使用する優先度のリスト
 */
public record TaskSearchDTO(
        String summary,
        List<String> status,
        List<String> priority
) {
    /**
     * 指定されたステータスがステータスリストに含まれているかを確認します。
     *
     * @param status 確認するステータス
     * @return ステータスがリストに含まれている場合はtrue、それ以外はfalse
     */
    public boolean isChecked(String status) {
        return Optional.ofNullable(status())
                .map(statusList -> statusList.contains(status))
                .orElse(false);
    }
    public boolean isPriorityChecked(String priority) {
        return Optional.ofNullable(priority())
                .map(list -> list.contains(priority))
                .orElse(false);
    }
}
