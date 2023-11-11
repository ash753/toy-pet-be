package com.toy.pet.repository;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberProfileImageRepository extends JpaRepository<MemberProfileImage, Long> {
    List<MemberProfileImage> findByMember(Member member);
}
