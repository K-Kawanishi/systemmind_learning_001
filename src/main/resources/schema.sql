CREATE TABLE tasks
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    summary VARCHAR(256) NOT NULL,
    description TEXT,
    status VARCHAR(256) NOT NULL,
    priority VARCHAR(256) NOT NULL,
    operator_id BIGINT

);

CREATE TABLE operator_users
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256) NOT NULL,
    kana VARCHAR(256) NOT NULL,
    email VARCHAR(256) NOT NULL,
    phone_number VARCHAR(256) NOT NULL,
    gender VARCHAR(256) NOT NULL
);