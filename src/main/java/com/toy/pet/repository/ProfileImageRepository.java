package com.toy.pet.repository;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    List<ProfileImage> findByMember(Member member);
}
