package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Routine { // 각각 리스트에 미리 new ArrayList로 설정해 둘지, 또한 run 관련된 테이블을 따로 뺄지
    private Integer routineId;
    private List<String> monday;
    private List<String> tuesday;
    private List<String> wednesday;
    private List<String> thursday;
    private List<String> friday;
    private List<String> saturday;
    private List<String> sunday;
    private Integer userId;
    private Integer boardId;
}
