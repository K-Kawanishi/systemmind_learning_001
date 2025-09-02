package com.example.todo.entity;

import java.util.List;
import java.util.Objects;

/**
 * タスクを表すエンティティクラス。
 *
 * @param id タスクの一意な識別子
 * @param summary タスクの概要
 * @param description タスクの詳細説明
 * @param status タスクの現在の状態
 * @param priority タスクの優先度
 * @param assignees タスクの担当者
 */
public class TaskEntity {
    private Long id;
    private String summary;
    private String description;
    private TaskStatus status;
    private String priority;
    private List<Assignee> assignees;

    public TaskEntity() {
    }

    public TaskEntity(Long id, String summary, String description, TaskStatus status, String priority, List<Assignee> assignees) {
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignees = assignees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<Assignee> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<Assignee> assignees) {
        this.assignees = assignees;
    }

    /**
     * 担当者ID（1人だけの場合のみ返す。複数や未設定はnull）
     */
    public Long getAssigneeId() {
        if (assignees != null && assignees.size() == 1) {
            return assignees.get(0).getId();
        }
        return null;
    }
}
