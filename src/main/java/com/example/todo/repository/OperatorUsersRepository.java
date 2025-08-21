package com.example.todo.repository;

import com.example.todo.entity.OperatorSearch;
import com.example.todo.entity.OperatorUsers;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OperatorUsersRepository {

    @Select("SELECT * FROM operator_users")
    List<OperatorUsers> findALL();

    @Select("""
  <script>
    SELECT
    *
    FROM operator_users o
    <where>
      <if test='condition.name != null and !condition.name.isBlank()'>
        name LIKE CONCAT('%', #{condition.name}, '%')
      </if>
      <if test='condition.gender != null and condition.gender.size() > 0'>
        AND gender IN
            <foreach collection='condition.gender' item='gender' separator=',' open='(' close=')'>
              #{gender}
            </foreach>
      </if>
    </where>
  </script>
""")
    List<OperatorUsers> select(@Param("condition") OperatorSearch condition);

    /**
     * IDに基づいてオペレーターを検索します。
     *
     * @param operatorId オペレーターのID
     * @return オペレーターのエンティティ
     */
    @Select("SELECT * FROM operator_users o  WHERE o.id = #{operatorId};")
    Optional<OperatorUsers> selectById(@Param("operatorId") long operatorId);

    /**
     * 新しいタスクをデータベースに挿入します。
     *
     * @param newEntity 挿入する担当者のエンティティ
     */
    @Insert("""
        INSERT INTO operator_users (name, kana, email, phone_number, gender)
        VALUES (#{operator.name}, #{operator.kana}, #{operator.email}, #{operator.phoneNumber}, #{operator.gender});
        """)
    void insert(@Param("operator") OperatorUsers newEntity);

    /**
     * タスクを更新します。
     *
     * @param entity 更新するタスクのエンティティ
     */
    @Update("""
        Update operator_users
        SET
            name     = #{operator.name},
            kana = #{operator.kana},
            email      = #{operator.email},
            phone_number    = #{operator.phoneNumber},
            gender = #{operator.gender}
        WHERE
            id = #{operator.id};
        """)
    void update(@Param("operator") OperatorUsers entity);

    /**
     * IDに基づいてタスクを削除します。
     *
     * @param id 削除する担当者のID
     */
    @Delete("DELETE FROM operator_users WHERE id = #{id};")
    void delete(@Param("id") long id);
}
