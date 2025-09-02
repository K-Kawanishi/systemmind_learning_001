package com.example.todo.controller;

import com.example.todo.entity.Assignee;
import com.example.todo.form.AssigneeForm;
import com.example.todo.service.AssigneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/assignees")
public class AssigneeController {
    @Autowired
    private AssigneeService assigneeService;

    @GetMapping
    public String list(Model model) {
        List<Assignee> assignees = assigneeService.findAll();
        model.addAttribute("assignees", assignees);
        return "assignees/list";
    }
}
