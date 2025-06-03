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
                .map(TaskDTO::toDTO) //DTO変換。DBのデータ(エンティティ)を画面表示やAPI返却用の専用オブジェクト（DTO）に詰め替えること。
                .toList();
        model.addAttribute("taskList", taskList);  //画面にデータを渡す
        model.addAttribute("searchDTO", searchForm.toDTO());
        return "tasks/list";  //tasks/list.htmlを表示
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
}
