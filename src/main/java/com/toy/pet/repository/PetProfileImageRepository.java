package com.toy.pet.repository;

import com.toy.pet.domain.entity.PetProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetProfileImageRepository extends JpaRepository<PetProfileImage, Long> {
}
