package com.synapx.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InsuranceClaimsAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceClaimsAgentApplication.class, args);
	}
	@GetMapping("/")
	public String hello() {
		return "Hello";
	}
}
