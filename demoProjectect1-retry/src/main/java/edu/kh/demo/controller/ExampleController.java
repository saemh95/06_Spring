package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {

//	@GetMappign() : get (read)
//	@PostMapping : post (create)
//	@PutMapping() : put (update)
//	@DeleteMapping() : delete (delete)
	
	@GetMapping("example")
	public String exampleMethod() {
		
		
		
		return "example";
	}
	
}
