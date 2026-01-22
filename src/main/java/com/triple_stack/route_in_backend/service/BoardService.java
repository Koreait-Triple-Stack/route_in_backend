package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.board.*;
import com.triple_stack.route_in_backend.dto.board.CopyPayloadReqDto;
import com.triple_stack.route_in_backend.entity.Board;
import com.triple_stack.route_in_backend.entity.Course;
import com.triple_stack.route_in_backend.entity.CoursePoint;
import com.triple_stack.route_in_backend.entity.User;
import com.triple_stack.route_in_backend.repository.*;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecommendRepository recommendRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoursePointRepository coursePointRepository;

    @Transactional
    public ApiRespDto<?> addBoard(AddBoardReqDto addBoardReqDto, PrincipalUser principalUser) {
        if (!addBoardReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<User> foundUser = userRepository.getUserByUserId(addBoardReqDto.getUserId());
        if (foundUser.isEmpty()) {
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }

        List<String> tags = (addBoardReqDto.getTags() == null) ? List.of() : addBoardReqDto.getTags();

        Board board = addBoardReqDto.toEntity(principalUser.getUserId(), tags);

        Optional<Board> optionalBoard = boardRepository.addBoard(board);
        if (optionalBoard.isEmpty()) {
            throw new RuntimeException("게시물 추가 실패");
        }

        if ("COURSE".equals(addBoardReqDto.getType())) {
            addBoardReqDto.getCourse().setBoardId(optionalBoard.get().getBoardId());
            Optional<Course> optionalCourse = courseRepository.addCourse(addBoardReqDto.getCourse());
            if (optionalCourse.isEmpty()) {
                throw new RuntimeException("러닝 코스 추가에 실패했습니다.");
            }

            Integer courseId = optionalCourse.get().getCourseId();
            for (CoursePoint point : addBoardReqDto.getCourse().getPoints()) {
                point.setCourseId(courseId);
                int result = coursePointRepository.addCoursePoint(point);
                if (result != 1) {
                    throw new RuntimeException("러닝 코스 추가에 실패했습니다.");
                }
            }
        } else {
            // 운동 루틴 로직 작성
        }

        return new ApiRespDto<>("success", "게시물이 추가되었습니다.", null);
    }

    public ApiRespDto<?> getBoardList() {
        return new ApiRespDto<>("success", "게시물 전체 조회 완료", boardRepository.getBoardList());
    }

    public ApiRespDto<?> getBoardInfinite(BoardInfiniteParam param) {
        if ("RECOMMEND".equals(param.getSort())) {
            if (param.getCursorBoardId() != null ^ param.getCursorRecommendCnt() != null) {
                throw new RuntimeException("cursorBoardId와 cursorRecommendCnt가 모두 전달되지 않았습니다");
            }
        } else { // LATEST(default)
            if (param.getCursorBoardId() != null ^ param.getCursorCreateDt() != null) {
                throw new RuntimeException("cursorBoardId와 cursorCreateDt가 모두 전달되지 않았습니다");
            }
        }

        param.setLimitPlusOne(param.getLimitPlusOne());

        List<BoardRespDto> rows = boardRepository.getBoardInfinite(param);

        boolean hasNext = rows.size() > param.getLimit();
        if (hasNext) {
            rows = rows.subList(0, param.getLimit());
        }
        BoardInfiniteRespDto data = new BoardInfiniteRespDto(rows, hasNext, null, null, null);
        if (!rows.isEmpty()) {
            BoardRespDto last = rows.get(rows.size() - 1);
            data.setNextCursorBoardId(last.getBoardId());
            if ("RECOMMEND".equals(param.getSort())) {
                data.setNextCursorRecommendCnt(last.getRecommendCnt());
            } else {
                data.setNextCursorCreateDt(last.getCreateDt());
            }
        }

        return new ApiRespDto<>("success", "게시물 무한스크롤 조회 완료", data);
    }

    public ApiRespDto<?> getBoardByBoardId(Integer boardId) {
        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(boardId);
        if (foundBoard.isEmpty()) {
            throw new RuntimeException("존재하지 않는 게시물입니다");
        }

        BoardRespDto board = foundBoard.get();
//        if (board.getType().equals("routine")) {
//            List<Routine> optionalRoutine = routineRepository.getRoutine(null, board.getBoardId());
//            if (optionalRoutine.isEmpty()) {
//                throw new RuntimeException("운동 루틴 조회에 실패했습니다");
//            }
//            board.setRoutine(optionalRoutine.get());
//        }

        return new ApiRespDto<>("success", "게시물 조회 완료", board);
    }

    public ApiRespDto<?> getBoardListByKeyword(String keyword) {
        return new ApiRespDto<>("success", "게시물 검색 조회 완료", boardRepository.getBoardListByKeyword(keyword));
    }

    @Transactional
    public ApiRespDto<?> updateBoard(UpdateBoardReqDto updateBoardReqDto, PrincipalUser principalUser) {
        if (!updateBoardReqDto.getUserId().equals(principalUser.getUserId())
                && !"ROLE_ADMIN".equals(principalUser.getRole())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(updateBoardReqDto.getBoardId());
        if (foundBoard.isEmpty()) {
            throw new RuntimeException("존재하지 않는 게시물입니다.");
        }

        int result = boardRepository.updateBoard(updateBoardReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("게시물 수정 실패.");
        }

//        if (updateBoardReqDto.getType().equals("routine")) {
//            Routine routine = updateBoardReqDto.getRoutine();
//            routine.setBoardId(updateBoardReqDto.getBoardId());
//            int routineResult = routineRepository.updateRoutine(routine);
//            if (routineResult != 1) {
//                throw new RuntimeException("운동 루틴 수정 실패");
//            }
//        }

        return new ApiRespDto<>("success", "게시물 수정 완료", null);
    }

    public ApiRespDto<?> removeBoard(RemoveBoardReqDto removeBoardReqDto, PrincipalUser principalUser) {
        if (!removeBoardReqDto.getUserId().equals(principalUser.getUserId())
                && !"ROLE_ADMIN".equals(principalUser.getRole())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(removeBoardReqDto.getBoardId());
        if (foundBoard.isEmpty()) {
            throw new RuntimeException("존재하지 않는 게시물입니다.");
        }

        int result = boardRepository.removeBoard(removeBoardReqDto.getBoardId());
        if (result != 1) {
            throw new RuntimeException("게시물 삭제에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "게시물 삭제 완료", null);
    }

    public ApiRespDto<?> getBoardListByUserId(Integer userId) {
        Optional<User> foundUser = userRepository.getUserByUserId(userId);
        if (foundUser.isEmpty()) {
            throw new RuntimeException("회원 정보가 존재하지 않습니다..");
        }

        return new ApiRespDto<>("success", "유저 게시물 리스트 조회 완료", boardRepository.getBoardListByUserId(userId));
    }

    @Transactional
    public ApiRespDto<?> changeRecommend(ChangeRecommendReqDto changeRecommendReqDto) {
        System.out.println(changeRecommendReqDto);
        Optional<User> foundUser = userRepository.getUserByUserId(changeRecommendReqDto.getUserId());
        if (foundUser.isEmpty()) {
            throw new RuntimeException("추천 추가에 실패했습니다");
        }

        if (changeRecommendReqDto.getIsRecommended()) {
            int boardResult = boardRepository.minusRecommend(changeRecommendReqDto.getBoardId());
            if (boardResult != 1) {
                throw new RuntimeException("추천 목록 삭제에 실패했습니다");
            }

            int recommendResult = recommendRepository.minusRecommend(changeRecommendReqDto.toEntity());
            if (recommendResult != 1) {
                throw new RuntimeException("추천 취소에 실패했습니다");
            }
        } else {
            int boardResult = boardRepository.plusRecommend(changeRecommendReqDto.getBoardId());
            if (boardResult != 1) {
                throw new RuntimeException("추천 추가에 실패했습니다");
            }

            int recommendResult = recommendRepository.plusRecommend(changeRecommendReqDto.toEntity());
            if (recommendResult != 1) {
                throw new RuntimeException("추천 추가에 실패했습니다");
            }
        }
        return new ApiRespDto<>("success", "추천 변경 완료", null);
    }

    public ApiRespDto<?> getRecommendListByBoardId(Integer boardId) {
        return new ApiRespDto<>("success", "추천 리스트 조회 완료",
                recommendRepository.getRecommendListByBoardId(boardId));
    }

    @Transactional
    public ApiRespDto<?> copyPayload(CopyPayloadReqDto copyPayloadReqDto) {
        if (copyPayloadReqDto.getType().equals("COURSE")) {
            Optional<Course> foundCourse = courseRepository.getCourseByBoardId(copyPayloadReqDto.getBoardId());
            if (foundCourse.isEmpty()) {
                throw new RuntimeException("코스 조회에 실패했습니다.");
            }
            Course course = foundCourse.get();
            course.setBoardId(null);
            course.setUserId(copyPayloadReqDto.getUserId());
            Integer courseId = course.getCourseId();

            Optional<Course> optionalCourse = courseRepository.addCourse(course);
            if (optionalCourse.isEmpty()) {
                throw  new RuntimeException("코스 추가에 실패했습니다.");
            }

            List<CoursePoint> pointList = coursePointRepository.getCoursePointList(courseId);
            if (pointList.size() < 2) {
                throw new RuntimeException("경로 추가에 실패했습니다");
            }

            for (CoursePoint point : pointList) {
                point.setCourseId(course.getCourseId());
                int result = coursePointRepository.addCoursePoint(point);
                if (result != 1) {
                    throw new RuntimeException("경로 추가에 실패했습니다.");
                }
            }
        } else {
            // 운동 루틴 저장 로직 작성
        }

        return new ApiRespDto<>("success", (copyPayloadReqDto.getType().equals("COURSE") ? "코스" : "루틴") + " 저장을 완료했습니다", null);
    }
}
