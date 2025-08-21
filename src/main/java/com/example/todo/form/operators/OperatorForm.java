package com.example.todo.form.operators;

import com.example.todo.entity.Gender;
import com.example.todo.entity.OperatorUsers;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OperatorForm(
        @NotBlank
        @Size(max = 70 , message = "概要は256文字以内で入力してください")
        String name,
        @Size(max = 70 , message = "カナは256文字以内で入力してください")
        String kana,
        @NotBlank
        @Size(max = 100 , message = "メールアドレスは256文字以内で入力してください")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "メールアドレスの形式が正しくありません"
        )
        String email,
        @Pattern(
                regexp = "^\\+?[0-9]{1,3}?[-.\\s]?\\(?[0-9]{1,4}?\\)?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$",
                message = "電話番号の形式が正しくありません"
        )
        @Size(max = 15, message = "電話番号は15文字以内で入力してください")
        String phoneNumber,
        @NotBlank
        @Pattern(
                regexp = "MALE|FEMALE|OTHER",
                message = "性別は男性、女性、その他のいずれかを指定してください")
        String gender
) {
        /**
         * OperatorUsersエンティティからOperatorFormを生成します。
         *
         * @param operatorUsers OperatorUsersエンティティ
         * @return 生成されたOperatorForm
         */
        public static OperatorForm fromEntity(OperatorUsers operatorUsers) {
                return new OperatorForm(
                        operatorUsers.name(),
                        operatorUsers.kana(),
                        operatorUsers.email(),
                        operatorUsers.phoneNumber(),
                        operatorUsers.gender().name()
                );
        }
        /**
         * OperatorFormをOperatorUsersエンティティに変換します。
         *
         * @return 変換されたOperatorUsersエンティティ
         */
        public OperatorUsers toEntity() {
                return new OperatorUsers(null, name, kana, email, phoneNumber, Gender.valueOf(gender()));

        }

        /**
         * OperatorFormをOperatorUsersエンティティに変換します。
         * 指定されたIDを使用します。
         *
         * @param id オペレーターID
         * @return 変換されたOperatorUsersエンティティ
         */
        public OperatorUsers toEntity(Long id) {
                return new OperatorUsers(id, name, kana, email, phoneNumber, Gender.valueOf(gender()));
        }
}
