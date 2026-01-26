package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.comment.AddComment;
import com.triple_stack.route_in_backend.dto.comment.CommentRespDto;
import com.triple_stack.route_in_backend.entity.Comment;
import com.triple_stack.route_in_backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public ApiRespDto<?> addComment(AddComment addComment) {
        Integer parentId = addComment.getParentId();

        if (parentId != null) {
            Optional<Comment> parentComment = commentRepository.getCommentByCommentId(parentId);

            if (parentComment.isEmpty()) {
                throw new RuntimeException("부모 댓글이 존재하지 않습니다.");
            }

            if (parentComment.get().getParentId() != null) {
                parentId = parentComment.get().getParentId();
            }
        }

        Comment comment = addComment.toEntity();
        comment.setParentId(parentId);

        int result = commentRepository.addComment(comment);
        if (result != 1) {
            throw new RuntimeException("댓글 작성에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "댓글이 작성되었습니다.", null);
    }

    public ApiRespDto<?> getCommentListByBoardId(Integer boardId) {
        List<CommentRespDto> commentList = commentRepository.getCommentListByBoardId(boardId);

        List<CommentRespDto> resultList = new ArrayList<>();
        Map<Integer, CommentRespDto> commentMap = new HashMap<>();

        for (CommentRespDto commentRespDto : commentList) {
            commentMap.put(commentRespDto.getCommentId(), commentRespDto);

            if (commentRespDto.getParentId() == null) {
                resultList.add(commentRespDto);
            }
        }

        for (CommentRespDto commentRespDto : commentList) {
            if (commentRespDto.getParentId() != null) {
                CommentRespDto parent = commentMap.get(commentRespDto.getParentId());

                if (parent != null) {
                    parent.getCommentRespDtoList().add(commentRespDto);
                }
            }
        }

        int totalCount = 0;

        for (CommentRespDto root : resultList) {
            List<CommentRespDto> children = root.getCommentRespDtoList();

            // 대댓글 중 살아있는(false) 녀석이 있는지 확인
            // 수정: getIsDeleted() == 0  ->  getIsDeleted() == false
            boolean hasLiveChild = children.stream()
                    .anyMatch(child -> child.getIsDeleted() == false);

            // (1) 원댓글 카운트: 본인이 살아있거나(false) or 자식이 살아있어서 껍데기가 보여야 할 때
            // 수정: getIsDeleted() == 0  ->  getIsDeleted() == false
            if (root.getIsDeleted() == false || hasLiveChild) {
                totalCount++;
            }

            // (2) 대댓글 카운트: 살아있는 것만 센다
            // 수정: getIsDeleted() == 0  ->  getIsDeleted() == false
            totalCount += children.stream()
                    .filter(child -> child.getIsDeleted() == false)
                    .count();
        }

        // 3. 반환
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("comments", resultList);
        responseMap.put("totalCount", totalCount);

        return new ApiRespDto<>("success", "댓글 목록 조회 완료", responseMap);
    }

    @Transactional
    public ApiRespDto<?> deleteComment(Integer commentId) {
        Optional<Comment> comment = commentRepository.getCommentByCommentId(commentId);
        if (comment.isEmpty()) {
            throw new RuntimeException("존재하지 않는 댓글입니다.");
        }

        int result = commentRepository.deleteComment(commentId);
        if (result != 1) {
            throw new RuntimeException("댓글 삭제에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "댓글이 삭제되었습니다.", null);
    }
}
