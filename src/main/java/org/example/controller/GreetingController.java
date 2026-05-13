package org.example.controller;

import org.example.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling greeting requests.
 */
@RestController
public class GreetingController {

    /**
     * The GreetingService used to generate greetings.
     */
    @Autowired
    private GreetingService greetingService;

    @GetMapping("/api/greeting/{name}")
    public ResponseEntity<String> greeting(@PathVariable String name) {
        String message = greetingService.getGreeting(name);
        return ResponseEntity.ok(message);
    }
}