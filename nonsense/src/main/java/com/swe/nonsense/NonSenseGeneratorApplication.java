package com.swe.nonsense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Main class of the NonSenseGenerator application that starts the Spring Boot application.
 * this class contains even the controller that handles the root endpoint
 */
@SpringBootApplication
public class NonSenseGeneratorApplication {

	/**
	 * Main method that serves as the launcher of the application
	 * 
	 * @param args Eventual arguments passed to the application at startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(NonSenseGeneratorApplication.class, args);
	}

	// Java
	
	/**
	 * Inner class that handles the root endpoint of the application
	 */
	@Controller
	public class HomeController {
		
		/**
		 * Method that handles the root endpoint of the application
		 * 
		 * @return The index view name, which should match a template name
		 */
		@GetMapping("/")
		public String index() {
			return "index"; // This should match a template name
		}
	}

}
