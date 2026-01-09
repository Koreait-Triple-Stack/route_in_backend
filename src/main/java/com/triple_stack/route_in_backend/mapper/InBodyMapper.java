package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.InBody;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface InBodyMapper {
    int addInBody(InBody inBody);
    int deleteInBody(Integer inBodyId);
    List<InBody> getInBodyListByUserId(Integer userId);
    Optional<InBody> getInBodyByUserIdAndMonthDt(Integer userId, LocalDate monthDt);
    Optional<InBody> getInBodyByInBodyId(Integer inBodyId);
}
