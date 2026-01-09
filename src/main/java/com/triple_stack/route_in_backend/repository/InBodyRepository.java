package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.InBody;
import com.triple_stack.route_in_backend.mapper.InBodyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class InBodyRepository {
    @Autowired
    private InBodyMapper inBodyMapper;

    public int addInBody(InBody inBody){
        return inBodyMapper.addInBody(inBody);
    }
    public int deleteInBody(Integer inBodyId){
        return inBodyMapper.deleteInBody(inBodyId);
    }

    public Optional<InBody> getInBodyByUserIdAndMonthDt(Integer userId, LocalDate monthDt) {
        return inBodyMapper.getInBodyByUserIdAndMonthDt(userId, monthDt);
    }
    public List<InBody> getInBodyListByUserId(Integer userId){
        return inBodyMapper.getInBodyListByUserId(userId);
    }
    public Optional<InBody> getInBodyByInBodyId(Integer inBodyId) {
        return inBodyMapper.getInBodyByInBodyId(inBodyId);
    }
}
