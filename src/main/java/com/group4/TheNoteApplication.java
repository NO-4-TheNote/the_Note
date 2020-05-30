package com.group4;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.group4")
public class TheNoteApplication {
    public static void main(String[] args) {
        Application.launch(JavafxApplication.class,args);
    }
}
