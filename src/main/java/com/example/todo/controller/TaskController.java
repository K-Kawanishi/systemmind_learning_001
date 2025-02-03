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
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    /*
     * タスク一覧画面を表示する
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

    /*
     * タスク詳細画面を表示する
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

    /*
     * タスク作成画面を表示する
     */
    @GetMapping("/create")
    public String showCreate(@ModelAttribute TaskForm form, Model model) {
        model.addAttribute("mode", "CREATE");
        return "tasks/form";
    }

    /*
     * タスクを作成する
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

    /*
     * タスク編集画面を表示する
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

    /*
     * タスクを更新する
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

    /*
     * タスクを削除する
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        // タスクIDに一致するタスクを削除
        taskService.delete(id);
        return "redirect:/tasks";
    }
}
