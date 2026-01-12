package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.Recommend;
import com.triple_stack.route_in_backend.mapper.RecommendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecommendRepository {
    @Autowired
    private RecommendMapper recommendMapper;

    public int plusRecommend(Recommend recommend) {
        return recommendMapper.plusRecommend(recommend);
    }

    public int minusRecommend(Recommend recommend) {
        return recommendMapper.minusRecommend(recommend);
    }
}
