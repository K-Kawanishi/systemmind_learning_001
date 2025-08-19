INSERT INTO tasks (summary ,description ,status ,priority ,operator_id) values ('Spring Bootを学ぶ' , 'TODOアプリを作る' , 'TODO', 'HIGH', 0);
INSERT INTO tasks (summary ,description ,status ,priority ,operator_id) values ('SQLを勉強する' , 'MySQLを導入する' , 'DOING', 'MEDIUM', 0);
INSERT INTO tasks (summary ,description ,status ,priority ,operator_id) values ('設計書を作る' , 'プログラム仕様書を作成してみる' , 'TODO', 'LOW', 1);
INSERT INTO tasks (summary ,description ,status ,priority ,operator_id) values ('テストを実施してみる' , '試験項目書を作成する' , 'DONE', 'HIGH', 2);
INSERT INTO operator_users (name, kana, email, phone_number, gender) values ('山田太郎', 'ヤマダタロウ', 'yamada@gmail.com', '090-1234-5678', 'MALE');
INSERT INTO operator_users (name, kana, email, phone_number, gender) values ('佐藤花子', 'サトウハナコ', 'satou@gmail.com', '080-8765-4321', 'FEMALE');