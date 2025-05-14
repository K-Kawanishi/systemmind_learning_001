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
        List<String> statusList
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
}
