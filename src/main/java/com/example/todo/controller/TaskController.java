package com.example.todo.controller;


import com.example.todo.dto.TaskDTO;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.form.TaskForm;
import com.example.todo.form.TaskSearchForm;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks") //このクラス内の全てのURLの先頭に/tasksが付く。
public class TaskController {

    private final TaskService taskService;
    /*↑フィールド。このクラスだけで使えて一回だけ初期化できる。
    @RequiredArgsConstructorアノテーションのおかげで、SpringがTaskServiceのインスタンスを自動で注入（DI）してくれる。*/


    /**
     * タスク一覧画面を表示する。
     *
     * @param searchForm 検索条件を保持するフォームオブジェクト
     * @param model      ビューに渡すデータを保持するオブジェクト
     * @return タスク一覧画面のテンプレート名
     */
    @GetMapping("")  //tasksへのGETリクエストで呼ばれる
    public String list(TaskSearchForm searchForm, Model model) {  //検索条件を受け取って画面にデータを渡す。
        // 検索条件に一致するタスクを取得
        var taskList = taskService.find(searchForm.toEntity())
                .stream()
                .map(TaskDTO::toDTO)
                .toList();
        model.addAttribute("taskList", taskList);
        model.addAttribute("searchDTO", searchForm.toDTO());
        return "tasks/list";
    }

}
