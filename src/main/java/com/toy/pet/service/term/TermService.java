package com.toy.pet.service.term;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberTerm;
import com.toy.pet.domain.entity.Term;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.request.TermRegisterRequest;
import com.toy.pet.domain.response.TermResponseDto;
import com.toy.pet.domain.response.TermResponseListDto;
import com.toy.pet.exception.CommonException;
import com.toy.pet.repository.MemberTermRepository;
import com.toy.pet.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermService {

    private final TermRepository termRepository;
    private final MemberTermRepository memberTermRepository;

    public TermResponseListDto findAllTermList(){
        List<TermResponseDto> termResponseDtoList = termRepository.findAll().stream().map(TermResponseDto::new)
                .collect(Collectors.toList());
        return new TermResponseListDto(termResponseDtoList);
    }

    @Transactional
    public void saveMemberTerm(List<TermRegisterRequest> termRegisterRequestList,
                               Member member) {
        List<Term> termList = termRepository.findAll();

        // 유효한 약관 id 인지 검증
        Set<Long> termIdSet = termList.stream()
                .map(Term::getId).collect(Collectors.toSet());
        Set<Long> registerTermIdSet = termRegisterRequestList.stream()
                .map(TermRegisterRequest::getId)
                .collect(Collectors.toSet());
        if (termIdSet.size() != registerTermIdSet.size() || !termIdSet.containsAll(registerTermIdSet)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0060);
        }

        for (TermRegisterRequest termRegisterRequest : termRegisterRequestList) {
            for (Term term : termList) {
                if (term.getId().equals(termRegisterRequest.getId())) {
                    MemberTerm memberTerm = termRegisterRequest.toEntity(member, term);
                    memberTermRepository.save(memberTerm);
                    break;
                }
            }
        }
    }
}
