# Rocky Linuxベースのイメージを使用
FROM rockylinux:9

# 必要なパッケージをインストール
RUN yum update -y && \
    yum install -y java-17-openjdk && \
    yum clean all

# 作業ディレクトリを設定
WORKDIR /app

# プロジェクト全体をコピー
COPY . /app

# Gradleでビルドを実行
RUN ./gradlew clean bootJar

## ビルド成果物をコピー
##　COPY build/libs/todo-0.0.1-SNAPSHOT.jar /app/app.jar

# アプリケーションを起動
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "build/libs/todo-0.0.1-SNAPSHOT.jar"]