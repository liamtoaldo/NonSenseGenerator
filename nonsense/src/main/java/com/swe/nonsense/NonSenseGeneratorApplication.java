package com.swe.nonsense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class NonSenseGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NonSenseGeneratorApplication.class, args);
	}

	// Java
	@Controller
	public class HomeController {
		@GetMapping("/")
		public String index() {
			return "index"; // This should match a template name
		}
	}

}
