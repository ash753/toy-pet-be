package com.toy.pet.service.enumeration;

import com.toy.pet.domain.entity.Enumeration;
import com.toy.pet.domain.enums.EnumerationCategory;
import com.toy.pet.domain.response.EnumerationResponseDto;
import com.toy.pet.domain.response.EnumerationResponseListDto;
import com.toy.pet.repository.EnumerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EnumerationService {

    private final EnumerationRepository enumerationRepository;

    public EnumerationResponseListDto findEnumerationResponseListDto(String enumerationCategory) {
        return new EnumerationResponseListDto(enumerationRepository
                .findAllByEnumerationCategory(EnumerationCategory.findByCode(enumerationCategory))
                .stream()
                .map(EnumerationResponseDto::new)
                .collect(Collectors.toList()));
    }
}
