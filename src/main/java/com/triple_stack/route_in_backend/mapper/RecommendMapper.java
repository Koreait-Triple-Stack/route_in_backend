package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Recommend;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecommendMapper {
    int plusRecommend(Recommend recommend);
    int minusRecommend(Recommend recommend);
}
