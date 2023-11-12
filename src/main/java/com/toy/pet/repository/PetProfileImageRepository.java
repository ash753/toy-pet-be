package com.toy.pet.repository;

import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.entity.PetProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetProfileImageRepository extends JpaRepository<PetProfileImage, Long> {
    List<PetProfileImage> findAllByPet(Pet pet);
}
