package com.toy.pet.domain.entity;

import com.toy.pet.domain.enums.Relationship;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Table(name = "member_pets")
@Getter
@Entity
@Where(clause = "delete_yn = 0")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPet extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "pet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pet pet;

    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    private boolean deleteYn;

    public MemberPet(Member member, Pet pet, Relationship relationship) {
        this.member = member;
        this.pet = pet;
        this.relationship = relationship;
        this.createdBy = member.getId();
        this.updatedBy = member.getId();
    }
}
