package net.practice.JournalApp.service;

import net.practice.JournalApp.api.response.WeatherResponse;
import net.practice.JournalApp.cache.AppCache;
import net.practice.JournalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

//    @Autowired
//    private RedisService redisService;



    @Cacheable(value = "weatherCache", key = "#city")
    public WeatherResponse getWeather(String city) {
        String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString())
                .replace(Placeholders.CITY, city)
                .replace(Placeholders.API_KEY, apiKey);

        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(finalAPI, HttpMethod.POST, null, WeatherResponse.class);

        return response.getBody();
        }
    }


