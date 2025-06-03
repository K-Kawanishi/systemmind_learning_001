package com.example.todo.controller;

import org.springframework.stereotype.Controller; //ControllerだよとSpringに教える
import org.springframework.web.bind.annotation.GetMapping; //URLを開いたら後述するメソッドの中にある画面に飛ばすようにする

@Controller   //コントローラー(画面やAPIの入口)
public class IndexController {

    // http://localhost:8080/ -> トップページ表示
    @GetMapping("/")  // 「/」（トップページ）へのGETリクエストが来たとき、この下のメソッドを実行するよう指定
    public String index(){
        return "index";
    }  //画面テンプレート（index.htmlなど）を表示するようSpringに指示
}
