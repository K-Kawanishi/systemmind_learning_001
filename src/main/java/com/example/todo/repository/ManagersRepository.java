package com.example.todo.repository;

import com.example.todo.entity.ManagersEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManagersRepository {
    @Select("SELECT * FROM managers")
    List<ManagersEntity> findAll();

    @Select("SELECT * FROM managers WHERE id = #{id}")
    ManagersEntity findById(Long id);
}

