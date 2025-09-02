CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    summary VARCHAR(256) NOT NULL,
    description TEXT,
    status VARCHAR(256) NOT NULL,
    priority VARCHAR(20)
);

CREATE TABLE assignees (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE task_assignees (
    task_id BIGINT NOT NULL,
    assignee_id BIGINT NOT NULL,
    PRIMARY KEY (task_id, assignee_id),
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    FOREIGN KEY (assignee_id) REFERENCES assignees(id) ON DELETE CASCADE
);

