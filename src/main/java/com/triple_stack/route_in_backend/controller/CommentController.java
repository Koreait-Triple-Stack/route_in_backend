package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.comment.AddComment;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody AddComment addComment, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(commentService.addComment(addComment, principalUser));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getCommentListByBoardId(@PathVariable Integer boardId) {
        return ResponseEntity.ok(commentService.getCommentListByBoardId(boardId));
    }

    @GetMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId) {
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }
}