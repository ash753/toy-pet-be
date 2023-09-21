package com.toy.pet.service;

import com.toy.pet.service.WebclientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WebClientServiceImpl implements WebclientService {

    @Override
    public <T> T getRequest(String uri, MultiValueMap<String, String> headerMap, Class<T> responseType) {
        WebClient.Builder webclientBuilder = WebClient.builder()
                .baseUrl(uri);

        if (!ObjectUtils.isEmpty(headerMap)) {
            Set<String> headerKeySet = headerMap.keySet();
            for (String headerKey : headerKeySet) {
                List<String> headerValueList = headerMap.get(headerKey);
                webclientBuilder.defaultHeader(headerKey, headerValueList.toArray(new String[0]));
            }
        }

        WebClient webClient = webclientBuilder.build();

        return webClient.get()
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
}
