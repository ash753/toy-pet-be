package com.toy.pet.service.pet;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberPet;
import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.enums.Relationship;
import com.toy.pet.repository.MemberPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberPetService {
    private final MemberPetRepository memberPetRepository;

    @Transactional
    public void saveMemberPet(Member member, Pet pet, Relationship relationship) {
        MemberPet memberPet = new MemberPet(member, pet, relationship);
        memberPetRepository.save(memberPet);
    }

    public List<MemberPet> findMemberPet(Member member) {
        return memberPetRepository.findAllByMember(member);
    }

    public List<MemberPet> findRelationShip(Member member, Pet pet) {
        return memberPetRepository.findAllByMemberAndPet(member, pet);
    }

    public void deleteMemberPet(Member member) {
        List<MemberPet> memberPetList = findMemberPet(member);
        memberPetList.forEach(memberPetRepository::delete);
    }
}
