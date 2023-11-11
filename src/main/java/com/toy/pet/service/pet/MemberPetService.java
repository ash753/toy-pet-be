package com.toy.pet.service.pet;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberPet;
import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.enums.Relationship;
import com.toy.pet.repository.MemberPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberPetService {
    private final MemberPetRepository memberPetRepository;

    @Transactional
    public void saveMemberPet(Member member, Pet pet, Relationship relationship) {
        MemberPet memberPet = new MemberPet(member, pet, relationship);
        memberPetRepository.save(memberPet);
    }
}
