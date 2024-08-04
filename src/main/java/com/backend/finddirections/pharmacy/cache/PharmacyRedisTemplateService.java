package com.backend.finddirections.pharmacy.cache;

import com.backend.finddirections.pharmacy.entity.dto.PharmacyRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRedisTemplateService {


    private static final String CACHE_KEY = "PHARMACY";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;


    @PostConstruct
    private void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }


    public void save(PharmacyRes pharmacyRes) {
        if (Objects.isNull(pharmacyRes) || Objects.isNull(pharmacyRes.id())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY, pharmacyRes.id().toString(), serializePharmacyDto(pharmacyRes));
            log.info("[PharmacyRedisTemplateService save success] id: {}", pharmacyRes.id());
        } catch (JsonProcessingException e) {
            log.error("[PharmacyRedisTemplateService save error] {}", e.getMessage());
        }
    }


    public List<PharmacyRes> findAll() {
        List<PharmacyRes> pharmacyRes = new ArrayList<>();

        try {
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                pharmacyRes.add(desSerializePharmacyDto(value));
            }
            return pharmacyRes;
        } catch (JsonProcessingException e) {
            log.error("[PharmacyRedisTemplateService findAll error] {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void deleteById(Long id) {
        hashOperations.delete(CACHE_KEY, id.toString());
        log.error("[PharmacyRedisTemplateService delete success] {}", id);
    }


    private String serializePharmacyDto(PharmacyRes pharmacyRes) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pharmacyRes);
    }

    private PharmacyRes desSerializePharmacyDto(String pharmacyData) throws JsonProcessingException {
        return objectMapper.readValue(pharmacyData, PharmacyRes.class);
    }
}
