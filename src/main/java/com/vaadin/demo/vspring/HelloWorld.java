package com.vaadin.demo.vspring;

import org.springframework.stereotype.Component;
@Component
public class HelloWorld {


        protected String message;
        public String getMessage() {
    return message;
    }
        public void setMessage(String message) {
    this.message = message;
    }
        public void execute() {
    System.out.println("Hello bean" + getMessage() + "!");
    }
    
}
