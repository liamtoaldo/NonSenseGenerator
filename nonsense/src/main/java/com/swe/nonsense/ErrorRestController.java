package com.swe.nonsense;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * REST controller that handles errors in the application controller
 */
public class ErrorRestController implements ErrorController {

    @RequestMapping("/error")
    /**
     * Main method of the class that handles errors in the application
     * 
     * @return A String message indicating that an error has occurred
     */
    public String handleError() {
        return "Something went wrong. Please try again later.";
    }

}
