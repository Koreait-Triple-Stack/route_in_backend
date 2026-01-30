package com.triple_stack.route_in_backend.dto.ai;

import com.triple_stack.route_in_backend.entity.Course;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AIRecommendReqDto {
    private Integer userId;
    private UserProfileDto userProfile;
    private BodyInfoDto bodyInfo;
    private LocationDto location;
    private WorkoutHistoryDto workoutHistory;
    private List<Course> course;

    @Data
    public static class UserProfileDto {
        private LocalDate birthDate;
        private String gender;
    }

    @Data
    public static class BodyInfoDto {
        private Integer height;
        private Integer weight;
        private Float skeletalMuscleMass;
        private Float bodyFatMass;
        private LocalDate monthDt;
    }

    @Data
    public static class LocationDto {
        private String zipCode;
        private Double lat;
        private Double lng;
    }

    @Data
    public static class WorkoutHistoryDto {
        private List<String> currentRun;
        private List<String> weeklyRun;
        private String exercise;
        private String weekday;
    }
}
