package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Recommend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecommendMapper {
    int plusRecommend(Recommend recommend);
    int minusRecommend(Recommend recommend);
    List<Recommend> getRecommendListByBoardId(Integer userId);
}
