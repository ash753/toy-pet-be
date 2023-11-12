package com.toy.pet.repository;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberTermRepository extends JpaRepository<MemberTerm, Long> {

    List<MemberTerm> findAllByMember(Member member);
}
