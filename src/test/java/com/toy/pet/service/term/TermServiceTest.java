package com.toy.pet.service.term;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberTerm;
import com.toy.pet.domain.entity.Term;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.enums.Role;
import com.toy.pet.domain.request.TermRegisterRequest;
import com.toy.pet.exception.CommonException;
import com.toy.pet.repository.MemberRepository;
import com.toy.pet.repository.MemberTermRepository;
import com.toy.pet.repository.TermRepository;
import com.toy.pet.testconfig.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


@Import(TestConfig.class)
@Transactional
@SpringBootTest
class TermServiceTest {

    @Autowired private TermService termService;

    @Autowired private TermRepository termRepository;
    @Autowired private MemberRepository memberRepository;

    @Autowired private MemberTermRepository memberTermRepository;

    @Test
    @DisplayName("멤버의 약관 동의 정보 저장 테스트")
    void saveMemberTermTest() throws Exception {
        //given
        Member member = memberRepository.save(new Member("테스트멤버", OAuthProvider.KAKAO,
                1L, Role.ROLE_USER));

        Term requiredTerm = termRepository.save(new Term("필수약관", "필수약관 내용", true));
        Term optinalTerm = termRepository.save(new Term("선택약관", "선택약관 내용", false));

        //when
        TermRegisterRequest termRegisterRequest = new TermRegisterRequest(requiredTerm.getId(), true);
        TermRegisterRequest termRegisterRequest2 = new TermRegisterRequest(optinalTerm.getId(), true);
        List<TermRegisterRequest> termRegisterRequestList = List.of(termRegisterRequest, termRegisterRequest2);
        termService.saveMemberTerm(termRegisterRequestList, member);

        //then
        List<MemberTerm> memberTermList = memberTermRepository.findAll();
        Assertions.assertThat(memberTermList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("[예외] 약관 목록이 모두 넘어오지 않았을 때 테스트")
    void throwExceptionInsufficientTermTest() throws Exception {
        //given
        Member member = memberRepository.save(new Member("테스트멤버", OAuthProvider.KAKAO,
                1L, Role.ROLE_USER));

        Term requiredTerm = termRepository.save(new Term("필수약관", "필수약관 내용", true));
        termRepository.save(new Term("선택약관", "선택약관 내용", false));

        //when
        TermRegisterRequest termRegisterRequest = new TermRegisterRequest(requiredTerm.getId(), true);
        List<TermRegisterRequest> termRegisterRequestList = List.of(termRegisterRequest);

        CommonException commonException = assertThrows(CommonException.class, () -> {
            termService.saveMemberTerm(termRegisterRequestList, member);
        });

        Assertions.assertThat(commonException.getCode()).isEqualTo(ResponseCode.CODE_0060.getCode());
    }

    @Test
    @DisplayName("[예외] 필수 약관에 동의를 하지 않았으 때 테스트")
    void throwExceptionDisagreeForRequiredTerm() throws Exception {
        //given
        Member member = memberRepository.save(new Member("테스트멤버", OAuthProvider.KAKAO,
                1L, Role.ROLE_USER));

        Term requiredTerm = termRepository.save(new Term("필수약관", "필수약관 내용", true));
        termRepository.save(new Term("선택약관", "선택약관 내용", false));

        //when
        TermRegisterRequest termRegisterRequest = new TermRegisterRequest(requiredTerm.getId(), true);
        List<TermRegisterRequest> termRegisterRequestList = List.of(termRegisterRequest);

        CommonException commonException = assertThrows(CommonException.class, () -> {
            termService.saveMemberTerm(termRegisterRequestList, member);
        });

        Assertions.assertThat(commonException.getCode()).isEqualTo(ResponseCode.CODE_0060.getCode());
    }
}