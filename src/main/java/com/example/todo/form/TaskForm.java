package com.example.todo.form;

import com.example.todo.entity.TaskEntity;
import com.example.todo.entity.TaskPriority;
import com.example.todo.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * タスクフォームを表すレコードクラス。
 * バリデーションを含むフィールドとエンティティ変換メソッドを提供します。
 */
public record TaskForm (
//        @NotBlank
        String summary,
        @Size(max = 256 , message = "概要は256文字以内で入力してください")
        String description,
        @NotBlank
        @Pattern( regexp = "TODO|DOING|DONE" , message = "ステータスはTODO, DOING, DONEのいずれかを指定してください")
        String status,
        String priority
){

    /**
     * TaskEntityからTaskFormを生成します。
     *
     * @param taskEntity タスクエンティティ
     * @return 生成されたTaskForm
     */
    public static Object fromEntity(TaskEntity taskEntity) {
        return new TaskForm(taskEntity.summary(),
                taskEntity.description(),
                taskEntity.status().name(),
                taskEntity.priority().name()
        );
    }

    /**
     * TaskFormをTaskEntityに変換します。
     * IDはnullで設定されます。
     *
     * @return 変換されたTaskEntity
     */
    public TaskEntity toEntity() {
        return new TaskEntity(
                null,
                summary(),
                description(),
                TaskStatus.valueOf(status()),
                TaskPriority.valueOf(priority()),
                null // managerIdは新規作成時はnull
        );
    }

    /**
     * TaskFormをTaskEntityに変換します。
     * 指定されたIDを使用します。
     *
     * @param id タスクID
     * @return 変換されたTaskEntity
     */
    public TaskEntity toEntity(Long id) {
        return new TaskEntity(id,
                summary(),
                description(),
                TaskStatus.valueOf(status()),
                TaskPriority.valueOf(priority()),
                null // managerIdは編集画面では現状nullでOK
        );
    }
}
