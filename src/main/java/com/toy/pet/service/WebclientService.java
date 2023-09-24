package com.toy.pet.service;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface WebclientService {

    <T> ResponseEntity<T> getRequest(String uri, MultiValueMap<String, String> headerMap, Class<T> responseType);
}
