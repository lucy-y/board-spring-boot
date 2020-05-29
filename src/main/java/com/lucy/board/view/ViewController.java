package com.lucy.board.view;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController implements ErrorController {

    @RequestMapping("/")
    public String view() {
        return "index";
    }

    /** Controller에서 모든 uri에 대하여 index.html로 향하게 하는 함수 */
    @GetMapping("/error")
    public String error() {
        return "index";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
