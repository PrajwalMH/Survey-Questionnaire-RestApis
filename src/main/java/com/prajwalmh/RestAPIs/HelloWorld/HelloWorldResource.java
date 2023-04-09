package com.prajwalmh.RestAPIs.HelloWorld;

import com.prajwalmh.RestAPIs.HelloWorld.HelloWorldBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller
@RestController
public class HelloWorldResource {
    //Hello-world ->"hello world"

    @RequestMapping("/hello-world")
    public String helloWorld(){
      return "Hello World";
    }

    @RequestMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    @RequestMapping("/hello-world-path-param/{name}")
    public HelloWorldBean helloWorldPatParam(@PathVariable String name){
        return new HelloWorldBean("Hello World "+name);
    }

    @RequestMapping("/hello-world-path-param/{name}/message/{message}")
    public HelloWorldBean helloWorldMultiplePathParam
            (@PathVariable String name,
             @PathVariable String message) {
        return new HelloWorldBean("Hello World " + name + "," + message);
    }

}
