package com.triple_stack.route_in_backend.dto.board;

import com.triple_stack.route_in_backend.entity.Course;
import com.triple_stack.route_in_backend.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailRespDto {
    private Integer boardId;
    private Integer userId;
    private String type;
    private String title;
    private String content;
    private List<String> tags;
    private Integer recommendCnt;
    private String username;
    private LocalDate birthDate;
    private String profileImg;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    private Course course;
    private List<Routine> routines;
}
