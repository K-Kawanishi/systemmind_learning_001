package com.example.todo.controller;


import com.example.todo.dto.TaskDTO;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.form.TaskForm;
import com.example.todo.form.TaskSearchForm;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * タスク一覧画面を表示する。
     *
     * @param searchForm 検索条件を保持するフォームオブジェクト
     * @param model      ビューに渡すデータを保持するオブジェクト
     * @return タスク一覧画面のテンプレート名
     */
    @GetMapping("")
    public String list(TaskSearchForm searchForm, Model model) {
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
     * タスク詳細画面を表示する。
     *
     * @param taskId タスクのID
     * @param model  ビューに渡すデータを保持するオブジェクト
     * @return タスク詳細画面のテンプレート名
     * @throws TaskNotFoundException 指定されたIDのタスクが見つからない場合
     */
    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long taskId, Model model) {
        // タスクIDに一致するタスクを取得
        var taskDTO = taskService.findById(taskId)
                .map(TaskDTO::toDTO)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("task", taskDTO);
        return "tasks/detail";
    }

    /**
     * タスク作成画面を表示する。
     *
     * @param form  タスク作成用のフォームオブジェクト
     * @param model ビューに渡すデータを保持するオブジェクト
     * @return タスク作成画面のテンプレート名
     */
    @GetMapping("/create")
    public String showCreate(@ModelAttribute TaskForm form, Model model) {
        model.addAttribute("mode", "CREATE");
        return "tasks/form";
    }

    /**
     * タスクを作成する。
     *
     * @param form          タスク作成用のフォームオブジェクト
     * @param bindingResult 入力値のバリデーション結果を保持するオブジェクト
     * @param model         ビューに渡すデータを保持するオブジェクト
     * @return タスク一覧画面へのリダイレクトURL
     */
    @PostMapping("/create")
    public String create(@Validated TaskForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showCreate(form,model);
        }
        // タスクを作成
        taskService.create(form.toEntity());
        return "redirect:/tasks";
    }

    /**
     * タスク編集画面を表示する。
     *
     * @param id    タスクのID
     * @param model ビューに渡すデータを保持するオブジェクト
     * @return タスク編集画面のテンプレート名
     * @throws TaskNotFoundException 指定されたIDのタスクが見つからない場合
     */
    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable("id") long id,  Model model) {
        // タスクIDに一致するタスクを取得
        var form = taskService.findById(id)
                .map(TaskForm::fromEntity)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("taskForm", form);
        model.addAttribute("mode", "EDIT");
        return "tasks/form";
    }

    /**
     * タスクを更新する。
     *
     * @param id            更新対象のタスクのID
     * @param form          更新内容を保持するフォームオブジェクト
     * @param bindingResult 入力値のバリデーション結果を保持するオブジェクト
     * @param model         ビューに渡すデータを保持するオブジェクト
     * @return 更新が成功した場合はタスク詳細画面へのリダイレクトURL、
     *         バリデーションエラーがある場合はタスクフォーム画面のテンプレート名
     */
    @PutMapping("/{id}")
    public String update(
            @PathVariable("id") long id,
            @Validated @ModelAttribute TaskForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "EDIT");
            return "tasks/form";
        }
        // タスクIDに一致するタスクを更新
        taskService.update(form.toEntity(id));
        return "redirect:/tasks/{id}";
    }

    /**
     * タスクを削除する。
     *
     * @param id 削除対象のタスクのID
     * @return タスク一覧画面へのリダイレクトURL
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        // タスクIDに一致するタスクを削除
        taskService.delete(id);
        return "redirect:/tasks";
    }

    /**
     * タスクを一括削除する。
     *
     * @param ids 削除対象のタスクのIDリスト
     *
     */
    @PostMapping("/deleteBatch")
    public String deleteBatch(@RequestParam List<Long> ids) {
        taskService.deleteBatch(ids);
        return "redirect:/tasks";
    }

    /**
     *タスクを一括更新する。
     * @param ids 更新対象のタスクのIDリスト
     */
    @PostMapping("/updateBatch")
    public String updateBatch(@RequestParam(value = "ids") List<Long> ids,
                              @RequestParam(value = "priority")String priority,
                              @RequestParam(value = "status") String status) {
        if(!Objects.equals(status, "") && Objects.equals(priority, "")){
            taskService.updateStatus(ids, status);
        } else if (!Objects.equals(priority, "") && Objects.equals(status, "")) {
            taskService.updatePriority(ids, priority);}
        else {
            taskService.updateStatus(ids, status);
            taskService.updatePriority(ids, priority);
        }
        return "redirect:/tasks";
    }

}
