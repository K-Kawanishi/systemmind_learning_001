package com.example.todo.repository;


import com.example.todo.entity.TaskEntity;
import com.example.todo.entity.TaskSearchEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskRepository {

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
              </where>
            </script>
            """)
    List<TaskEntity> select(@Param("condition") TaskSearchEntity condition);

    @Select("SELECT * FROM tasks WHERE id = #{taskId};")
    Optional<TaskEntity> selectById(@Param("taskId") long taskId);

    @Insert("""
        INSERT INTO tasks (summary, description, status)
        VALUES (#{task.summary}, #{task.description}, #{task.status});
        """)
    void insert(@Param("task") TaskEntity newEntity);

    @Update("""
        Update tasks
        SET
            summary     = #{task.summary},
            description = #{task.description},
            status      = #{task.status}
        WHERE
            id = #{task.id};
        """)
    void update(@Param("task") TaskEntity entity);

    @Delete("DELETE FROM tasks WHERE id = #{id};")
    void delete(@Param("id") long id);
}
