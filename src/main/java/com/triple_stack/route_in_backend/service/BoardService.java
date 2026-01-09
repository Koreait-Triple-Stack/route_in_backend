package com.triple_stack.route_in_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
// import triple_stack.route_in_backend.dto.*;
// import triple_stack.route_in_backend.entity.User;
// import triple_stack.route_in_backend.repository.BoardRepository;
// import triple_stack.route_in_backend.repository.UserRepository;
// import triple_stack.route_in_backend.security.model.PrincipalUser;

import java.util.Optional;

@Service
public class BoardService {
   // @Autowired
   // private BoardRepository boardRepository;
   // @Autowired
   // private UserRepository userRepository;
   //
   //
   // // 게시글 추가
   // public ApiRespDto<?> addBoard(AddBoardReqDto addBoardReqDto, PrincipalUser principalUser) {
   //     if (!addBoardReqDto.getUserId().equals(principalUser.getUserId())) {
   //         return new ApiRespDto<>("");
   //     }
   //     Optional<User> foundUser = userRepository.getUserByUserId(addBoardReqDto.getUserId());
   //     if (foundUser.isEmpty()) {
   //         return new ApiRespDto<>();
   //     }
   //     int result = boardRepository.addBoard(addBoardReqDto.toEntity());
   //     if (result != 1) {
   //         return new ApiRespDto<>();
   //     }
   //     return new ApiRespDto<>();
   // }
   //
   // // 게시글 수정
   // public ApiRespDto<?> updateBoard(UpdateBoardReqDto updateBoardReqDto, PrincipalUser principalUser) {
   //     if (!updateBoardReqDto.getUserId().equals(principalUser.getUserId())) {
   //         return new ApiRespDto<>();
   //     }
   //
   //     Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(updateBoardReqDto.getBoardId());
   //     if (foundBoard.isEmpty()) {
   //         return new ApiRespDto<>();
   //     }
   //
   //     int result = boardRepository.updateBoard(updateBoardReqDto.toEntity());
   //     if (result != 1) {
   //         return new ApiRespDto<>();
   //     }
   //
   //     return new ApiRespDto<>();
   // }
   //
   // // 게시글 삭제
   // public ApiRespDto<?> removeBoard(RemoveBoardReqDto removeBoardReqDto, PrincipalUser principalUser) {
   //     if (!removeBoardReqDto.getUserId().equals(principalUser.getUserId())
   //             && principalUser.getUserRoles()
   //             .stream()
   //             .noneMatch(userRole -> userRole.getRole().getRoleId() == 1)) {
   //         return new ApiRespDto<>();
   //     }
   //
   //     Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(removeBoardReqDto.getBoardId());
   //     if (foundBoard.isEmpty()) {
   //         return new ApiRespDto<>();
   //     }
   //
   //     int result = boardRepository.removeBoard(removeBoardReqDto.getBoardId());
   //     if (result != 1) {
   //         return new ApiRespDto<>();
   //     }
   //
   //     return new ApiRespDto<>();
   // }
   // 게시글 단건 조회
}

