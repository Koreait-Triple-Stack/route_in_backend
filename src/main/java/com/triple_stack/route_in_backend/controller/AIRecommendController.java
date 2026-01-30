 package com.triple_stack.route_in_backend.controller;

 import com.triple_stack.route_in_backend.dto.ai.SendQuestionDto;
 import com.triple_stack.route_in_backend.service.AIRecommendService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/ai")
 public class AIRecommendController {
     @Autowired
     private AIRecommendService aiRecommendService;

     @GetMapping("/chatList/{userId}")
     public ResponseEntity<?> getAIChatListByUserId(@PathVariable Integer userId) {
         return ResponseEntity.ok(aiRecommendService.getAIChatListByUserId(userId));
     }

     @GetMapping("/recommend/{userId}")
     public ResponseEntity<?> getTodayRecommendation(@PathVariable Integer userId) {
         return ResponseEntity.ok(aiRecommendService.getTodayRecommendation(userId));
     }

     @PostMapping("/question")
     public ResponseEntity<?> getAIResp(@RequestBody SendQuestionDto sendQuestionDto) {
         return ResponseEntity.ok(aiRecommendService.getAIResp(sendQuestionDto));
     }
 }