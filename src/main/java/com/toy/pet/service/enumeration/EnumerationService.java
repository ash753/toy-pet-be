package com.toy.pet.service.enumeration;

import com.toy.pet.domain.enums.EnumerationCategory;
import com.toy.pet.domain.response.EnumerationResponseDto;
import com.toy.pet.repository.EnumerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EnumerationService {

    private final EnumerationRepository enumerationRepository;

    public List<EnumerationResponseDto> findEnumerationResponseDtoList(EnumerationCategory enumerationCategory) {
        return enumerationRepository
                .findAllByEnumerationCategory(enumerationCategory)
                .stream()
                .map(EnumerationResponseDto::new)
                .collect(Collectors.toList());
    }
}
