INSERT INTO tasks (summary, description, status, priority) VALUES ('Spring Bootを学ぶ', 'TODOアプリを作る', 'TODO', '高');
INSERT INTO tasks (summary, description, status, priority) VALUES ('SQLを勉強する', 'MySQLを導入する', 'DOING', '中');
INSERT INTO tasks (summary, description, status, priority) VALUES ('設計書を作る', 'プログラム仕様書を作成してみる', 'TODO', '中');
INSERT INTO tasks (summary, description, status, priority) VALUES ('テストを実施してみる', '試験項目書を作成する', 'DONE', '低');
INSERT INTO tasks (summary, description, status, priority) VALUES ('テストを実施してみる', '試験項目書を作成する', 'DONE', '低');

INSERT INTO assignees (id, name) VALUES (1, 'ゴダイゴ');
INSERT INTO assignees (id, name) VALUES (2, 'はましょー');
INSERT INTO assignees (id, name) VALUES (3, '吉田拓郎');

INSERT INTO task_assignees (task_id, assignee_id) VALUES (1, 1);
INSERT INTO task_assignees (task_id, assignee_id) VALUES (2, 2);
INSERT INTO task_assignees (task_id, assignee_id) VALUES (3, 3);
