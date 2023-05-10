package com.course.naidu.restfulwebservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.course.naidu.restfulwebservices.model.HelloWorldBean;

@RestController
public class HelloWorldController {
	// @RequestMapping(path = "/hello-world", method = RequestMethod.GET) Instead of
	// this we can use GetMapping
	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "hello world";
	}

	@GetMapping(path = "/hello-world-json", produces = "application/json")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello world");
	}

	@GetMapping(path = "/hello-world/{name}", produces = "application/json")
	public HelloWorldBean helloWorld1(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello %s. How are you doing?", name));
	}
}
