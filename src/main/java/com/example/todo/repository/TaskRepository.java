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
     * 新しいタスクをデータベースに挿入します。
     *
     * @param newEntity 挿入するタスクのエンティティ
     */
    @Insert("""
        INSERT INTO tasks (summary, description, status, priority)
        VALUES (#{task.summary}, #{task.description}, #{task.status}, #{task.priority});
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
            priority    = #{task.priority}
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

    /**
     * 複数IDのタスクを一括削除します。
     * @param ids 削除するタスクIDリスト
     */
    @Delete("""
        <script>
        DELETE FROM tasks WHERE id IN
        <foreach collection='ids' item='id' open='(' separator=',' close=')'>
            #{id}
        </foreach>
        </script>
        """)
    void deleteAllByIds(@Param("ids") List<Long> ids);

    /**
     * 複数IDのタスクを一括編集します（ステータス・優先度）。
     */
    @Update("""
        <script>
        UPDATE tasks
        <set>
            <if test='status != null and status != ""'>status = #{status},</if>
            <if test='priority != null and priority != ""'>priority = #{priority},</if>
        </set>
        WHERE id IN
        <foreach collection='ids' item='id' open='(' separator=',' close=')'>
            #{id}
        </foreach>
        </script>
        """)
    void bulkEdit(@Param("ids") List<Integer> ids, @Param("status") String status, @Param("priority") String priority);

    // 優先度でソートするクエリ
    List<TaskEntity> findAllByOrderByPriorityDesc();
}

