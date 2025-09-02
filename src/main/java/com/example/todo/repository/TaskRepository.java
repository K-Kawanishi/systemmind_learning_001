package com.example.todo.repository;


import com.example.todo.entity.TaskEntity;
import com.example.todo.entity.TaskSearchEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * タスクを管理するためのリポジトリインターフェース。
 * MyBatisを使用してデータベース操作を行います。
 */
@Mapper
public interface TaskRepository {

    /**
     * 条件に基づいてタスクを検索します。
     *
     * @param condition 検索条件を含むエンティティ
     * @return 条件に一致するタスクのリスト
     */
    @Select("""
            <script>
              SELECT *
              FROM tasks
              <where>
                <if test='condition.summary != null and !condition.summary.isBlank()'>
                  summary LIKE CONCAT('%', #{condition.summary}, '%')
                </if>
                <if test='condition.status != null and condition.status.size() > 0'>
                  AND status IN
                      <foreach collection='condition.status' item='status' separator=',' open='(' close=')'>
                        #{status}
                      </foreach>
                </if>
                <if test='condition.priority != null and condition.priority.size() > 0'>
          AND priority IN
              <foreach collection='condition.priority' item='priority' separator=',' open='(' close=')'>
                #{priority}
              </foreach>
        </if>
              </where>
            </script>
            """)
    List<TaskEntity> select(@Param("condition") TaskSearchEntity condition);

    /**
     * IDに基づいてタスクを取得します。
     *
     * @param taskId タスクのID
     * @return 該当するタスクのOptional
     */
    @Select("SELECT * FROM tasks WHERE id = #{taskId};")
    Optional<TaskEntity> selectById(@Param("taskId") long taskId);

    /**
     * タスクと担当者を多対多で紐付けるため、タスク取得時に担当者リストも取得する。
     */
    @Select("""
        SELECT t.*, a.id AS assignee_id, a.name AS assignee_name
        FROM tasks t
        LEFT JOIN task_assignees ta ON t.id = ta.task_id
        LEFT JOIN assignees a ON ta.assignee_id = a.id
        WHERE t.id = #{taskId}
        """)
    @Results(id = "TaskWithAssignees", value = {
        @Result(property = "id", column = "id"),
        @Result(property = "summary", column = "summary"),
        @Result(property = "description", column = "description"),
        @Result(property = "status", column = "status"),
        @Result(property = "priority", column = "priority"),
        @Result(property = "assignees", javaType = java.util.List.class,
            column = "id",
            many = @Many(select = "com.example.todo.repository.TaskRepository.findAssigneesByTaskId"))
    })
    TaskEntity selectWithAssigneesById(@Param("taskId") long taskId);

    @Select("""
        SELECT a.id, a.name FROM assignees a
        INNER JOIN task_assignees ta ON a.id = ta.assignee_id
        WHERE ta.task_id = #{taskId}
        """)
    List<com.example.todo.entity.Assignee> findAssigneesByTaskId(@Param("taskId") long taskId);

    @Insert("""
        INSERT INTO tasks (summary, description, status, priority)
        VALUES (#{summary}, #{description}, #{status}, #{priority})
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TaskEntity task);

    @Update("""
        UPDATE tasks
        SET summary = #{summary},
            description = #{description},
            status = #{status},
            priority = #{priority}
        WHERE id = #{id}
        """)
    void update(TaskEntity task);

    @Delete("DELETE FROM tasks WHERE id = #{id}")
    void delete(@Param("id") Long id);

    /**
     * 複数IDでタスクをまとめて取得
     */
    @Select({
        "<script>",
        "SELECT * FROM tasks WHERE id IN",
        "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
        "  #{id}",
        "</foreach>",
        "</script>"
    })
    List<TaskEntity> findAllByIds(@Param("ids") List<Long> ids);
}
