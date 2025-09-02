package com.example.todo.controller;

import com.example.todo.dto.TaskDTO;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.form.BulkEditForm;
import com.example.todo.form.TaskForm;
import com.example.todo.form.TaskSearchForm;
import com.example.todo.service.TaskService;
import com.example.todo.service.AssigneeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/tasks")
@Controller
public class TaskController {

    private final TaskService taskService;
    private final AssigneeService assigneeService;

    /**
     * タスク一覧画面
     */
    @GetMapping("")
    public String list(TaskSearchForm searchForm, Model model) {
        var fixedSearchForm = new TaskSearchForm(
            searchForm.summary(),
            searchForm.status() != null ? searchForm.status() : List.of(),
            searchForm.priority() != null ? searchForm.priority() : List.of()
        );
        var taskEntities = taskService.find(fixedSearchForm.toEntity());
        var taskList = taskEntities.stream().map(TaskDTO::toDTO).toList();
        model.addAttribute("taskList", taskList);
        model.addAttribute("searchDTO", fixedSearchForm);
        model.addAttribute("assignees", assigneeService.findAll());
        return "tasks/list";
    }

    /**
     * タスク詳細画面
     */
    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        var taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            return "error/404";
        }
        // TaskDTOにassignees情報が無いので、担当者名はtaskOpt.get().getAssigneeName()等で取得する想定に修正
        model.addAttribute("task", taskOpt.get());
        return "tasks/detail";
    }

    /**
     * タスク作成画面
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("taskForm", new TaskForm("", "", "TODO", "中", ""));
        model.addAttribute("mode", "CREATE");
        model.addAttribute("id", null);
        model.addAttribute("assignees", assigneeService.findAll());
        return "tasks/form";
    }

    /**
     * タスク作成処理
     */
    @PostMapping("/create")
    public String create(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "CREATE");
            model.addAttribute("assignees", assigneeService.findAll());
            return "tasks/form";
        }
        taskService.create(taskForm.toEntity(), List.of(Long.valueOf(taskForm.assigneeId())));
        return "redirect:/tasks";
    }

    /**
     * タスク編集画面
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
        model.addAttribute("assignees", assigneeService.findAll());
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
            model.addAttribute("assignees", assigneeService.findAll());
            return "tasks/form";
        }
        taskService.update(taskForm.toEntity(id), List.of(Long.valueOf(taskForm.assigneeId())));
        return "redirect:/tasks";
    }

    /**
     * タスク削除
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    /**
     * 一括削除
     */
    @PostMapping("/bulk-delete")
    @ResponseBody
    public String bulkDelete(@RequestBody BulkEditForm form) {
        taskService.deleteAllByIds(form.getIds());
        return "OK";
    }

    /**
     * 一括編集
     */
    @PostMapping("/bulk-edit")
    @ResponseBody
    public String bulkEdit(@RequestBody BulkEditForm form) {
        taskService.bulkEdit(form.getIds(), form.getStatus(), form.getPriority(), form.getAssigneeId());
        return "OK";
    }

    /**
     * 一括編集用 選択タスク情報取得API
     */
    @GetMapping("/bulk-info")
    @ResponseBody
    public List<BulkInfoResponse> getBulkInfo(@RequestParam("ids") List<Long> ids) {
        // タスク情報を取得し、必要な情報のみ返却
        return taskService.findAllByIds(ids).stream()
                .map(task -> new BulkInfoResponse(
                        task.getId(),
                        task.getStatus() != null ? task.getStatus().name() : null,
                        task.getPriority(),
                        task.getAssigneeId() // TaskDTOやEntityにgetterが必要
                ))
                .toList();
    }

    // レスポンス用DTO
    public static class BulkInfoResponse {
        public Long id;
        public String status;
        public String priority;
        public Long assigneeId;
        public BulkInfoResponse(Long id, String status, String priority, Long assigneeId) {
            this.id = id;
            this.status = status;
            this.priority = priority;
            this.assigneeId = assigneeId;
        }
    }
}
