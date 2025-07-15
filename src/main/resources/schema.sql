CREATE TABLE tasks
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    summary VARCHAR(256) NOT NULL,
    description TEXT,
    status VARCHAR(256) NOT NULL,
    priority VARCHAR(256) NOT NULL,
    operator_id BIGINT

);
CREATE TABLE operators
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256) NOT NULL,
    name_kana VARCHAR(256) NOT NULL,
    gender VARCHAR(256) NOT NULL,
    birthday DATE ,
    email VARCHAR(256)
);