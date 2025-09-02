package com.example.todo.repository;

import com.example.todo.entity.TaskAssignee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface TaskAssigneeRepository {
    @Insert("INSERT INTO task_assignees (task_id, assignee_id) VALUES (#{taskId}, #{assigneeId})")
    void insert(@Param("taskId") Long taskId, @Param("assigneeId") Long assigneeId);

    @Delete("DELETE FROM task_assignees WHERE task_id = #{taskId}")
    void deleteByTaskId(@Param("taskId") Long taskId);

    @Select("SELECT * FROM task_assignees WHERE task_id = #{taskId}")
    List<TaskAssignee> findByTaskId(@Param("taskId") Long taskId);

    @Select("SELECT * FROM task_assignees WHERE assignee_id = #{assigneeId}")
    List<TaskAssignee> findByAssigneeId(@Param("assigneeId") Long assigneeId);
}

