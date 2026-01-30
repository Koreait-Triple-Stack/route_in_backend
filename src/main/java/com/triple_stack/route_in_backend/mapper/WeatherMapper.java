package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.weather.AddWeatherDto;
import com.triple_stack.route_in_backend.entity.Weather;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface WeatherMapper {
    int addWeather(AddWeatherDto addWeatherDto);
    Optional<Weather> getWeatherByWeatherId(Integer weatherId);
}
