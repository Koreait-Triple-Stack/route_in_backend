package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.comment.CommentRespDto;
import com.triple_stack.route_in_backend.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    int addComment(Comment comment);
    List<CommentRespDto> getCommentListByBoardId(Integer boardId);
    Optional<Comment> getCommentByCommentId(Integer commentId);
    int deleteComment(Integer commentId);
}