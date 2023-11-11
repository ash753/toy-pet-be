package com.toy.pet.domain.request;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberTerm;
import com.toy.pet.domain.entity.Term;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TermRegisterRequest {
    @NotNull
    private Long id;

    @NotNull
    private Boolean agree;

    public MemberTerm toEntity(Member member, Term term) {
        if (!id.equals(term.getId())) {
            throw new IllegalArgumentException("약관 id가 올바르지 않습니다.");
        }

        return new MemberTerm(member, term, agree);
    }
}
