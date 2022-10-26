package com.pragma.reto.apibackend.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String code, String message, Object data, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);

        return new ResponseEntity<>(map, status);
    }

}
