package com.example.todo.repository;

import com.example.todo.entity.OperatorUsers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperatorUsersRepository {

    @Select("SELECT * FROM operator_users")
    List<OperatorUsers> findALL();
}
