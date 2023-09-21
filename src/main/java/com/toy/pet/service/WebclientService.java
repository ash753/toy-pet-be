package com.toy.pet.service;

import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface WebclientService {

    <T> T getRequest(String uri, MultiValueMap<String, String> headerMap, Class<T> responseType);
}
