package com.example.todo.service;

import com.example.todo.dto.TaskDTO;
import com.example.todo.entity.ManagersEntity;
import com.example.todo.entity.TaskEntity;
import com.example.todo.entity.TaskSearchEntity;
import com.example.todo.repository.ManagersRepository;
import com.example.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * タスクに関連するサービスクラス。
 * データベース操作を行うリポジトリを利用して、タスクの作成、更新、削除、検索を提供します。
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ManagersRepository managersRepository;

    /**
     * 指定された検索条件に基づいてタスクを検索します。
     *
     * @param searchEntity 検索条件を含むエンティティ
     * @return 検索結果のタスクリスト
     */
    public List<TaskEntity> find(TaskSearchEntity searchEntity) {
        return taskRepository.select(searchEntity);
    }

    /**
     * 指定されたIDのタスクを検索します。
     *
     * @param taskId タスクのID
     * @return 検索結果のタスク（存在しない場合は空のOptional）
     */
    public Optional<TaskEntity> findById(long taskId) {
        return taskRepository.selectById(taskId);
    }

    /**
     * 新しいタスクを作成します。
     *
     * @param newEntity 作成するタスクのエンティティ
     */
    @Transactional
    public void create(TaskEntity newEntity) {
        taskRepository.insert(newEntity);
    }

    /**
     * 新しいタスクを作成します。
     *
     * @param newEntity 作成するタスクのエンティティ
     */
    @Transactional
    public void create2(TaskEntity newEntity, ManagersEntity managersEntity) {
        taskRepository.insert2(newEntity, managersEntity.name());
    }


    /**
     * 指定されたタスクを更新します。
     *
     * @param entity 更新するタスクのエンティティ
     */
    @Transactional
    public void update(TaskEntity entity) {
        taskRepository.update(entity);
    }

    /**
     * 指定されたIDのタスクを削除します。
     *
     * @param id 削除するタスクのID
     */
    @Transactional
    public void delete(long id) {
        taskRepository.delete(id);
    }

    /**
     * 指定されたIDリストのタスクを一括削除します。
     *
     * @param ids 削除するタスクのIDリスト
     */
    @Transactional
    public void deleteByIds(List<Long> ids) {
        taskRepository.deleteByIds(ids);
    }

    /**
     * managersテーブルの全件取得
     */
    public List<ManagersEntity> findAllManagers() {
        return managersRepository.findAll();
    }

    /**
     * タスクと担当者名を紐付けたDTOリストを返す
     */
    public List<TaskDTO> findManagerName(TaskSearchEntity searchEntity) {
        List<TaskEntity> tasks = taskRepository.select(searchEntity);
        List<ManagersEntity> managers = managersRepository.findAll();
        Map<Long, String> managerMap = managers.stream()
                .collect(Collectors.toMap(ManagersEntity::id, ManagersEntity::name));
        return tasks.stream()
                .map(task -> TaskDTO.toDTO(task, managerMap.get(task.id())))
                .collect(Collectors.toList());
    }

    /**
     * タスクIDで1件取得し、担当者名を付与したDTOを返す
     */
    public Optional<TaskDTO> findManagerName(long taskId) {
        Optional<TaskEntity> taskOpt = taskRepository.selectById(taskId);
        if (taskOpt.isEmpty()) return Optional.empty();
        List<ManagersEntity> managers = managersRepository.findAll();
        Map<Long, String> managerMap = managers.stream()
                .collect(Collectors.toMap(ManagersEntity::id, ManagersEntity::name));
        TaskEntity task = taskOpt.get();
        return Optional.of(TaskDTO.toDTO(task, managerMap.get(task.id())));
    }
}
