package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.comment.CommentRespDto;
import com.triple_stack.route_in_backend.entity.Comment;
import com.triple_stack.route_in_backend.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {
    @Autowired
    private CommentMapper commentMapper;

    public int addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    public List<CommentRespDto> getCommentListByBoardId(Integer boardId) {
        return commentMapper.getCommentListByBoardId(boardId);
    }

    public Optional<Comment> getCommentByCommentId(Integer commentId) {
        return commentMapper.getCommentByCommentId(commentId);
    }

    public int deleteComment(Integer commentId) {
        return commentMapper.deleteComment(commentId);
    }
}
