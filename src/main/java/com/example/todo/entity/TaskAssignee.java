package com.example.todo.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class TaskAssignee implements Serializable {
    private Long taskId;
    private Long assigneeId;
}

