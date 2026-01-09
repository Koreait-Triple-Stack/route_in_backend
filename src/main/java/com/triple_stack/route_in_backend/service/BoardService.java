package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.board.AddBoardReqDto;
import com.triple_stack.route_in_backend.dto.board.BoardRespDto;
import com.triple_stack.route_in_backend.dto.board.RemoveBoardReqDto;
import com.triple_stack.route_in_backend.dto.board.UpdateBoardReqDto;
import com.triple_stack.route_in_backend.entity.Board;
import com.triple_stack.route_in_backend.entity.User;
import com.triple_stack.route_in_backend.repository.BoardRepository;
import com.triple_stack.route_in_backend.repository.UserRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    // 게시글 추가
    public ApiRespDto<?> addBoard(AddBoardReqDto addBoardReqDto, PrincipalUser principalUser) {
//        if (!addBoardReqDto.getUserId().equals(principalUser.getUserId())) {
//            throw new RuntimeException("");
//        }
//
//        Optional<User> foundUser = userRepository.getUserByUserId(addBoardReqDto.getUserId());
//        if (foundUser.isEmpty()) {
//            throw new RuntimeException("유저가 존재하지 않습니다.");
//        }
//
//
//        int result = boardRepository.addBoard(addBoardReqDto.toEntity(principalUser.getUserId(), ));
//        if (result != 1) {
//            throw new RuntimeException("게시물 추가 실패.");
//        }

        return new ApiRespDto<>("success", "게시물이 추가되었습니다.", null);
    }


    // 게시글 수정
    public ApiRespDto<?> updateBoard(UpdateBoardReqDto updateBoardReqDto, PrincipalUser principalUser) {
//        if (!updateBoardReqDto.getUserId().equals(principalUser.getUserId())) {
//            throw new RuntimeException("");
//        }
//
//        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(updateBoardReqDto.getBoardId());
//        if (foundBoard.isEmpty()) {
//            throw new RuntimeException("존재하지 않는 게시물입니다.");
//        }
//
//        int result = boardRepository.updateBoard(updateBoardReqDto.toEntity());
//        if (result != 1) {
//            throw new RuntimeException("게시물 수정에 실패했습니다.");
//        }

        return new ApiRespDto<>("success", "게시물 수정 완료", null);
    }

    // 게시글 삭제
    public ApiRespDto<?> removeBoard(RemoveBoardReqDto removeBoardReqDto, PrincipalUser principalUser) {
//        if (!removeBoardReqDto.getUserId().equals(principalUser.getUserId())
//                && principalUser.getUserRoles()
//                .stream()
//                .noneMatch(userRole -> userRole.getRole().getRoleId() == 1)) {
//            throw new RuntimeException("");
//        }
//
//        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(removeBoardReqDto.getBoardId());
//        if (foundBoard.isEmpty()) {
//            throw new RuntimeException("존재하지 않는 게시물입니다.");
//        }
//
//        int result = boardRepository.removeBoard(removeBoardReqDto.getBoardId());
//        if (result != 1) {
//            throw new RuntimeException("게시물 삭제에 실패했습니다.");
//        }

        return new ApiRespDto<>("success", "게시물 삭제 완료", null);
    }

    // 게시글 목록
    public ApiRespDto<?> getBoardList() {
        return new ApiRespDto<>("success", "게시물 전체 조회 완료", boardRepository.getBoardList());
    }

    // boardId로 단건 조회
    public ApiRespDto<?> getBoardByBoardId(Integer boardId) {
        return new ApiRespDto<>("success", "게시물 조회 완료", null);
    }

    // userId로 단건 조회
    public ApiRespDto<?> getBoardListByUserId(Integer userId) {
        return new ApiRespDto<>("success", "게시물 조회 완료", null);
    }

    // 키워드 검색

    // 무한 스크롤

}

