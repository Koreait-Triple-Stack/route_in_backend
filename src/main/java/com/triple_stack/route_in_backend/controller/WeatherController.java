package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/api/weather")
    public ResponseEntity<?> getWeather(@RequestParam double lat, @RequestParam double lon) {
        return ResponseEntity.ok(weatherService.getWeather(lat, lon));
    }
}
