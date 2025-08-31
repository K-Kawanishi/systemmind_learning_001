package com.example.todo.controller;

import com.example.todo.dto.TaskDTO;
import com.example.todo.dto.operators.OperatorsDTO;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.form.operators.OperatorForm;
import com.example.todo.form.operators.OperatorSearchForm;
import com.example.todo.service.OperatorService;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/operators")
public class OperatorsController {

    private final OperatorService operatorService;
    private final TaskService taskService;

    @GetMapping("")
    public String list(Model model, OperatorSearchForm searchForm) {
        var list = operatorService.find(searchForm.toEntity())
                .stream()
                .map(OperatorsDTO::toDTO)
                .toList();
        model.addAttribute("operatorList", list);
        model.addAttribute("searchDTO", searchForm.toDTO());
        return "operators/list";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long operatorId, Model model) {
        // タスクIDに一致するタスクを取得
        var operatorsDTO = operatorService.findById(operatorId)
                .map(OperatorsDTO::toDTO)
                .orElseThrow(TaskNotFoundException::new);
        var taskList = taskService.findByOperatorId(operatorId)
                .stream()
                .map(TaskDTO::toDTO)
                .toList();
        model.addAttribute("operator", operatorsDTO);
        model.addAttribute("taskList", taskList);
        return "operators/detail";
    }

    @GetMapping("/create")
    public String showCreate(OperatorForm form,Model model) {
        model.addAttribute("mode", "CREATE");
        return "operators/form";
    }

    @PostMapping("/create")
    public String create(@Validated OperatorForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showCreate(form,model);
        }
        // タスクを作成
        operatorService.create(form.toEntity());
        return "redirect:/operators";
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable("id") long operatorId,  Model model) {
        // タスクIDに一致するタスクを取得
        var form = operatorService.findById(operatorId)
                .map(OperatorForm::fromEntity)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("operatorForm", form);
        model.addAttribute("mode", "EDIT");
        return "operators/form";
    }

    @PutMapping("/{id}")
    public String update(
            @PathVariable("id") long operatorId,
            @Validated @ModelAttribute OperatorForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "EDIT");
            return "operators/form";
        }
        // タスクIDに一致するタスクを更新
        operatorService.update(form.toEntity(operatorId));
        return "redirect:/operators/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long operatorId) {
        // タスクIDに一致するタスクを削除
        operatorService.delete(operatorId);
        return "redirect:/operators";
    }

}
