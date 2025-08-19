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
              SELECT
                t.id,
                t.summary,
                t.description,
                t.status,
                t.priority,
                t.operator_id AS operator_id,
                o.name AS operator_name
              FROM tasks t
              LEFT JOIN operator_users o ON t.operator_id = o.id
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
                <if test='condition.operatorId > 0'>
                  AND operator_id = #{condition.operatorId}
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
    @Select("SELECT t.id, t.summary, t.description, t.status, t.priority, " +
            "t.operator_id AS operator_id, " +
            "o.name AS operator_name " +
            "FROM tasks t " +
            "LEFT JOIN operator_users o ON t.operator_id = o.id " +
            "WHERE t.id = #{taskId};")
    Optional<TaskEntity> selectById(@Param("taskId") long taskId);

    /**
     * 新しいタスクをデータベースに挿入します。
     *
     * @param newEntity 挿入するタスクのエンティティ
     */
    @Insert("""
        INSERT INTO tasks (summary, description, status, priority, operator_id)
        VALUES (#{task.summary}, #{task.description}, #{task.status}, #{task.priority}, #{task.operatorId});
        """)
    void insert(@Param("task") TaskEntity newEntity);

    /**
     * タスクを更新します。
     *
     * @param entity 更新するタスクのエンティティ
     */
    @Update("""
        Update tasks
        SET
            summary     = #{task.summary},
            description = #{task.description},
            status      = #{task.status},
            priority    = #{task.priority},
            operator_id = #{task.operatorId}
        WHERE
            id = #{task.id};
        """)
    void update(@Param("task") TaskEntity entity);

    /**
     * IDに基づいてタスクを削除します。
     *
     * @param id 削除するタスクのID
     */
    @Delete("DELETE FROM tasks WHERE id = #{id};")
    void delete(@Param("id") long id);

    @Update("""
        <script>
        UPDATE tasks
        SET status = #{status}
        WHERE id IN
            <foreach collection='ids' item='id' separator=',' open='(' close=')'>
                #{id}
            </foreach>
        </script>
        """)
    void updateStatus(@Param("ids") List<Long> ids,@Param("status") String status);

    @Update("""
        <script>
        UPDATE tasks
        SET priority = #{priority}
        WHERE id IN
            <foreach collection='ids' item='id' separator=',' open='(' close=')'>
                #{id}
            </foreach>
        </script>
        """)
    void updatePriority(@Param("ids") List<Long> ids,@Param("priority") String priority);
}