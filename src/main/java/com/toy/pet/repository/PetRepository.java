package com.toy.pet.repository;

import com.toy.pet.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findPetBySharingCode(String sharingCode);
}
