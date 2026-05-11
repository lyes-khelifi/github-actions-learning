package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController implements HealthIndicator {

    @Override
    @GetMapping("/health")
    public Health health() {
        return Health.up()
                .withDetail("service", "github-actions-learning")
                .withDetail("version", "1.0-SNAPSHOT")
                .build();
    }

    @GetMapping("/metrics")
    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("status", "healthy");
        metrics.put("uptime", System.currentTimeMillis());
        metrics.put("memory", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        metrics.put("processors", Runtime.getRuntime().availableProcessors());
        return metrics;
    }
}