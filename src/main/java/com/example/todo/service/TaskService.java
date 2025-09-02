package com.example.todo.service;

import com.example.todo.entity.Assignee;
import com.example.todo.entity.TaskEntity;
import com.example.todo.entity.TaskSearchEntity;
import com.example.todo.entity.TaskStatus;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.TaskAssigneeRepository;
import com.example.todo.repository.AssigneeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskAssigneeRepository taskAssigneeRepository;
    private final AssigneeRepository assigneeRepository;

    public List<TaskEntity> find(TaskSearchEntity searchEntity) {
        // タスク一覧取得時も担当者リストをセットする
        var tasks = taskRepository.select(searchEntity);
        for (var task : tasks) {
            var assignees = taskRepository.findAssigneesByTaskId(task.getId());
            task.setAssignees(assignees);
        }
        return tasks;
    }

    public Optional<TaskEntity> findById(long taskId) {
        // 担当者も含めて取得するメソッドを利用
        TaskEntity entity = taskRepository.selectWithAssigneesById(taskId);
        return Optional.ofNullable(entity);
    }

    @Transactional
    public void create(TaskEntity newEntity, List<Long> assigneeIds) {
        taskRepository.insert(newEntity);
        // insert後にIDがセットされていることを保証
        if (newEntity.getId() != null && assigneeIds != null) {
            for (Long assigneeId : assigneeIds) {
                taskAssigneeRepository.insert(newEntity.getId(), assigneeId);
            }
        }
    }

    @Transactional
    public void update(TaskEntity entity, List<Long> assigneeIds) {
        taskRepository.update(entity);
        taskAssigneeRepository.deleteByTaskId(entity.getId());
        if (entity.getId() != null && assigneeIds != null) {
            for (Long assigneeId : assigneeIds) {
                taskAssigneeRepository.insert(entity.getId(), assigneeId);
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        taskAssigneeRepository.deleteByTaskId(id);
        taskRepository.delete(id);
    }

    @Transactional
    public void deleteAllByIds(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }

    @Transactional
    public void bulkEdit(List<Long> ids, String status, String priority, Long assigneeId) {
        for (Long id : ids) {
            Optional<TaskEntity> opt = findById(id);
            if (opt.isPresent()) {
                TaskEntity entity = opt.get();
                if (status != null) entity.setStatus(TaskStatus.valueOf(status));
                if (priority != null) entity.setPriority(priority);
                update(entity, assigneeId != null ? List.of(assigneeId) : null);
            }
        }
    }

    /**
     * 指定したIDリストのタスクを全件取得
     */
    public List<TaskEntity> findAllByIds(List<Long> ids) {
        return taskRepository.findAllByIds(ids);
    }
}
