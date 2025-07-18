package com.example.todo.dto;

import java.util.List;
import java.util.Optional;

/**
 * タスク検索用のデータ転送オブジェクト (DTO)。
 *
 * @param summary    タスクの概要またはタイトル
 * @param statusList フィルタリングに使用するステータスのリスト
 */
public record TaskSearchDTO(
        String summary,
        List<String> statusList,
        List<String> priorityList
) {
    /**
     * 指定されたステータスがステータスリストに含まれているかを確認します。
     *
     * @param status 確認するステータス
     * @return ステータスがリストに含まれている場合はtrue、それ以外はfalse
     */
    public boolean isChecked(String status) {
        return Optional.ofNullable(statusList())
                .map(statusList -> statusList.contains(status))
                .orElse(false)
                ;
    }

    /**
     * 指定された優先度が優先度リストに含まれているかを確認します。
     *
     * @param priority 確認する優先度
     * @return 優先度がリストに含まれている場合はtrue、それ以外はfalse
     */
    public boolean isCheckedPriority(String priority) {
        return Optional.ofNullable(priorityList())
                .map(priorityList -> priorityList.contains(priority))
                .orElse(false)
                ;
    }
}
