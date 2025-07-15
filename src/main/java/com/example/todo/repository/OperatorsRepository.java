package com.example.todo.repository;

import com.example.todo.entity.Operators;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperatorsRepository {

    @Select("SELECT * FROM operators")
    List<Operators> findALL();
}
