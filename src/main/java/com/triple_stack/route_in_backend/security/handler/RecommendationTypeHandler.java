package com.triple_stack.route_in_backend.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple_stack.route_in_backend.dto.ai.RecommendationDto;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 이 핸들러는 오직 RecommendationDto 객체 변환만을 담당합니다.
@MappedTypes(RecommendationDto.class)
public class RecommendationTypeHandler extends BaseTypeHandler<RecommendationDto> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RecommendationDto parameter, JdbcType jdbcType) throws SQLException {
        try {
            // DTO 객체를 통째로 JSON 문자열로 변환 (내부의 List<String> tags도 알아서 변환됨)
            ps.setString(i, mapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("JSON 변환 에러 (DTO -> DB)", e);
        }
    }

    @Override
    public RecommendationDto getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }

    @Override
    public RecommendationDto getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public RecommendationDto getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private RecommendationDto parse(String json) throws SQLException {
        if (json == null || json.isEmpty()) return null;
        try {
            // JSON 문자열을 RecommendationDto 객체로 복원
            return mapper.readValue(json, RecommendationDto.class);
        } catch (JsonProcessingException e) {
            throw new SQLException("JSON 파싱 에러 (DB -> DTO)", e);
        }
    }
}