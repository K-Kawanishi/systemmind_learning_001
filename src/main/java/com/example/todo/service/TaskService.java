package com.example.todo.service;

import com.example.todo.entity.TaskEntity;
import com.example.todo.entity.TaskSearchEntity;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.ManagersRepository;
import com.example.todo.entity.ManagersEntity;
import com.example.todo.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


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
     * 指定された検索条件に基づいてタスクと担当者名を紐付けて検索します。
     *
     * @param searchEntity 検索条件を含むエンティティ
     * @return タスクと担当者名の紐付け結果のリスト
     */
    public List<TaskDTO> findWithManager(TaskSearchEntity searchEntity) {
        var managers = managersRepository.findAll();
        return taskRepository.select(searchEntity).stream()
                .map(task -> {
                    String managerName = managers.stream()
                        .filter(m -> m.id().equals(task.managerId()))
                        .map(ManagersEntity::name)
                        .findFirst().orElse(null);
                    return TaskDTO.toDTO(task, managerName);
                })
                .toList();
    }

    /**
     * 指定されたIDのタスクを検索し、担当者名と共に返却します。
     *
     * @param taskId タスクのID
     * @return タスクと担当者名を含むタスクDTO（タスクが存在しない場合はnull）
     */
    public TaskDTO findByIdWithManager(long taskId) {
        var taskOpt = taskRepository.selectById(taskId);
        if (taskOpt.isEmpty()) return null;
        var task = taskOpt.get();
        var manager = (task.managerId() != null) ? managersRepository.findById(task.managerId()) : null;
        String managerName = (manager != null) ? manager.name() : null;
        return TaskDTO.toDTO(task, managerName);
    }
}
