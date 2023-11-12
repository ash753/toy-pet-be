package com.toy.pet.service.pet;

import com.toy.pet.domain.entity.Enumeration;
import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.enums.*;
import com.toy.pet.exception.CommonException;
import com.toy.pet.repository.EnumerationRepository;
import com.toy.pet.repository.MemberRepository;
import com.toy.pet.repository.PetRepository;
import com.toy.pet.testconfig.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestConfig.class)
@Transactional
@SpringBootTest
class PetServiceTest {

    @Autowired private PetService petService;

    @Autowired private PetRepository petRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EnumerationRepository enumerationRepository;


    @Test
    @DisplayName("반려동물의 종류에 맞는 품종코드인지 테스트")
    void petBreedTest() throws Exception {
        //given
        final String POMERANIAN = "POMERANIAN";
        Enumeration dogEnumeration = new Enumeration(EnumerationCategory.DOG_BREED, POMERANIAN, "포메라니안");
        final String BENGAL = "BENGAL";
        Enumeration catEnumeration = new Enumeration(EnumerationCategory.CAT_BREED, BENGAL, "뱅갈");
        enumerationRepository.save(dogEnumeration);
        enumerationRepository.save(catEnumeration);

        Pet cat = new Pet(PetType.CAT, Gender.MALE, false, "testCat", null,
                BENGAL, LocalDate.now(), new Member(null, OAuthProvider.KAKAO, 0L, Role.ROLE_USER));

        Pet dog = new Pet(PetType.DOG, Gender.MALE, false, "testCat", null,
                BENGAL, LocalDate.now(), new Member(null, OAuthProvider.KAKAO, 0L, Role.ROLE_USER));

        //when
        assertDoesNotThrow(() ->{
            petService.checkPetBreed(cat);
        });
        CommonException commonException = assertThrows(CommonException.class, () -> {
            petService.checkPetBreed(dog);
        });

        //then
        Assertions.assertThat(commonException.getCode()).isEqualTo(ResponseCode.CODE_0083.getCode());
    }

    @Test
    @DisplayName("공유 코드로 반려동물을 탐색 테스트")
    void getPetWithSharingCodeTest() throws Exception {
        //given
        final String BENGAL = "BENGAL";
        Pet cat = new Pet(PetType.CAT, Gender.MALE, false, "testCat", null,
                BENGAL, LocalDate.now(), new Member(null, OAuthProvider.KAKAO, 0L, Role.ROLE_USER));
        petRepository.save(cat);

        //when
        cat.generateSharingCode();
        petRepository.flush();

        //then
        String sharingCode = cat.getSharingCode();
        Pet findPet = petService.getPetWithSharingCode(sharingCode);

        Assertions.assertThat(findPet).isNotNull();

        String randomSharingCode = UUID.randomUUID().toString();
        CommonException commonException = assertThrows(CommonException.class, () -> {
            petService.getPetWithSharingCode(randomSharingCode);
        });
        Assertions.assertThat(commonException.getCode()).isEqualTo(ResponseCode.CODE_0080.getCode());
    }
}