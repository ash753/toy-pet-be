package com.toy.pet.repository;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberPet;
import com.toy.pet.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberPetRepository extends JpaRepository<MemberPet, Long> {
    List<MemberPet> findAllByMemberAndPet(Member member, Pet pet);

    List<MemberPet> findAllByMember(Member member);
}
