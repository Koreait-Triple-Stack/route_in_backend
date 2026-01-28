// package com.triple_stack.route_in_backend.controller;
//
// import com.triple_stack.route_in_backend.service.AIRecommendService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequiredArgsConstructor
// public class AIRecommendController {
//     @Autowired
//     private AIRecommendService aiRecommendService;
//
//     @GetMapping("/api/recommend/{userId}")
//     public ResponseEntity<?> getAIContext(@PathVariable Integer userId) {
//         return ResponseEntity.ok(aiRecommendService.getAIContext(userId));
//     }
// }