package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Routine {
    private Integer routineId;
    private Integer userId;
    private Integer boardId;
    private String weekday;
    private String exercise;
    private Boolean checked;
}
