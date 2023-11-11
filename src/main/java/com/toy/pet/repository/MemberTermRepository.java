package com.toy.pet.repository;

import com.toy.pet.domain.entity.MemberTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTermRepository extends JpaRepository<MemberTerm, Long> {
}
