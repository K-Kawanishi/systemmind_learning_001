package com.example.todo.repository;

import com.example.todo.entity.Assignee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AssigneeRepository {
    @Select("SELECT * FROM assignees")
    List<Assignee> findAll();

    @Select("SELECT * FROM assignees WHERE id = #{id}")
    Assignee findById(@Param("id") Long id);

    @Select("SELECT * FROM assignees WHERE name = #{name}")
    Assignee findByName(@Param("name") String name);

    @Insert("INSERT INTO assignees (name) VALUES (#{name})")
    void insert(Assignee assignee);
}

