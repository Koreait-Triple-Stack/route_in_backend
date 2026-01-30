package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.weather.AddWeatherDto;
import com.triple_stack.route_in_backend.entity.Weather;
import com.triple_stack.route_in_backend.mapper.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WeatherRepository {
    @Autowired
    private WeatherMapper weatherMapper;

    public int addWeather(AddWeatherDto addWeatherDto) {
        return weatherMapper.addWeather(addWeatherDto);
    }

    public Optional<Weather> getWeatherByWeatherId(Integer weatherId) {
        return weatherMapper.getWeatherByWeatherId(weatherId);
    }
}
