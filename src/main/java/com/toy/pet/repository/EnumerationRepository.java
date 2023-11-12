package com.toy.pet.repository;

import com.toy.pet.domain.entity.Enumeration;
import com.toy.pet.domain.enums.EnumerationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnumerationRepository extends JpaRepository<Enumeration, Long> {

    List<Enumeration> findAllByEnumerationCategory(EnumerationCategory enumerationCategory);
}
