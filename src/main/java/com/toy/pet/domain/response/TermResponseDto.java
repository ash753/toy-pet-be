package com.toy.pet.domain.response;

import com.toy.pet.domain.entity.Term;
import lombok.Getter;

@Getter
public class TermResponseDto {
    private final Long id;
    private final boolean required;
    private final String title;
    private final String description;

    public TermResponseDto(Term term){
        this.id = term.getId();
        this.required = term.isRequired();
        this.title = term.getTitle();
        this.description = term.getDescription();
    }
}
