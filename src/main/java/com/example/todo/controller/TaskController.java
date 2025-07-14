package com.example.todo.controller;


import com.example.todo.dto.TaskDTO;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.form.TaskForm;
import com.example.todo.form.TaskSearchForm;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

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

    /**
     * タスク詳細画面を表示する
     */
    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        var taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            return "error/404";
        }
        model.addAttribute("task", taskOpt.get());
        return "tasks/detail";
    }

    /**
     * タスク作成画面を表示する
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("taskForm", new TaskForm("", "", "TODO", "中"));
        model.addAttribute("mode", "CREATE");
        model.addAttribute("id", null); // idを明示的にセット
        return "tasks/form";
    }

    /**
     * タスク作成処理
     */
    @PostMapping("/create")
    public String create(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "CREATE");
            return "tasks/form";
        }
        taskService.create(taskForm.toEntity());
        return "redirect:/tasks";
    }

    /**
     * タスク編集画面を表示する
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        var taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            return "error/404";
        }
        model.addAttribute("taskForm", TaskForm.fromEntity(taskOpt.get()));
        model.addAttribute("mode", "EDIT");
        model.addAttribute("id", id);
        return "tasks/form";
    }

    /**
     * タスク更新処理
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid TaskForm taskForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "EDIT");
            model.addAttribute("id", id);
            return "tasks/form";
        }
        taskService.update(taskForm.toEntity(id));
        return "redirect:/tasks";
    }

    /**
     * 一括削除エンドポイント
     */
    @PostMapping("/bulk-delete")
    @ResponseBody
    public ResponseEntity<?> bulkDelete(@RequestBody Map<String, List<Long>> body) {
        List<Long> ids = body.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("IDリストが空です");
        }
        try {
            taskService.deleteAllByIds(ids);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("削除に失敗しました");
        }
    }
}
