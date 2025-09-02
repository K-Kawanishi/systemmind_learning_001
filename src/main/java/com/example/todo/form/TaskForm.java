package com.example.todo.form;

import com.example.todo.entity.TaskEntity;
import com.example.todo.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * タスクフォームを表すレコードクラス。
 * バリデーションを含むフィールドとエンティティ変換メソッドを提供します。
 */
public record TaskForm (
        @NotBlank
        @Size(max = 256 , message = "概要は256文字以内で入力してください")
        String summary,
        @Size(max = 1000, message = "詳細は1000文字以内で入力してください")
        String description,
        @NotBlank
        @Pattern( regexp = "TODO|DOING|DONE" , message = "ステータスはTODO, DOING, DONEのいずれかを指定してください")
        String status,
        @NotBlank(message = "優先度を選択してください")
        String priority,
        @NotBlank(message = "担当者を選択してください")
        String assigneeId

){
    /**
     * TaskEntityからTaskFormを生成します。
     *
     * @param taskEntity タスクエンティティ
     * @return 生成されたTaskForm
     */
    public static TaskForm fromEntity(TaskEntity taskEntity) {
        String assigneeId = (taskEntity.getAssignees() != null && !taskEntity.getAssignees().isEmpty())
            ? String.valueOf(taskEntity.getAssignees().get(0).getId())
            : "";
        return new TaskForm(
            taskEntity.getSummary(),
            taskEntity.getDescription(),
            taskEntity.getStatus() != null ? taskEntity.getStatus().name() : null,
            taskEntity.getPriority(),
            assigneeId
        );
    }

    /**
     * TaskFormをTaskEntityに変換します。
     * IDはnullで設定されます。
     *
     * @return 変換されたTaskEntity
     */
    public TaskEntity toEntity() {
        return new TaskEntity(null, summary, description, TaskStatus.valueOf(status), priority, null);
    }

    /**
     * TaskFormをTaskEntityに変換します。
     * 指定されたIDを使用します。
     *
     * @param id タスクID
     * @return 変換されたTaskEntity
     */
    public TaskEntity toEntity(Long id) {
        return new TaskEntity(id, summary, description, TaskStatus.valueOf(status), priority, null);
    }
}
