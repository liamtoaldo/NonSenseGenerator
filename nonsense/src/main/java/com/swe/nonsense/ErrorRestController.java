package com.swe.nonsense;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorRestController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "Something went wrong. Please try again later.";
    }

}
