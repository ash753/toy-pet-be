package com.toy.pet.repository;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.enums.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByOauthProviderAndOauthId(OAuthProvider oAuthProvider, Long OAuthId);
}
