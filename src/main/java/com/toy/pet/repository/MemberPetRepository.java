package com.toy.pet.repository;

import com.toy.pet.domain.entity.MemberPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPetRepository extends JpaRepository<MemberPet, Long> {

}
